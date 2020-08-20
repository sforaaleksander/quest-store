package com.codecool.queststore.control.services;

import com.codecool.queststore.control.services.models.Transaction;
import com.codecool.queststore.control.services.models.TransactionObjectType;
import com.codecool.queststore.control.services.models.Wallet;
import com.codecool.queststore.dao.Dao;
import com.codecool.queststore.dao.balance.Balance;
import com.codecool.queststore.dao.balance.BalanceDao;
import com.codecool.queststore.dao.categories.Category;
import com.codecool.queststore.dao.categories.CategoryDao;
import com.codecool.queststore.dao.items.Item;
import com.codecool.queststore.dao.items.ItemDao;
import com.codecool.queststore.dao.quests.Quest;
import com.codecool.queststore.dao.quests.QuestDao;
import com.codecool.queststore.dao.userItems.UserItems;
import com.codecool.queststore.dao.userItems.UserItemsDao;
import com.codecool.queststore.dao.userQuests.UserQuests;
import com.codecool.queststore.dao.userQuests.UserQuestsDao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TradingService {

    private final UserItemsDao userItemDao = new UserItemsDao();
    private final ItemDao itemDao = new ItemDao();
    private Dao<UserQuests> userQuestDao = new UserQuestsDao();
    private Dao<Balance> balanceDao = new BalanceDao();
    private Dao<Quest> questDao = new QuestDao();
    private Dao<Category> categoryDao = new CategoryDao();

    public int getStudentCoinsAmount(int userId) {
        return balanceDao.get(String.format("user_id=?", userId)).get(0).getAmount();
    }

    public boolean addUserQuest(int questId, int userId) {
        return userQuestDao.insert(new UserQuests().setQuestId(questId).setUserId(userId)
                .setDoneDate(Date.valueOf(LocalDate.now())).setAccepted(false));
    }

    public boolean acceptUserQuest(int userQuestId) {
        UserQuests userQuestToAccept = userQuestDao.get(String.format("id=%d LIMIT 1", userQuestId)).get(0);
        userQuestToAccept.setAccepted(true);
        int userId = userQuestToAccept.getUserId();
        int questId = userQuestToAccept.getQuestId();
        int amountToAdd = questDao.get(String.format("id=%d LIMIT 1", questId)).get(0).getCost();
        Balance previousBalance = balanceDao.get(String.format("user_id=%d LIMIT 1", userId)).get(0);
        boolean isAdded = balanceDao.update(new Balance().setUserId(userId)
                                                         .setAmount(previousBalance.getAmount() + amountToAdd)
                                                         .setTotalEarned(previousBalance.getTotalEarned() + amountToAdd));
        if (isAdded) {
            return userQuestDao.update(userQuestToAccept);
        } else {
            return false;
        }
//        return userQuestDao.update(userQuestToAccept);
    }

    public boolean addUserItem(int itemId, int userId) {
        Item itemToBuy = itemDao.get(String.format("id=%d LIMIT 1", itemId)).get(0);
        if (itemToBuy.getCategoryId() == 4) return makeGroupShopping();
        int itemCost = itemToBuy.getCost();
        int userBalance = getStudentCoinsAmount(userId);
        if (userBalance < itemCost) return false;
        boolean isAdded = balanceDao.update(new Balance().setUserId(userId)
                .setAmount(userBalance - itemCost));
        if (isAdded) {
            return userItemDao.insert(new UserItems().setItemId(itemToBuy.getId()).setUserId(userId)
                    .setBoughtDate(Date.valueOf(LocalDate.now())).setUsed(false));
        }
        return false;
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

    public Wallet makeStudentWallet(int studentId) {
        List<Transaction> transactions = new ArrayList<>();
        fillTransactionsWithQuests(studentId, transactions);
        fillTransactionsWithItems(studentId, transactions);
        List<Transaction> sortedTransactions = transactions.stream()
                .sorted(Comparator.comparing(Transaction::getTransactionDate).reversed())
                .collect(Collectors.toList());
        return new Wallet(sortedTransactions);
    }

    private void fillTransactionsWithItems(int studentId, List<Transaction> transactions) {
        userItemDao.get(String.format("user_id=%d", studentId)).stream().map(ui -> {
            Item item = itemDao.get(String.format("item_id=%d", ui.getItemId())).get(0);
            return new Transaction().setName(item.getName()).setDescription(item.getDescription())
                    .setCost(item.getCost()).setCategoryName(
                            categoryDao.get(String.format("id=%d", item.getCategoryId())).get(0).getName())
                    .setTransactionDate(ui.getBoughtDate()).setDone(ui.isUsed())
                    .setTransactionObjectType(TransactionObjectType.ITEM);
        }).forEach(transactions::add);
    }

    private void fillTransactionsWithQuests(int studentId, List<Transaction> transactions) {
        userQuestDao.get(String.format("user_id=%d", studentId)).stream().map(uq -> {
            Quest quest = questDao.get(String.format("quest_id=%d", uq.getQuestId())).get(0);
            return new Transaction().setName(quest.getName()).setDescription(quest.getDescription())
                    .setCost(quest.getCost()).setCategoryName(
                            categoryDao.get(String.format("id=%d", quest.getCategoryId())).get(0).getName())
                    .setTransactionDate(uq.getDoneDate()).setDone(uq.isAccepted())
                    .setTransactionObjectType(TransactionObjectType.QUEST);
        }).forEach(transactions::add);
    }

    public List<Transaction> getAllItemsTransactions() { return null; }

}
