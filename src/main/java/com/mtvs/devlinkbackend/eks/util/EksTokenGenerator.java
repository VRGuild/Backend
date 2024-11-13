package com.mtvs.devlinkbackend.eks.util;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sts.StsClient;

public class EksTokenGenerator {
    private static final Region REGION = Region.AP_NORTHEAST_2; // 서울 리전

    public static StsClient createStsClient() {
        return StsClient.builder()
                .region(REGION)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }
}
