package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.security.resource_server;


import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
@EnableCaching
public class HazelcastCacheConfig {

    @Bean
    public HazelcastInstance hazelcastInstance() {
        Config config = new Config();
        config.setClusterName("rate-limit-cluster");

        MapConfig bucketCacheConfig = new MapConfig("filterConfigCache");
        config.addMapConfig(bucketCacheConfig);

        return Hazelcast.newHazelcastInstance(config);
    }
}
