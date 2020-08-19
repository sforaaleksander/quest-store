package com.codecool.queststore.control.services;

import com.codecool.queststore.control.services.models.CategoryType;
import com.codecool.queststore.dao.Dao;
import com.codecool.queststore.dao.categories.Category;
import com.codecool.queststore.dao.categories.CategoryDao;
import com.codecool.queststore.dao.items.Item;
import com.codecool.queststore.dao.items.ItemDao;
import com.codecool.queststore.dao.quests.Quest;
import com.codecool.queststore.dao.quests.QuestDao;

public class ShopService {
    private Dao<Item> itemDao = new ItemDao();
    private Dao<Quest> questDao = new QuestDao();
    private Dao<Category> categoryDao = new CategoryDao();

    public boolean addItem(String name, String description, int cost, boolean isBasic) {
        return itemDao.insert(new Item().setName(name).setDescription(description).setCost(cost)
                .setCategoryId(categoryDao.get(String.format("name='%s' LIMIT 1",
                        isBasic ? CategoryType.BASIC_ITEM.toString() :
                        CategoryType.MAGIC_ITEM.toString())).get(0).getId()));
    }

    public boolean editItem(int id, String name, String description, int cost) {
        Item itemToEdit = itemDao.get(String.format("id=%d LIMIT 1", id)).get(0);
        itemToEdit.setName(name).setDescription(description).setCost(cost);
        return itemDao.update(itemToEdit);
    }

    public boolean addQuest(String name, String description, int cost, boolean isBasic) {
        return questDao.insert(new Quest().setName(name).setDescription(description).setCost(cost)
                .setCategoryId(categoryDao.get(String.format("name='%s' LIMIT 1",
                        isBasic ? CategoryType.BASIC_QUEST.toString() :
                                CategoryType.EXTRA_QUEST.toString())).get(0).getId()));
    }

    public boolean editQuest(int id, String name, String description, int cost) {
        Quest questToEdit = questDao.get(String.format("id=%d LIMIT 1", id)).get(0);
        questToEdit.setName(name).setDescription(description).setCost(cost);
        return questDao.update(questToEdit);
    }

    public boolean addCategory(String categoryName) {
        return categoryDao.insert(new Category().setName(categoryName));
    }

    public boolean changeItemCategory(int itemId, String categoryName) {
        int categoryId = categoryDao.get(String.format("name=%s LIMIT 1", categoryName)).get(0).getId();
        return itemDao.update(itemDao.get(String.format("id=%d LIMIT 1", itemId)).get(0).setCategoryId(categoryId));
    }

    public boolean changeQuestCategory(int questId, String categoryName) {
        int categoryId = categoryDao.get(String.format("name=%s LIMIT 1", categoryName)).get(0).getId();
        return questDao.update(questDao.get(String.format("id=%d LIMIT 1", questId)).get(0).setCategoryId(categoryId));
    }
}
