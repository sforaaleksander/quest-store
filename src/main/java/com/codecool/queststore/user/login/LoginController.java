package com.codecool.queststore.user.login;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;

public class LoginController implements HttpHandler {

    private LoginService loginService = new LoginService();
    private LoginView loginView = new LoginView();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }

    private File getLoginForm() {
        return loginView.getLoginForm();
    }

    private boolean createNewLoginSession(String json) {
        return loginService.createNewLoginSession(json);
    }

    private boolean validateCurrentLoginSession(String json) {
        return loginService.validateCurrentLoginSession(json);
    }


}
