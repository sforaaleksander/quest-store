package com.codecool.queststore.user.mentor;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;

public class MentorController implements HttpHandler {

    private MentorService mentorService = new MentorService();
    private MentorView mentorView = new MentorView();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }

    private File getStartScreen(String json) {
        return mentorView.createStartScreenTemplate(mentorService.getMentorInfo(json));
    }

    private void addStudent(String json) {
        mentorService.addStudent(json);
    }

    private void addQuest(String json) {
        mentorService.addQuest(json);
    }

    private void splitQuests() {

    }

    private void addStoreItem(String json) {
        mentorService.addStoreItem(json);
    }

    private void editStoreItem(String json) {
        mentorService.editStoreItem(json);
    }

    private void markAchievedQuests(String json) {
        mentorService.markAchievedQuest(json);
    }

    private void markBoughtArtifacts(String json) {
        mentorService.markBoughtArtifacts(json);
    }

    private File seeStudentsWallets() {
        return mentorView.createStudentsWalletsTemplate(mentorService.getStudentsWallets());
    }

}
