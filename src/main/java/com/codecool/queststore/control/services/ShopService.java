package com.codecool.queststore.control.services;

import com.codecool.queststore.control.services.models.CategoryType;
import com.codecool.queststore.control.services.models.TradableObject;
import com.codecool.queststore.control.services.models.TradableObjectType;
import com.codecool.queststore.dao.Dao;
import com.codecool.queststore.dao.categories.Category;
import com.codecool.queststore.dao.categories.CategoryDao;
import com.codecool.queststore.dao.items.Item;
import com.codecool.queststore.dao.items.ItemDao;
import com.codecool.queststore.dao.quests.Quest;
import com.codecool.queststore.dao.quests.QuestDao;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ShopService {
    private Dao<Item> itemDao = new ItemDao();
    private Dao<Quest> questDao = new QuestDao();
    private Dao<Category> categoryDao = new CategoryDao();

    public List<TradableObject> getAllQuests() {
        return questDao.get("1=1").stream().map(quest -> {
            String name = categoryDao.get(String.format("id='%s' LIMIT 1", quest.getId())).get(0).getName();
            return new TradableObject().setId(quest.getId()).setName(quest.getName())
                    .setDescription(quest.getDescription()).setCategoryName(name)
                    .setTradableObjectType(TradableObjectType.QUEST);
        }).collect(Collectors.toList());
    }

    public List<TradableObject> getAllItems() {
        return itemDao.get("1=1").stream().map(item -> {
            String name = categoryDao.get(String.format("id='%s' LIMIT 1", item.getId())).get(0).getName();
            return new TradableObject().setId(item.getId()).setName(item.getName())
                    .setDescription(item.getDescription()).setCategoryName(name)
                    .setTradableObjectType(TradableObjectType.ITEM);
        }).collect(Collectors.toList());
    }

    public boolean addItem(Item item, boolean isBasic) {
        return itemDao.insert(new Item().setName(item.getName()).setDescription(item.getDescription())
                .setCost(item.getCost()).setCategoryId(categoryDao.get(String.format("name='%s' LIMIT 1",
                        isBasic ? CategoryType.BASIC_ITEM.toString() :
                        CategoryType.MAGIC_ITEM.toString())).get(0).getId()));
    }

    public boolean editItem(Item item) {
        Item itemToEdit = itemDao.get(String.format("id=%d LIMIT 1", item.getId())).get(0);
        itemToEdit.setName(item.getName()).setDescription(item.getDescription()).setCost(item.getCost());
        return itemDao.update(itemToEdit);
    }

    public boolean addQuest(Quest quest, boolean isBasic) {
        return questDao.insert(new Quest().setName(quest.getName()).setDescription(quest.getDescription())
                .setCost(quest.getCost()).setCategoryId(categoryDao.get(String.format("name='%s' LIMIT 1",
                        isBasic ? CategoryType.BASIC_QUEST.toString() :
                                CategoryType.EXTRA_QUEST.toString())).get(0).getId()));
    }

    public boolean editQuest(Quest quest) {
        Quest questToEdit = questDao.get(String.format("id=%d LIMIT 1", quest.getId())).get(0);
        questToEdit.setName(quest.getName()).setDescription(quest.getDescription()).setCost(quest.getCost());
        return questDao.update(questToEdit);
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
