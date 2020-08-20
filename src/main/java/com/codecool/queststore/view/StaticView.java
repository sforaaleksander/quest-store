package com.codecool.queststore.view;

import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;

public class StaticView {

    public void loadFormToProvideLogin(HttpExchange httpExchange) throws IOException {
        URL fileURL = getClass().getClassLoader().getResource("./static/html/signInRegister.html");
        sendFile(httpExchange, fileURL);
    }

    private void sendFile(HttpExchange httpExchange, URL fileURL) throws IOException {

        File file = new File(fileURL.getFile());
        String mimeType = Files.probeContentType(file.toPath());

        httpExchange.getResponseHeaders().set("Content-type", mimeType);
        httpExchange.sendResponseHeaders(200, 0);

        OutputStream os = httpExchange.getResponseBody();

        FileInputStream fs = new FileInputStream(file);
        final byte[] buffer = new byte[0x10000];
        int count = 0;
        while((count = fs.read(buffer)) >= 0) {
            os.write(buffer,0,count);
        }
        os.close();
    }


}
