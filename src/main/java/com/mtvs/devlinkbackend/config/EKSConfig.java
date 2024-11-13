package com.mtvs.devlinkbackend.config;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.credentials.AccessTokenAuthentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.eks.EksClient;
import software.amazon.awssdk.services.eks.model.DescribeClusterRequest;
import software.amazon.awssdk.services.eks.model.DescribeClusterResponse;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.utils.BinaryUtils;
import software.amazon.awssdk.utils.Md5Utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mtvs.devlinkbackend.eks.util.EksTokenGenerator.createStsClient;

@Configuration
public class EKSConfig {
    private static final String CLUSTER_NAME = "devlink-eks";
    private static final Region REGION = Region.AP_NORTHEAST_2; // 예: 서울 리전
    private static final String SERVICE = "sts";
    private static final String ALGORITHM = "AWS4-HMAC-SHA256";

    @Bean
    public ApiClient apiClient() throws Exception {
        // Initialize EKS client
        EksClient eksClient = EksClient.builder()
                .region(REGION)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

        // Describe the EKS cluster to retrieve endpoint and certificate
        DescribeClusterRequest describeClusterRequest = DescribeClusterRequest.builder()
                .name(CLUSTER_NAME)
                .build();

        DescribeClusterResponse describeClusterResponse = eksClient.describeCluster(describeClusterRequest);

        String clusterEndpoint = describeClusterResponse.cluster().endpoint();
        String clusterCaCert = describeClusterResponse.cluster().certificateAuthority().data();
        String token = getEksToken();

        // Configure the Kubernetes API client
        ApiClient client = ClientBuilder.standard()
                .setBasePath(clusterEndpoint)
                .setAuthentication(new AccessTokenAuthentication(token))
                .setCertificateAuthority(Base64.getDecoder().decode(clusterCaCert))
                .build();

        // Set the default API client to be used globally
        io.kubernetes.client.openapi.Configuration.setDefaultApiClient(client);
        return client;
    }

    public static String getEksToken() throws Exception {
        StsClient stsClient = createStsClient();
        AwsCredentials credentials = DefaultCredentialsProvider.create().resolveCredentials();

        Instant now = Instant.now();
        String amzDate = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")
                .withZone(ZoneOffset.UTC)
                .format(now);
        String shortDate = amzDate.substring(0, 8);

        String credentialScope = shortDate + "/" + REGION.id() + "/" + SERVICE + "/aws4_request";
        String host = "sts." + REGION.id() + ".amazonaws.com";
        String canonicalHeaders = "host:" + host + "\n" + "x-k8s-aws-id:" + CLUSTER_NAME + "\n";
        String signedHeaders = "host;x-k8s-aws-id";
        String payloadHash = hash("");

        String canonicalRequest = "GET\n/\n" + getCanonicalQueryString(credentials, amzDate, credentialScope) + "\n" +
                canonicalHeaders + "\n" + signedHeaders + "\n" + payloadHash;

        String stringToSign = ALGORITHM + "\n" + amzDate + "\n" + credentialScope + "\n" + hash(canonicalRequest);

        byte[] signingKey = getSignatureKey(credentials.secretAccessKey(), shortDate, REGION.id(), SERVICE);
        String signature = BinaryUtils.toHex(hmacSha256(stringToSign, signingKey));

        String url = "https://" + host + "/?" + getCanonicalQueryString(credentials, amzDate, credentialScope) +
                "&X-Amz-Signature=" + signature;

        return "k8s-aws-v1." + Base64.getUrlEncoder().encodeToString(url.getBytes(StandardCharsets.UTF_8));
    }

    private static String getCanonicalQueryString(AwsCredentials credentials, String amzDate, String credentialScope) throws Exception {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("Action", "GetCallerIdentity");
        queryParams.put("Version", "2011-06-15");
        queryParams.put("X-Amz-Algorithm", ALGORITHM);
        queryParams.put("X-Amz-Credential", URLEncoder.encode(credentials.accessKeyId() + "/" + credentialScope, StandardCharsets.UTF_8.toString()));
        queryParams.put("X-Amz-Date", amzDate);
        queryParams.put("X-Amz-Expires", "60");
        queryParams.put("X-Amz-SignedHeaders", "host;x-k8s-aws-id");

        return queryParams.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }

    private static byte[] getSignatureKey(String key, String dateStamp, String regionName, String serviceName) throws Exception {
        byte[] kSecret = ("AWS4" + key).getBytes(StandardCharsets.UTF_8);
        byte[] kDate = hmacSha256(dateStamp, kSecret);
        byte[] kRegion = hmacSha256(regionName, kDate);
        byte[] kService = hmacSha256(serviceName, kRegion);
        return hmacSha256("aws4_request", kService);
    }

    private static byte[] hmacSha256(String data, byte[] key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key, "HmacSHA256"));
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    private static String hash(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(text.getBytes(StandardCharsets.UTF_8));
        return BinaryUtils.toHex(hashBytes);
    }
}
