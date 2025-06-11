package com.brij;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;


public class HelloHttpFunction implements HttpFunction {
    private static final Logger logger = LoggerFactory.getLogger(HelloHttpFunction.class);

    @Override
    public void service(HttpRequest request, HttpResponse response)
            throws Exception {
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        JsonObject requestBody = gson.fromJson(reader, JsonObject.class);
        logger.info("Request received with body :{} /n path {} /n and method {}",
                requestBody, request.getPath(), request.getMethod());
        JsonObject responseJson = new JsonObject();
        responseJson.add("inputPayload", requestBody);
        responseJson.addProperty("message", "Hello, from code with B!");
        response.setContentType("application/json");
        response.getWriter().write(responseJson.toString());
    }
}