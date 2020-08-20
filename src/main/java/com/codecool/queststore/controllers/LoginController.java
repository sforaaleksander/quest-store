package com.codecool.queststore.controllers;

import com.codecool.queststore.control.services.LoginService;
import com.codecool.queststore.control.services.models.UserRoleType;
import com.codecool.queststore.dao.user.User;
import com.codecool.queststore.helpers.CookieHelper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpCookie;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class LoginController implements HttpHandler {
    private final CookieHelper ch = new CookieHelper();
    private final LoginService loginService = new LoginService();
    private String context;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        List<String> uriList = Arrays.stream(httpExchange.getRequestURI().getPath().split("/"))
                .collect(Collectors.toList());
        context = uriList.get(uriList.size() - 1);

        if (method.equals("GET")) {
            handleGet(httpExchange);

        } else if (method.equals("POST")) {
            handlePost(httpExchange);
        }
    }

    private void handleGet(HttpExchange httpExchange) throws IOException {
        Optional<User> loggedUser = getUserFromCookie(httpExchange);
        if (context.equals("quest-store")) {
            redirectToLogin(httpExchange);
            return;
        }
        if (loggedUser.isPresent() && context.equals("login")) {
            redirectToUserMain(httpExchange, loggedUser.get());
            return;
        }
        if (context.equals("logout")){
            String sessionId = getSessionIdFromCookie(httpExchange).orElse("");
            ch.deleteCookie(httpExchange, sessionId);
            send404OrLoginPage(httpExchange);
        }
        send404OrLoginPage(httpExchange);
    }

    private void redirectToUserMain(HttpExchange httpExchange, User loggedUser) throws IOException {
        int id = loggedUser.getIdRole();
        String userRole = UserRoleType.getById(id).toString().toLowerCase();

        httpExchange.getResponseHeaders().set("Location", "quest-store/" + userRole);
        httpExchange.sendResponseHeaders(302, 0);
    }

    private void redirectToLogin(HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().set("Location", "quest-store/login");
        httpExchange.sendResponseHeaders(302, 0);
    }

    private Optional<User> getUserFromCookie(HttpExchange httpExchange) {
        User user = null;
        Optional<HttpCookie> cookie = ch.getSessionIdCookie(httpExchange);
        String sessionId;
        if (cookie.isPresent()) {
            sessionId = cookie.get().getValue();
            user = loginService.getLoggedUserBySessionId(sessionId).orElse(user);
        }
        return Optional.ofNullable(user);
    }

    private Optional<String> getSessionIdFromCookie(HttpExchange httpExchange) {
        Optional<HttpCookie> cookie = ch.getSessionIdCookie(httpExchange);
        String sessionId = null;
        if (cookie.isPresent()) {
            sessionId = cookie.get().getValue();
        }
        return Optional.ofNullable(sessionId);
    }

    private void send404OrLoginPage(HttpExchange httpExchange) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL fileURL = classLoader.getResource("signInRegister.html");
        if (fileURL == null) {
            send404(httpExchange);
        } else {
            sendLoginPage(httpExchange, fileURL);
        }
    }

    public void send404(HttpExchange httpExchange) throws IOException {
        String response = "404 (Not Found)\n";
        httpExchange.sendResponseHeaders(404, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void sendLoginPage(HttpExchange httpExchange, URL fileURL) throws IOException {
        File file = new File(fileURL.getFile());
        String mime = Files.probeContentType(file.toPath());

        httpExchange.getResponseHeaders().set("Content-Type", mime);
        httpExchange.sendResponseHeaders(200, 0);

        OutputStream os = httpExchange.getResponseBody();

        FileInputStream fs = new FileInputStream(file);
        final byte[] buffer = new byte[0x10000];
        int count;
        while ((count = fs.read(buffer)) >= 0) {
            os.write(buffer, 0, count);
        }
        os.close();
    }

    private void handlePost(HttpExchange httpExchange) throws IOException {
        Optional<User> loggedUser = getUserFromCookie(httpExchange);
        if (loggedUser.isEmpty() && context.equals("login")) {
            Map<String, String> inputs = getInputs(httpExchange);
            String email = inputs.get("email");
            String password = inputs.get("password");

            Optional<String> sessionId = loginService.login(email, password);
            if (sessionId.isPresent()) {
                User user = loginService.getLoggedUserBySessionId(sessionId.get()).get();
                ch.createNewCookie(httpExchange, sessionId.get());
                redirectToUserMain(httpExchange, user);
            }
        }
    }

    private Map<String, String> getInputs(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        System.out.println(formData);
        return parseFormData(formData);
    }

    private static Map<String, String> parseFormData(String formData) {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
            map.put(keyValue[0], value);
        }
        return map;
    }
}
