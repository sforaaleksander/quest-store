package com.codecool.queststore.helpers;

import com.sun.net.httpserver.HttpExchange;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CookieHelper {
    private static final String SESSION_COOKIE_NAME = "sessionId";

    public void createNewCookie(HttpExchange httpExchange, String sessionId) {
        Optional<HttpCookie> cookie = getSessionIdCookie(httpExchange);

        cookie = Optional.of(new HttpCookie(SESSION_COOKIE_NAME, sessionId));
        httpExchange.getResponseHeaders().add("Set-Cookie", cookie.get().toString());
    }

    public void deleteCookie(HttpExchange httpExchange, String sessionId) {
        Optional<HttpCookie> cookie;
        cookie = Optional.of(new HttpCookie(SESSION_COOKIE_NAME, sessionId));
        httpExchange.getResponseHeaders().remove("Set-Cookie", cookie.get().toString());
    }

    public Optional<HttpCookie> getSessionIdCookie(HttpExchange httpExchange) {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = parseCookies(cookieStr);
        return findCookieByName(SESSION_COOKIE_NAME, cookies);
    }

    private List<HttpCookie> parseCookies(String cookieString) {
        List<HttpCookie> cookies = new ArrayList<>();
        if (cookieString == null || cookieString.isEmpty()) {
            return cookies;
        }
        for (String cookie : cookieString.split(";")) {
            int indexOfEq = cookie.indexOf('=');
            String cookieName = cookie.substring(0, indexOfEq);
            String cookieValue = cookie.substring(indexOfEq + 1);
            cookies.add(new HttpCookie(cookieName, cookieValue));
        }
        return cookies;
    }

    private Optional<HttpCookie> findCookieByName(String name, List<HttpCookie> cookies) {
        for (HttpCookie cookie : cookies) {
            if (cookie.getName().equals(name))
                return Optional.of(cookie);
        }
        return Optional.empty();
    }
}
