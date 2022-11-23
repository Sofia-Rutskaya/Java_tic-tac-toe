package com.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.example.utils.CacheUtils;
import com.example.utils.RequestHelper;
import software.amazon.awssdk.services.apigatewaymanagementapi.ApiGatewayManagementApiAsyncClient;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GetAllConnectionFunction implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final CacheUtils cache = new CacheUtils(System.getenv("HOST"),
            Integer.valueOf(System.getenv("PORT")));


    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
        //Return all connected username
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        try{
            Set<String> key = cache.getClient().keys(CacheUtils.PREFIX_CONNECTION_PLAYER + "*");
            List<String> users = key.stream().map(s -> s.substring(s.indexOf(":") + 1)).collect(Collectors.toList());
            return response.withStatusCode(200).withBody(RequestHelper.writeToBody(users));
        } catch (IOException e) {
            logger.log(e.getMessage());
            return response.withStatusCode(400);
        }


    }
}
