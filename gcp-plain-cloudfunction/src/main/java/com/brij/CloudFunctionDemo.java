package com.brij;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class CloudFunctionDemo implements HttpFunction {
    Gson gson = new Gson();

    @Override
    public void service(HttpRequest request, HttpResponse response)
            throws Exception {
        log.info("Handling  request {}{}", request.getMethod(), request.getPath());

        switch (request.getMethod()) {
            case "GET":
                log.info("Handling get request");
                handleGet(request, response);
                break;
            case "POST":
                log.info("Handling post request");
                User user = gson.fromJson(request.getReader(), User.class);
                log.info("User: {}", user);
                handlePost(user, response);
                break;

            default:
                log.info("No supported method found {}", request.getMethod());
                response.setStatusCode(405); // Method Not Allowed
                response.getWriter().write("Method not allowed");
                break;
        }

    }

    private void handlePost(User user, HttpResponse response) throws IOException {
        log.info("Creating user {}", user);
        UserHelper.addUser(user);
        log.info("Added user {}", user);
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(user));

    }

    private void handleGet(HttpRequest request, HttpResponse response) throws IOException {
        log.info("Handling get request for user {}", request.getPath());
        String userId = getPathVariable(request.getPath(), "users/(\\w+)");
        User user = UserHelper.getUser(userId);
        if (Objects.isNull(user)) {
            response.setStatusCode(404);
            response.getWriter().write(String.format("User not found for id %s", userId));
        } else {
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(user));
        }
    }

    public String getPathVariable(String path, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            String group = matcher.group(1);
            log.info("Id {}", group);
            return group;
        }
        return null;
    }

}
