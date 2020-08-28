package com.codecool.queststore.controllers;

import com.codecool.queststore.control.services.LoginService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Controller implements HttpHandler {
    private final LoginService loginService = new LoginService();
    private String context;
    private String requestUri;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        requestUri = httpExchange.getRequestURI().getPath();
        List<String> uriList = Arrays.stream(requestUri.split("/"))
                .collect(Collectors.toList());
        context = uriList.get(uriList.size() - 1);

        if (method.equals("GET")) {
            handleGet(httpExchange);

        } else if (method.equals("POST")) {
            handlePost(httpExchange);
        }
    }

    abstract void handleGet(HttpExchange httpExchange) throws IOException;

    abstract void handlePost(HttpExchange httpExchange) throws IOException;

    Map<String, String> parseFormData(String formData) {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
            map.put(keyValue[0], value);
        }
        return map;
    }

    Map<String, String> getInputs(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        System.out.println(formData);
        return parseFormData(formData);
    }
}
