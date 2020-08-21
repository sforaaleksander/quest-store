package com.codecool.queststore.controllers;

import com.codecool.queststore.control.services.LoginService;
import com.codecool.queststore.control.services.ShopService;
import com.codecool.queststore.control.services.TradingService;
import com.codecool.queststore.control.services.UserService;
import com.codecool.queststore.control.services.models.TradableObject;
import com.codecool.queststore.control.services.models.UserRoleType;
import com.codecool.queststore.dao.user.User;
import com.codecool.queststore.view.MentorView;
import com.codecool.queststore.view.StaticView;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MentorController implements HttpHandler {

    private final MentorView mentorView = new MentorView();
    private final StaticView staticView = new StaticView();
    private final UserService userService = new UserService();
    private LoginService loginService = new LoginService();
    private ShopService shopService = new ShopService();
    private TradingService tradingService = new TradingService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //"/quest-store/mentor";                                GET
        //"/quest-store/mentor/new/student";                    POST
        //"/quest-store/mentor/form/new/student";               GET

        //"/quest-store/mentor/new/student-classroom";          POST
        //"/quest-store/mentor/form/new/student-classroom";     GET
        //"/quest-store/mentor/form/new/students-new-classroom" GET

        //"/quest-store/mentor/new/quest";                      POST
        //"/quest-store/mentor/form/new/quest";                 GET
        //"/quest-store/mentor/new/item";                       POST
        //"/quest-store/mentor/form/new/item";                  GET

        //"/quest-store/mentor/store/quest";                     GET
        //"/quest-store/mentor/form/current/quest";              GET
        //"/quest-store/mentor/updated/item";                   POST

        //"/quest-store/mentor/store/item";                     GET
        //"/quest-store/mentor/form/current/item";              GET
        //"/quest-store/mentor/updated/item";                   POST
        //"/quest-store/mentor/split/quest";                    ??
        //todo "/quest-store/mentor/not-marked/store/items";         GET
        //todo "/quest-store/mentor/not-marked/store/quests";        GET
        //"/quest-store/mentor/not-marked/quest";               POST
        //"/quest-store/mentor/not-marked/item";                POST
        //todo "/quest-store/mentor/students/wallets";               GET

        Optional<User> loggedUserOptional = validateSession(exchange);
        if (loggedUserOptional.isEmpty()) return;
        User loggedUser = loggedUserOptional.get();

        String method = exchange.getRequestMethod(); // "POST" or "GET"

        List<String> uriList = Arrays.stream(exchange.getRequestURI().getPath().split("/"))
                .collect(Collectors.toList());

        if (method.equals("GET")) {
            if (displayMentorMainPage(exchange, loggedUser, uriList)) return;
            if (displayForms(exchange, uriList)) return;
            if (displayStores(exchange, uriList)) return;
            if (uriList.get(3).equals("not-marked")) {
                switch (uriList.get(5)) {
                    case "items":
                        return;
                }
            }

        }

        sendNotFoundResponse(exchange);
    }

    private boolean displayStores(HttpExchange exchange, List<String> uriList) {
        if (uriList.get(3).equals("store")) {
            switch (uriList.get(4)) {
                case "item":
                    mentorView.displayAllItemsWhichAreAbleToEdit(exchange, shopService.getAllItems());
                    return true;
                case "quest":
                    mentorView.displayAllQuestsWhichAreAbleToEdit(exchange, shopService.getAllQuests());
                    return true;
            }
        }
        return false;
    }

    private boolean displayForms(HttpExchange exchange, List<String> uriList) throws IOException {
        if (uriList.get(3).equals("form")) {
            if (displayAllNewForms(exchange, uriList)) return true;
            return displayFormForCurrentObjectToEdit(uriList);
        }
        return false;
    }

    private boolean displayFormForCurrentObjectToEdit(List<String> uriList) {
        if (uriList.get(4).equals("current")) {
            switch (uriList.get(5)) {
                case "item":
                    int itemId = 0; // TODO get item id from exchange
                    mentorView.loadCurrentItemToEdit(shopService.getItem(itemId));
                    return true;
                case "quest":
                    int questId=0; //TODO get quest id from exchange
                    mentorView.loadCurrentQuestToEdit(shopService.getQuest(questId));
                    return true;
            }
        }
        return false;
    }

    private boolean displayAllNewForms(HttpExchange exchange, List<String> uriList) throws IOException {
        if (uriList.get(4).equals("new")) {
            switch (uriList.get(5)) {
                case "student":
                    staticView.receiveFormToProvideNewStudent(exchange);
                    return true;
                case "student-classroom":
                    // TODO get user from exchange
                    int studentId = 0;
                    mentorView.loadTemplateWithAssignStudentToClassroom(exchange,
                            userService.getStudent(studentId), userService.getAllClassrooms());
                    return true;
                case "student-new-classroom":
                    mentorView.loadTemplateWithAllStudents(exchange, userService.getAllStudents());
                case "item":
                    staticView.receiveFormToProvideNewItem(exchange);
                    return true;
                case "quest":
                    staticView.receiveFormToProvideNewQuest(exchange);
                    return true;
            }
        }
        return false;
    }

    private boolean displayMentorMainPage(HttpExchange exchange, User loggedUser, List<String> uriList) throws IOException {
        if (uriList.get(2).equals("mentor") && uriList.size() == 3) {
            getStartScreen(exchange, loggedUser);
            return true;
        }
        return false;
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

    private void addStudent(String json) {
    }

    private void addQuest(String json) {
    }

    private void splitQuests() {

    }

    private void addStoreItem(String json) {
    }

    private void editStoreItem(String json) {
    }

    private void markAchievedQuests(String json) {
    }

    private void markBoughtArtifacts(String json) {
    }

    private File seeStudentsWallets() {
        return null;
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
