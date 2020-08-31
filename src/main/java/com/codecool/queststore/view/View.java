package com.codecool.queststore.view;

import com.sun.net.httpserver.HttpExchange;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;

public abstract class View {
    protected void loadPageTemplate(HttpExchange exchange, String twigPath, Map<String, Object> objectsToRender) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(twigPath);
        JtwigModel model = JtwigModel.newModel();

        for (Map.Entry<String, Object> entry : objectsToRender.entrySet()) {
            model.with(entry.getKey(), entry.getValue());
        }
        send200(template.render(model), exchange);
    }

    protected void send200(String response, HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    protected void send404(HttpExchange httpExchange) throws IOException {
        String response = "404 (Not Found)\n";
        httpExchange.sendResponseHeaders(404, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public void sendStaticPage(HttpExchange httpExchange, String filePath) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL fileURL = classLoader.getResource("static/html/signInRegister.html");

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

    public void redirect(HttpExchange httpExchange, String redirection) throws IOException {
        httpExchange.getResponseHeaders().set("Location", redirection);
        httpExchange.sendResponseHeaders(302, 0);
    }
}
