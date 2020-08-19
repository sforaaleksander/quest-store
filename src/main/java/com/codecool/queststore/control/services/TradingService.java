package com.codecool.queststore.control.services;

import com.codecool.queststore.dao.Dao;
import com.codecool.queststore.dao.items.Item;
import com.codecool.queststore.dao.items.ItemDao;
import com.codecool.queststore.dao.userItems.UserItems;
import com.codecool.queststore.dao.userItems.UserItemsDao;
import com.codecool.queststore.dao.userQuests.UserQuests;
import com.codecool.queststore.dao.userQuests.UserQuestsDao;

import java.sql.Date;
import java.time.LocalDate;

public class TradingService {

    private final UserItemsDao userItemDao = new UserItemsDao();
    private final ItemDao itemDao = new ItemDao();
    private Dao<UserQuests> userQuestDao = new UserQuestsDao();

    public int getStudentCoinsAmount() {
        return 0;
    }

    public boolean addUserQuest(int questId, int userId) {
        return userQuestDao.insert(new UserQuests().setQuestId(questId).setUserId(userId)
                .setDoneDate(Date.valueOf(LocalDate.now())).setAccepted(false));
    }

    public boolean acceptUserQuest(int userQuestId) {
        UserQuests userQuestToAccept = userQuestDao.get(String.format("id=%d LIMIT 1", userQuestId)).get(0);
        userQuestToAccept.setAccepted(true);
        return userQuestDao.update(userQuestToAccept);

    }

    public boolean addUserItem(int itemId, int userId) {
        Item itemToBuy = itemDao.get(String.format("id=%d LIMIT 1", itemId)).get(0);
        if (itemToBuy.getCategoryId() == 0) return makeGroupShopping(); // TODO check if item is bought by many students
        int itemCost = itemToBuy.getCost();
        int userBalance = 50; // TODO get student's balance
        if (userBalance < itemCost) return false;
        return userItemDao.insert(new UserItems().setItemId(itemToBuy.getId()).setUserId(userId)
                .setBoughtDate(Date.valueOf(LocalDate.now())).setUsed(false));
    }

    private boolean makeGroupShopping() {
        //TODO
        return false;
    }

    public boolean useUserItem(int userItemId) {
        UserItems userItemToUse = userItemDao.get(String.format("id=%d LIMIT 1", userItemId)).get(0);
        userItemToUse.setUsed(true);
        return userItemDao.update(userItemToUse);
    }

    public void addStudentsShopping() {

    }

    public void addGroupShopping() {

    }

    public void makeStudentWallet() {

    }

}
