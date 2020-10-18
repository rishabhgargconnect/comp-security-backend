package com.example.ComputerSecurity.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {

    final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

    @Bean
    public DynamoDB clientWrapper() {
        final DynamoDB dynamoDBClient = new DynamoDB(client);
        return dynamoDBClient;
    }

    @Bean
    public AmazonDynamoDB client() {
        return client;
    }
}
