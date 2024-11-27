package com.mtvs.devlinkbackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;
import software.amazon.awssdk.services.sts.model.AssumeRoleResponse;
import software.amazon.awssdk.utils.BinaryUtils;
import software.amazon.awssdk.utils.Md5Utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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

        System.out.println(clusterEndpoint);

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
        ProcessBuilder processBuilder = new ProcessBuilder(
                "aws", "eks", "get-token",
                "--cluster-name", "devlink-eks",
                "--region", "ap-northeast-2"
        );

        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line);
        }
        process.waitFor();

        // JSON 출력에서 토큰 추출
        String jsonOutput = output.toString();
        System.out.println("CLI Token Response: " + jsonOutput);

        String token = new ObjectMapper()
                .readTree(jsonOutput)
                .path("status")
                .path("token")
                .asText();

        System.out.println("Generated EKS Token: " + token);
        return token;
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
}
