package com.codecool.queststore;

import com.codecool.queststore.controllers.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) throws IOException {
        LoginController loginController = new LoginController();
        MentorController mentorController = new MentorController();
        AdminController adminController = new AdminController();
        StudentController studentController = new StudentController();

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        //TODO determine appropriate paths and make controller for them
        server.createContext("/static", new StaticHandler());
        server.createContext("/", loginController); //done static present TODO return form to provide login or change path if logged
        server.createContext("/login", loginController); // TODO receive login data
        server.createContext("/logout", loginController); // TODO logout - remove cookie set sessionID non active
        server.createContext("/student", studentController); //done twig present TODO return student main page (profile)
        server.createContext("/mentor", mentorController); //done twig present ?twigOrStatic todo return mentor start page
        server.createContext("/admin", adminController); //done twig present ?twigOrStatic todo return admin start page

        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
