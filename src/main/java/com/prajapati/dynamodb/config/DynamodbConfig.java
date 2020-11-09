package com.prajapati.dynamodb.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.util.Arrays;

@Configuration
public class DynamodbConfig {

    public static final String SERVICE_ENDPOINT = "dynamodb.us-east-1.amazonaws.com";
    public static final String REGION = "us-east-1";

    @Value("${ACCESS_KEY:default}")
    String ACCESS_KEY;

    @Value("${SECRET_KEY:default}")
    String SECRET_KEY;

    @Bean
    public DynamoDBMapper mapper() {
        AmazonDynamoDB amazonDynamoDB=amazonDynamoDBConfig();
        createTable("person","personId",amazonDynamoDB);
        return new DynamoDBMapper(amazonDynamoDB);
    }

    private AmazonDynamoDB amazonDynamoDBConfig() {
        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(SERVICE_ENDPOINT, REGION))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY))).build();
    }

    private void createTable(String tableName, String partitionKeyName, AmazonDynamoDB client){
        DynamoDB dynamoDB = new DynamoDB(client);
        try {
            System.out.println("Checking if table "+ tableName+" already existed...");
            dynamoDB.getTable(tableName).describe();
            System.out.println("Table "+tableName+ " already exists...");
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            System.out.println(tableName+ " does not exist.");
            System.out.println("Attempting to create table; please wait...");
            try {
                Table table = dynamoDB.createTable(tableName,
                        Arrays.asList(new KeySchemaElement(partitionKeyName, KeyType.HASH)), // Partition key
                        Arrays.asList(new AttributeDefinition(partitionKeyName, ScalarAttributeType.S)),
                        new ProvisionedThroughput(10L, 10L));
                table.waitForActive();
                System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());
            }
            catch (Exception e){
                System.err.println("Unable to create table: ");
                System.err.println(e.getMessage());
            }
        }
    }
}
