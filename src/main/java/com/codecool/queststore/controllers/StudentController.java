package com.codecool.queststore.controllers;

import com.codecool.queststore.control.services.LoginService;
import com.codecool.queststore.control.services.ShopService;
import com.codecool.queststore.control.services.TradingService;
import com.codecool.queststore.dao.quests.Quest;
import com.codecool.queststore.dao.user.User;
import com.codecool.queststore.view.StudentView;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentController implements HttpHandler {

    private final StudentView studentView = new StudentView();
    private final ShopService shopService = new ShopService();
    private final TradingService tradingService = new TradingService();
    private final LoginService loginService = new LoginService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // routes to handle
        //"/quest-store/student"
        //"/quest-store/student/new/item"
        //"/quest-store/student/new/quest"
        //"/quest-store/student/store/items"
        //"/quest-store/student/store/quests"
        //"/quest-store/student/wallet"

        // todo validate session
        // todo check if user is student

        Optional<User> loggedUser = loginService.getLoggedUserBySessionId("");

        if (loggedUser.isEmpty()) {
            sendNotFoundResponse(exchange);
            return;
        }

        String method = exchange.getRequestMethod(); // "POST" or "GET"

        List<String> uriList = Arrays.stream(exchange.getRequestURI().getPath().split("/"))
                .collect(Collectors.toList());

        uriList.forEach(System.out::println);

        if (method.equals("GET")) {
            if (uriList.size() == 3) {
                getStartScreen(exchange);
                return;
            }
            if (uriList.get(3).equals("store")) {
                String nameOfObjectsToDisplay = uriList.get(4);
                if (nameOfObjectsToDisplay.equals("item")) {
                    displayAllArtifacts(exchange);
                    return;
                }
                if (nameOfObjectsToDisplay.equals("quest")) {
                    displayAllQuests(exchange);
                    return;
                }
            }
        }

        if (method.equals("POST")) {
            String nameObjectToAdd = uriList.get(4);
            if (nameObjectToAdd.equals("item")) {
                buyArtifact(exchange);
                return;
            }
            if (nameObjectToAdd.equals("quest")) {
                doQuest(exchange);
                return;
            }
        }

        sendNotFoundResponse(exchange);

    }

    private void displayAllQuests(HttpExchange exchange) {
        studentView.loadTemplateWithAllQuest(exchange, shopService.getAllQuests());
    }

    private void displayAllArtifacts(HttpExchange exchange) {
        studentView.loadTemplateWithAllArtifacts(exchange, shopService.getAllItems());
    }

    private void doQuest(HttpExchange exchange) {
        int questId = 0; // todo set parameters from exchange
        int userId = 0;
        tradingService.addUserQuest(questId, userId);
    }

    private void getStartScreen(HttpExchange exchange) {

        //studentView.loadStartScreenTemplateWithUser()
    }

    private File seeWallet(String json) {
        return null;
    }

    private void buyArtifact(HttpExchange json) {
    }

    private File seeExperienceLevel(String json) {
        return null;
    }

    private void sendNotFoundResponse(HttpExchange exchange) throws IOException {
        String response = "Source not found";
        exchange.sendResponseHeaders(404, response.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }

}
