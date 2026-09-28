package com.mypersonalportifolio.food_delivery_api.infrastructure.storage;

import com.mypersonalportifolio.food_delivery_api.application.storage.PhotoStorageService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.storage.aws.AmazonS3PhotoStorageService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.storage.local.LocalPhotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageStrategyConfig {

    enum StorageApproach {
        S3,
        LOCAL
    }

    @Value("${application.storage.strategy}")
    private StorageApproach storageApproach;


    @Bean
    public PhotoStorageService provide() {
        if (StorageApproach.S3.equals(storageApproach))
            return new AmazonS3PhotoStorageService();
        else
            return new LocalPhotoStorageService();
    }
}
