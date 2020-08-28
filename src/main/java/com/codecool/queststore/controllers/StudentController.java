package com.codecool.queststore.controllers;

import com.codecool.queststore.control.services.LoginService;
import com.codecool.queststore.control.services.ShopService;
import com.codecool.queststore.control.services.TradingService;
import com.codecool.queststore.control.services.models.UserRoleType;
import com.codecool.queststore.dao.balance.Balance;
import com.codecool.queststore.dao.user.User;
import com.codecool.queststore.helpers.CookieHelper;
import com.codecool.queststore.view.StudentView;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
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

        Optional<User> loggedUserOptional = validateSession(exchange);
        if (loggedUserOptional.isEmpty()) return;
        User loggedUser = loggedUserOptional.get();

        String method = exchange.getRequestMethod(); // "POST" or "GET"

        List<String> uriList = Arrays.stream(exchange.getRequestURI().getPath().split("/"))
                .collect(Collectors.toList());

        uriList.forEach(System.out::println);

        if (method.equals("GET")) {
            if (displayStartScreen(exchange, loggedUser, uriList)) return;
            if (displayStore(exchange, uriList)) return;
            if (displayWallet(exchange, loggedUser, uriList)) return;
        }

        if (method.equals("POST")) {
            String nameObjectToAdd = uriList.get(4);
            if (addArtifactToBuy(exchange, loggedUser, nameObjectToAdd)) return;
            if (addQuestToDo(exchange, loggedUser, nameObjectToAdd)) return;
        }

        sendNotFoundResponse(exchange);

    }

    private Optional<User> validateSession(HttpExchange exchange) throws IOException {
        loginService.logoutNonActiveUsers();
        Optional<HttpCookie> cookie = new CookieHelper().getSessionIdCookie(exchange);
        if (cookie.isEmpty()) {
            return Optional.empty();
        }

        String sessionId = cookie.get().getValue();
        sessionId = sessionId.replace("\"", "");
        Optional<User> loggedUser = loginService.getLoggedUserBySessionId(sessionId);
        if (loggedUser.isEmpty()) {
            redirection(exchange, "../quest-store");
            return loggedUser;
        }
        loginService.extendLoginTime(loggedUser.get());
        UserRoleType userRoleType = UserRoleType.getById(loggedUser.get().getId());
        switch (userRoleType) {
            case ADMIN:
            case MENTOR:
                redirection(exchange, "../quest-store/" + userRoleType.toString().toLowerCase());
                return Optional.empty();
        }
        return loggedUser;
    }

    private boolean addArtifactToBuy(HttpExchange exchange, User loggedUser, String nameObjectToAdd) {
        if (nameObjectToAdd.equals("item")) {
            buyArtifact(exchange, loggedUser);
            return true;
        }
        return false;
    }

    private boolean addQuestToDo(HttpExchange exchange, User loggedUser, String nameObjectToAdd) {
        if (nameObjectToAdd.equals("quest")) {
            doQuest(exchange, loggedUser);
            return true;
        }
        return false;
    }

    private boolean displayWallet(HttpExchange exchange, User loggedUser, List<String> uriList) throws IOException {
        if (uriList.get(3).equals("wallet") && uriList.size() == 4) {
            seeWallet(exchange, loggedUser);
            return true;
        }
        return false;
    }

    private boolean displayStore(HttpExchange exchange, List<String> uriList) throws IOException {
        if (uriList.get(3).equals("store")) {
            String nameOfObjectsToDisplay = uriList.get(4);
            if (displayQuestStore(exchange, nameOfObjectsToDisplay)) return true;
            return displayArtifactStore(exchange, nameOfObjectsToDisplay);
        }
        return false;
    }

    private boolean displayQuestStore(HttpExchange exchange, String nameOfObjectsToDisplay) throws IOException {
        if (nameOfObjectsToDisplay.equals("item")) {
            displayAllArtifacts(exchange);
            return true;
        }
        return false;
    }

    private boolean displayArtifactStore(HttpExchange exchange, String nameOfObjectsToDisplay) throws IOException {
        if (nameOfObjectsToDisplay.equals("quest")) {
            displayAllQuests(exchange);
            return true;
        }
        return false;
    }

    private boolean displayStartScreen(HttpExchange exchange, User loggedUser, List<String> uriList) throws IOException {
        if (uriList.get(2).equals("student") && uriList.size() == 3) {
            getStartScreen(exchange, loggedUser);
            return true;
        }
        return false;
    }

    private void displayAllQuests(HttpExchange exchange) throws IOException {
        studentView.loadTemplateWithAllQuest(exchange, shopService.getAllQuests());
    }

    private void displayAllArtifacts(HttpExchange exchange) throws IOException {
        studentView.loadTemplateWithAllArtifacts(exchange, shopService.getAllItems());
    }

    private void doQuest(HttpExchange exchange, User user) {
        int questId = 0; // todo set parameters from exchange
        tradingService.addUserQuest(questId, user.getId());
    }

    private void getStartScreen(HttpExchange exchange, User user) throws IOException {
        Balance balance = tradingService.getBalanceByUserId(user.getId());
        studentView.loadMainPageTemplateWithStudent(exchange, user, balance);
    }

    private void seeWallet(HttpExchange exchange, User user) throws IOException {
        Balance balance = tradingService.getBalanceByUserId(user.getId());
        studentView.loadTemplateWithWallet(exchange, tradingService.makeStudentWallet(user.getId()), balance);
    }

    private void buyArtifact(HttpExchange exchange, User user) {
        int artifactId = 0; // todo artifact id
        tradingService.addUserItem(artifactId, user.getId());
    }

    // private void seeExperienceLevel(HttpExchange exchange, User user) {}

    private void redirection(HttpExchange httpExchange, String path) throws IOException {
        httpExchange.getResponseHeaders().set("Location", path);
        httpExchange.sendResponseHeaders(302, 0);
    }

    private void sendNotFoundResponse(HttpExchange exchange) throws IOException {
        String response = "Source not found";
        exchange.sendResponseHeaders(404, response.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }

}
