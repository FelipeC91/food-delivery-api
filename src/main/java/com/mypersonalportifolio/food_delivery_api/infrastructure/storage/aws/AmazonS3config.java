package com.mypersonalportifolio.food_delivery_api.infrastructure.storage.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.core.retry.RetryMode;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;

import java.time.Duration;

@Configuration
public class AmazonS3config {

    @Autowired
    private AwsCredentials awsCredentials;

    @Bean
    public S3Client provideS3Client() {
        var basicCredentials = AwsBasicCredentials.create(awsCredentials.accessKey(), awsCredentials.secret());

        var overrideConfig = ClientOverrideConfiguration.builder()
                .apiCallTimeout(Duration.ofMinutes(2))
                .apiCallAttemptTimeout(Duration.ofSeconds(90))
                .retryStrategy(RetryMode.STANDARD)
                .build();

        return S3Client.builder()
                .region(Region.of(awsCredentials.region()))
                .credentialsProvider(StaticCredentialsProvider.create(basicCredentials))
                .overrideConfiguration(overrideConfig)
                .build();
    }
}
