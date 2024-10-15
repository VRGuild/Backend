package com.mtvs.devlinkbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class DevlinkBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevlinkBackendApplication.class, args);
    }

}
