package com.codecool.queststore.controllers;

import com.codecool.queststore.control.services.LoginService;
import com.codecool.queststore.dao.user.User;
import com.codecool.queststore.helpers.CookieHelper;
import com.codecool.queststore.view.LoginView;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.URI;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    static LoginService loginService;

    @Mock
    static LoginView loginView;

    //@Mock
    static CookieHelper cookieHelper;

    @Mock
    static HttpExchange httpExchange;

    @Mock
    static OutputStream outputStream;

    @InjectMocks
    static LoginController loginController;

    static Headers headers;
    static User user;
    static Optional<HttpCookie> cookie;

    void setGeneralMocks() {
        //when(cookieHelper.getSessionIdCookie(httpExchange)).thenReturn(cookie);
        when(httpExchange.getResponseHeaders()).thenReturn(headers);
    }

    @BeforeEach
    void setUp() {
        user = new User();
        headers = new Headers();
        loginController = new LoginController(loginService, loginView);
    }

    private String getSessionIdFromCookie(HttpCookie cookie) {
        return cookie.getValue().replace("\"", "");
    }

    @Test
    void should_RedirectToLoginPage_when_NoEndpointProvided() throws IOException {
        //setGeneralMocks();
//        when(loginController.getUserFromCookie(httpExchange)).thenReturn(Optional.of(user));
        when(httpExchange.getRequestMethod()).thenReturn("GET");
        when(httpExchange.getRequestURI()).thenReturn(URI.create(""));
        when(httpExchange.getResponseBody()).thenReturn(outputStream);
        headers = new Headers();
        headers.set("Location", "/login");
        when(httpExchange.getRequestHeaders()).thenReturn(headers);
        when(httpExchange.getResponseHeaders()).thenReturn(headers);
//        when(loginView.redirect(httpExchange, "/login")).thenReturn();)
        //when(loginController.accessGetUserFromCookie(httpExchange)).thenReturn(null);

        loginController.handle(httpExchange);

        assertAll(
                () -> verify(httpExchange, times(1)).getRequestMethod(),
                () -> verify(httpExchange, times(1)).sendResponseHeaders(302, 0),
                () -> verify(outputStream, times(1)).write(any()),
                () -> verify(outputStream, times(1)).close()
        );
    }

























}