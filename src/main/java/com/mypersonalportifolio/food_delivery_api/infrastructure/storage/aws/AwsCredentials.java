package com.mypersonalportifolio.food_delivery_api.infrastructure.storage.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record AwsCredentials(
        @Value("${application.storage.aws-s3.access-key}") String accessKey,
        @Value("${application.storage.aws-s3.secret}") String secret,
        @Value("${application.storage.aws-s3.region}") String region
) {}
