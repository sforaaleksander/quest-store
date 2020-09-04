package com.codecool.queststore.controllers;

import com.codecool.queststore.control.services.LoginService;
import com.codecool.queststore.dao.user.User;
import com.codecool.queststore.dao.user.UserDao;
import com.codecool.queststore.helpers.CookieHelper;
import com.codecool.queststore.view.LoginView;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.URI;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    static LoginService loginService;

    @Mock
    static LoginView loginView;

    @Mock
    static HttpExchange httpExchange;

    @InjectMocks
    static LoginController loginController;

    static Headers headers;
    static User user;


    @BeforeEach
    void setUp() {
        user = new User();
        headers = new Headers();
        loginController = new LoginController(loginService, loginView);
    }


    @Test
    void should_RedirectToLoginPage_when_NoEndpointProvided() throws IOException {
        when(httpExchange.getRequestMethod()).thenReturn("GET");
        when(httpExchange.getRequestURI()).thenReturn(URI.create("/"));
        doCallRealMethod().when(loginView).redirect(httpExchange, "/login");
        when(httpExchange.getRequestHeaders()).thenReturn(headers);
        when(httpExchange.getResponseHeaders()).thenReturn(headers);

        loginController.handle(httpExchange);
        assertAll(
                () -> verify(httpExchange, times(1)).getRequestMethod(),
                () -> verify(httpExchange, times(1)).sendResponseHeaders(302, 0)
//                () -> verify(outputStream, times(1)).write(any()),
//                () -> verify(outputStream, times(1)).close()
        );

    }

    @Test
    void should_ProcessLogin_when_Login() throws IOException {
        when(httpExchange.getRequestMethod()).thenReturn("POST");
        User testUser = new User().setIdRole(1).setEmail("mateusz@gmail.com").setPassword("asd");
        when(loginService.login(testUser.getEmail(),testUser.getPassword())).thenReturn(Optional.of(new AbstractMap.SimpleEntry<>("someSessionId", testUser)));
        when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream(String.format("email=%s&password=%s",testUser.getEmail(),testUser.getPassword()).getBytes()));
        when(httpExchange.getResponseHeaders()).thenReturn(headers);
        when(httpExchange.getRequestURI()).thenReturn(URI.create(""));
        doCallRealMethod().when(loginView).redirect(httpExchange, "/" + loginController.accessGetUsersRole(testUser));

        loginController.handle(httpExchange);
        assertAll(
                () -> assertEquals("sessionId=\"someSessionId\"", headers.get("Set-Cookie").get(0)),
                () -> assertEquals("/student", headers.get("Location").get(0)),
                () -> verify(httpExchange, times(1)).getRequestMethod(),
                () -> verify(httpExchange, times(1)).sendResponseHeaders(302, 0)
        );
    }
























}