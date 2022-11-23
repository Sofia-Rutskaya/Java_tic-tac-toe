package com.example.utils;

import com.example.model.User;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DDBUtils {
    //Make wort with DynamoDB

    private final DynamoDbEnhancedClient dbEnhancedClient;
    private final String tableName;

    public DDBUtils(String tableName,  String region) {
        this.tableName = tableName;
        DynamoDbClient client = DynamoDbClient.builder()
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .region(Region.of(region))
                .build();
        dbEnhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(client).build();
    }


    public String addRankRecord(User user){
        DynamoDbTable<User> table = dbEnhancedClient.table(tableName, TableSchema.fromBean(User.class));
        return table.updateItem(user).getNickName();
    }

    public User getRankRecord(String id){
        DynamoDbTable<User> table = dbEnhancedClient.table(tableName, TableSchema.fromBean(User.class));
        Key key = Key.builder()
                .partitionValue(id)
                .build();

        return table.getItem(key);
    }
}
