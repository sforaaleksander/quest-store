package com.codecool.queststore;

import com.codecool.queststore.controllers.AdminController;
import com.codecool.queststore.controllers.LoginController;
import com.codecool.queststore.controllers.MentorController;
import com.codecool.queststore.controllers.StudentController;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        // set routes
        server.createContext("/student", new StudentController());
        server.createContext("/mentor", new MentorController());
        server.createContext("/admin", new AdminController());
        server.createContext("/login", new LoginController());

        // start
        server.start();
    }
}
