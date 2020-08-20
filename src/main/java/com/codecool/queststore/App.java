package com.codecool.queststore;

import com.codecool.queststore.controllers.AdminController;
import com.codecool.queststore.controllers.LoginController;
import com.codecool.queststore.controllers.MentorController;
import com.codecool.queststore.controllers.StudentController;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws IOException {

        LoginController loginController = new LoginController();
        MentorController mentorController = new MentorController();
        AdminController adminController = new AdminController();
        StudentController studentController = new StudentController();

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        // set routes

        //TODO determine appropriate paths and make controller for them
        server.createContext("/quest-store", loginController); //done static present TODO return form to provide login or change path if logged
        server.createContext("/quest-store/login", loginController); // TODO receive login data
        server.createContext("quest-store/logout"); // TODO logout - remove cookie set sessionID non active

        server.createContext("/quest-store/student", studentController); //twig TODO return student main page (profile)
        server.createContext("/quest-store/student/new/item", studentController); //server TODO receive item to buy
        server.createContext("/quest-store/student/new/quest", studentController); //server TODO receive quest to be done
        server.createContext("/quest-store/student/store/items", studentController); //twig TODO return store page
        server.createContext("/quest-store/student/store/quests", studentController); //twig TODO return all quests' page
        server.createContext("/quest-store/student/wallet", studentController); //twig TODO return template with wallet

        server.createContext("/quest-store/mentor/", mentorController); //done twig present ?twigOrStatic todo return mentor start page

        server.createContext("/quest-store/mentor/new/student", mentorController); //server TODO receive student to add
        server.createContext("/quest-store/mentor/form/new/student", mentorController); // done static present TODO receive form to provide new student data
        server.createContext("/quest-store/mentor/new/student-classroom", mentorController); //server TODO receive class for student to assign
        server.createContext("/quest-store/mentor/form/new/student-classroom", mentorController); //done?twig present? ewentualnie strona ze sie udalo todo return form to provide data above
        server.createContext("/quest-store/mentor/new/quest", mentorController); //server TODO receive quest to add
        server.createContext("/quest-store/mentor/form/new/quest", mentorController); // done static present todo return form to provide new quest's data
        server.createContext("/quest-store/mentor/new/item", mentorController); //server TODO receive item to add
        server.createContext("/quest-store/mentor/form/new/item", mentorController); //done static present todo return form to provide new item's data

        server.createContext("quest-store/mentor/store/item", mentorController); //twig at end todo return page with available items which can be edited
        server.createContext("/quest-store/mentor/form/current/item", mentorController); //twig at end todo return form to edit item
        server.createContext("/quest-store/mentor/updated/item", mentorController); //server todo receive data to edit item
        // todo route which return page with split quest template (if needed)
        server.createContext("/quest-store/mentor/split/quest", mentorController); // todo ???

        server.createContext("/quest-store/mentor/not-marked/store/items", mentorController); //twig todo return template with items to mark
        server.createContext("/quest-store/mentor/not-marked/store/quests", mentorController); //twig todo return template with quests to mark
        server.createContext("/quest-store/mentor/not-marked/quest", mentorController); //server todo receive quest to mark
        server.createContext("/quest-store/mentor/not-marked/item", mentorController); //server todo receive item to add

        server.createContext("/quest-store/mentor/students/wallets", mentorController); //twig todo return template with student wallets data

        server.createContext("/quest-store/admin/", adminController); //done twig present ?twigOrStatic todo return admin start page

        server.createContext("/quest-store/admin/new/classroom", adminController); //server todo receive classroom to add
        server.createContext("quest-store/admin/form/new/classroom", adminController); //done static present todo return form to provide new classroom's data
        server.createContext("/quest-store/admin/new/mentor", adminController); //server todo receive mentor to add
        server.createContext("/quest-store/admin/form/new/mentor", adminController);  //done static present todo return form to provide new mentor's data

        server.createContext("/quest-store/admin/form/current/mentor", adminController); //done twig present editMentor.twig todo return form to edit mentor data
        server.createContext("/quest-store/admin/update/mentor", adminController); //server todo receive mentor to update

        server.createContext("/quest-store/admin/mentors", adminController); // done twig present todo return template with all mentors
        server.createContext("/quest-store/admin/specified/mentor", adminController); //server todo return template with specified mentor
        
        // start
        server.start();
    }
}
