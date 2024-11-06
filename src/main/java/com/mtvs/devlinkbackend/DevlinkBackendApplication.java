package com.mtvs.devlinkbackend;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class DevlinkBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevlinkBackendApplication.class, args);
    }
    @PostConstruct
    public void init() {
        String template = "mongodb://%s:%s@%s/devlink?ssl=true&replicaSet=rs0&readpreference=%s";
        String username = "ebroot";
        String password = "qazwsx1357*";
        String clusterEndpoint = "docdb-2024-11-01-16-21-04.cluster-cpiycgkkk147.ap-northeast-2.docdb.amazonaws.com";
        String readPreference = "secondaryPreferred";
        String connectionString = String.format(template, username, password, clusterEndpoint, readPreference);

        String truststore = "classpath:rds-truststore.jks";
        String truststorePassword = "qazwsx1357*";

        System.setProperty("javax.net.ssl.trustStore", truststore);
        System.setProperty("javax.net.ssl.trustStorePassword", truststorePassword);

        MongoClient mongoClient = MongoClients.create(connectionString);

        MongoDatabase testDB = mongoClient.getDatabase("devlink");
    }
}
