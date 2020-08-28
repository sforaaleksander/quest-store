package com.codecool.queststore.view;

import com.codecool.queststore.control.services.models.TradableObject;
import com.codecool.queststore.control.services.models.TradableObjectType;
import com.codecool.queststore.control.services.models.Transaction;
import com.codecool.queststore.control.services.models.Wallet;
import com.codecool.queststore.dao.balance.Balance;
import com.codecool.queststore.dao.quests.Quest;
import com.codecool.queststore.dao.user.User;
import com.sun.net.httpserver.HttpExchange;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

public class StudentView {

    public void loadMainPageTemplateWithStudent(HttpExchange exchange, User student, Balance balance) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/studentMain.twig");
        JtwigModel model = JtwigModel.newModel().with("student", student).with("balance", balance);
        renderSendGetWriteClose(template, model, exchange);
    }

    public void loadTemplateWithAllQuest(HttpExchange exchange, List<TradableObject> allQuestsOrItems) throws IOException {
        List<TradableObject> questsOrItems = allQuestsOrItems.stream()
                                                .filter(questOrItem -> questOrItem.getTradableObjectType().equals(TradableObjectType.QUEST))
                                                .collect(Collectors.toList());
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/allItemsOrQuests.twig");
        JtwigModel model = JtwigModel.newModel().with("questsOrItems", questsOrItems);
        renderSendGetWriteClose(template, model, exchange);
    }

    public void loadTemplateWithAllArtifacts(HttpExchange exchange, List<TradableObject> allQuestsOrItems) throws IOException {
        List<TradableObject> questsOrItems = allQuestsOrItems.stream()
                .filter(questOrItem -> questOrItem.getTradableObjectType().equals(TradableObjectType.ITEM))
                .collect(Collectors.toList());
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/allItemsOrQuests.twig");
        JtwigModel model = JtwigModel.newModel().with("questsOrItems", questsOrItems);
        renderSendGetWriteClose(template, model, exchange);
    }

    public void loadTemplateWithWallet(HttpExchange exchange, Wallet makeStudentWallet, Balance balance) throws IOException {
        List<Transaction> transactions = makeStudentWallet.getTransactions();
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/studentMain.twig");
        JtwigModel model = JtwigModel.newModel().with("transactions", transactions)
                                                .with("balance", balance);
        renderSendGetWriteClose(template, model, exchange);
    }

    private void renderSendGetWriteClose(JtwigTemplate template, JtwigModel model, HttpExchange exchange) throws IOException {
        String response = template.render(model);
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
