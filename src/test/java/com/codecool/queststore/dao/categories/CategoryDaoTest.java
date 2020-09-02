package com.codecool.queststore.dao.categories;

import com.codecool.queststore.dao.DatabaseResetter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDaoTest {
    static CategoryDao categoryDao;
    static DatabaseResetter databaseResetter = new DatabaseResetter();

    @BeforeEach
    void resetDb() throws SQLException {
        databaseResetter.resetDatabase();
        categoryDao = new CategoryDao("jdbc:h2:~/test_db", "test", "");
    }

    @Test
    void should_ReturnSingleCategory_when_CorrectConditionProvided() {
        Category category = new Category().setId(1).setName("basic-quest");
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        assertEquals(categories.toString(), categoryDao.get("id=1").toString());
    }

    @Test
    void should_ReturnEmptyList_when_ConditionIsNotProvided() {
        assertEquals(Collections.emptyList(), categoryDao.get(""));
    }

    @Test
    void should_ReturnEmptyList_when_WrongConditionProvided() {
        assertEquals(Collections.emptyList(), categoryDao.get("id=999"));
    }

    @Test
    void should_ReturnTrueAndInsertCategoryIntoDatabase_when_CorrectCategoryObjectProvided() {
        Category category = new Category().setId(99999).setName("Tester");
        assertTrue(categoryDao.insert(category));
    }

    @Test
    void should_ReturnFalse_when_EmptyCategoryObjectProvidedWhileInserting() {
        Category category = new Category();
        assertFalse(categoryDao.insert(category));
    }

    @Test
    void should_ReturnFalse_when_CategoryObjectProvidedWithIncompleteDataWhileInserting() {
        Category category = new Category().setId(123);
        assertFalse(categoryDao.insert(category));
    }

    @Test
    void should_ReturnTrue_when_CategoryDataSuccessfullyUpdated() {
        Category category = new Category().setId(1).setName("Mateuszeeeeek");
        assertTrue(categoryDao.update(category));
    }

    @Test
    void should_ReturnCategoryWithChangedData_when_CategoryProvidedWithMultipleChangedFields() {
        Category category = new Category().setId(1).setName("Mateuszeeeeek");
        categoryDao.update(category);
        assertEquals(category.toString(), categoryDao.get("id=1").get(0).toString());
    }

    @Test
    void should_ReturnEmptyListOfCategoriesAffectedByUpdate_when_EmptyCategoryObjectProvidedWhileUpdating() {
        Category category = new Category();
        categoryDao.update(category);
        assertEquals(Collections.emptyList(), categoryDao.get(String.format("id=%s", category.getId())));
    }

    @Test
    void should_ReturnCategoryWithChangedData_when_ProvidingSingleChangeToCategory() {
        Category category = new Category().setId(1).setName("Mateuszeeeeek");
        categoryDao.update(category);
        assertEquals(category.toString(), categoryDao.get("id=1").get(0).toString());
    }

    @Test
    void should_ReturnNullPointerException_when_CategoryAsNullProvidedToUpdate() {
        Category category = null;
        assertThrows(NullPointerException.class, () -> categoryDao.update(category));
    }

    @Test
    void should_ReturnEmptyListOfCategoriesAffectedByUpdate_when_ProvidedCategoryNotExistingInDatabase() {
        Category category = new Category().setId(9876).setName("Mateuszeeeeek");
        categoryDao.update(category);
        assertEquals(Collections.emptyList(), categoryDao.get(String.format("id=%s", category.getId())));
    }

    @Test
    void should_ReturnEmptyListOfCategories_when_DeletingProvidedCategory() {
        Category category = new Category().setId(1).setName("basic-quest");
        categoryDao.delete(category);
        assertEquals(Collections.emptyList(), categoryDao.get("id=1"));
    }

    @Test
    void should_ReturnEmptyListOfCategories_when_DeletingEmptyCategory() {
        Category category = new Category();
        categoryDao.delete(category);
        assertEquals(Collections.emptyList(), categoryDao.get(String.format("id=%s", category.getId())));
    }
}