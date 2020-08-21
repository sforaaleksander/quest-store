package com.codecool.queststore.controllers;

import com.codecool.queststore.view.StaticView;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class StaticController implements HttpHandler {

    private StaticView staticView = new StaticView();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        URI uri = httpExchange.getRequestURI();
        System.out.printf("Looking for %s\n", uri.getPath());
        String path = "./" + uri.getPath();

        System.out.println("path: " + path);
        URL fileURL = getClass().getClassLoader().getResource(path);
        staticView.sendFile(httpExchange, fileURL);
    }
}
