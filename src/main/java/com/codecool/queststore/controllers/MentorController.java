package com.codecool.queststore.controllers;

import com.codecool.queststore.control.services.LoginService;
import com.codecool.queststore.control.services.ShopService;
import com.codecool.queststore.control.services.TradingService;
import com.codecool.queststore.control.services.UserService;
import com.codecool.queststore.control.services.models.UserRoleType;
import com.codecool.queststore.dao.user.User;
import com.codecool.queststore.view.MentorView;
import com.codecool.queststore.view.StaticView;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MentorController implements HttpHandler {

    private final MentorView mentorView = new MentorView();
    private final StaticView staticView = new StaticView();
    private final UserService userService = new UserService();
    private LoginService loginService = new LoginService();
    private ShopService shopService = new ShopService();
    private TradingService tradingService = new TradingService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // path added "/quest-store/mentor/form/new/student";               GET
        // path added "/quest-store/mentor/form/new/student-classroom";     GET
        // path added "/quest-store/mentor/form/new/students-new-classroom" GET
        // path added "/quest-store/mentor/form/new/quest";                 GET
        // path added "/quest-store/mentor/form/new/item";                  GET
        // path added "/quest-store/mentor/form/current/quest";              GET
        // path added "/quest-store/mentor/form/current/item";              GET
        // path added "/quest-store/mentor";                                GET
        // path added "/quest-store/mentor/store/quest";                     GET
        // path added "/quest-store/mentor/store/item";                     GET
        // path added "/quest-store/mentor/store/not-marked/items";         GET
        // path added "/quest-store/mentor/store/not-marked/quests";        GET
        // path added "/quest-store/mentor/store/students-wallets";               GET
        // path added "/quest-store/mentor/new/student";                    POST
        // path added "/quest-store/mentor/new/student-classroom";          POST
        // path added "/quest-store/mentor/new/quest";                      POST
        // path added "/quest-store/mentor/new/item";                       POST
        // path added "/quest-store/mentor/updated/quest";                   POST
        // path added "/quest-store/mentor/updated/item";                   POST
        // path added "/quest-store/mentor/not-marked/quest";               POST
        // path added "/quest-store/mentor/not-marked/item";                POST

        //"/quest-store/mentor/split/quest";                    ??

        Optional<User> loggedUserOptional = validateSession(exchange);
        if (loggedUserOptional.isEmpty()) return;
        User loggedUser = loggedUserOptional.get();

        String method = exchange.getRequestMethod(); // "POST" or "GET"

        List<String> uriList = Arrays.asList(exchange.getRequestURI().getPath().split("/"));

        if (method.equals("GET")) {
            if (uriList.get(2).equals("mentor") && uriList.size() == 3) {
                getStartScreen(exchange, loggedUser);
                return;
            }
            switch (uriList.get(3)) {
                case "form":
                    displayForms(exchange, uriList);
                    return;
                case "store":
                    displayStores(exchange, uriList);
                    return;
            }
        }

        if (method.equals("POST")) {
            switch (uriList.get(3)) {
                case "new":
                    receiveNewForm(exchange, uriList);
                    return;
                case "updated":
                    receiveUpdatedForm(exchange, uriList);
                    return;
                case "not-marked":
                    markReceivedObject(exchange, uriList);
                    return;
            }
            redirection(exchange, "/quest-store/mentor");
        }

        sendNotFoundResponse(exchange);
    }

    private void markReceivedObject(HttpExchange exchange, List<String> uriList) {
        switch (uriList.get(4)) {
            case "item":
                markUsedArtifact(exchange);
                return;
            case "quest":
                markAchievedQuest(exchange);
        }
    }

    private void receiveUpdatedForm(HttpExchange exchange, List<String> uriList) {
        switch (uriList.get(4)) {
            case "quest":
                updateQuest(exchange);
                return;
            case "item":
                updateItem(exchange);
        }
    }

    private void receiveNewForm(HttpExchange exchange, List<String> uriList) {
        switch (uriList.get(4)) {
            case "student":
                addStudent(exchange);
                return;
            case "student-classroom":
                addStudentToClassroom(exchange);
                return;
            case "quest":
                addNewQuest(exchange);
                return;
            case "item":
                addNewItem(exchange);
        }
    }

    private void displayStores(HttpExchange exchange, List<String> uriList) {
        switch (uriList.get(4)) {
            case "item":
                mentorView.displayAllItemsWhichAreAbleToEdit(exchange, shopService.getAllItems());
                return;
            case "quest":
                mentorView.displayAllQuestsWhichAreAbleToEdit(exchange, shopService.getAllQuests());
                return;
            case "not-marked":
                displayNotMarkedStores(exchange, uriList);
                return;
            case "students-wallets":
                displayStudentsWallets();
        }
    }

    private void displayNotMarkedStores(HttpExchange exchange, List<String> uriList) {
        switch (uriList.get(5)) {
            case "item":
                mentorView.displayAllNotMarkedItems(exchange, tradingService.getAllNotMarkedItems());
                return;
            case "quest":
                mentorView.displayAllNotMarkedQuests(exchange, tradingService.getAllNotMarkedQuests());
        }
    }

    private void displayForms(HttpExchange exchange, List<String> uriList) throws IOException {
        switch (uriList.get(4)) {
            case "current":
                displayFormForCurrentObjectToEdit(exchange, uriList);
                return;
            case "new":
                displayAllNewForms(exchange, uriList);
        }
    }

    private void displayFormForCurrentObjectToEdit(HttpExchange exchange, List<String> uriList) {
        switch (uriList.get(5)) {
            case "item":
                int itemId = 0; // TODO get item id from exchange
                mentorView.loadCurrentItemToEdit(exchange, shopService.getItem(itemId));
                return;
            case "quest":
                int questId=0; //TODO get quest id from exchange
                mentorView.loadCurrentQuestToEdit(exchange, shopService.getQuest(questId));
        }
    }

    private void displayAllNewForms(HttpExchange exchange, List<String> uriList) throws IOException {
        switch (uriList.get(5)) {
            case "student":
                staticView.receiveFormToProvideNewStudent(exchange);
                return;
            case "student-classroom":
                // TODO get user from exchange
                int studentId = 0;
                mentorView.loadTemplateWithAssignStudentToClassroom(exchange,
                        userService.getStudent(studentId), userService.getAllClassrooms());
                return;
            case "student-new-classroom":
                mentorView.loadTemplateWithAllStudents(exchange, userService.getAllStudents());
            case "item":
                staticView.receiveFormToProvideNewItem(exchange);
                return;
            case "quest":
                staticView.receiveFormToProvideNewQuest(exchange);
        }
    }

    private Optional<User> validateSession(HttpExchange exchange) throws IOException {
        loginService.logoutNonActiveUsers();
        Optional<User> loggedUser = loginService.getLoggedUserBySessionId("");
        if (loggedUser.isEmpty()) {
            redirection(exchange, "/quest-store");
            return loggedUser;
        }
        loginService.extendLoginTime(loggedUser.get());
        UserRoleType userRoleType = UserRoleType.getById(loggedUser.get().getId());
        switch (userRoleType) {
            case STUDENT:
            case MENTOR:
                redirection(exchange, "/quest-store/" + userRoleType.toString().toLowerCase());
                return Optional.empty();
        }
        return loggedUser;
    }

    private void getStartScreen(HttpExchange exchange, User user) throws IOException {
        mentorView.loadMainPageTemplateWithMentor(exchange, user);
    }

    private void addStudent(HttpExchange exchange) {
        // todo implement
    }

    private void addStudentToClassroom(HttpExchange exchange) {
        // todo implement
    }

    private void addNewQuest(HttpExchange exchange) {
        // todo implement
    }

    private void splitQuests() {

    }

    private void addNewItem(HttpExchange exchange) {
        // todo implement
    }

    private void updateItem(HttpExchange exchange) {
        // todo implement
    }

    private void updateQuest(HttpExchange exchange) {
        // todo implement
    }

    private void markAchievedQuest(HttpExchange json) {
        // todo implement
    }

    private void markUsedArtifact(HttpExchange json) {
        // todo implement
    }

    private void displayStudentsWallets() {
        //todo implement method
        return;
    }

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
