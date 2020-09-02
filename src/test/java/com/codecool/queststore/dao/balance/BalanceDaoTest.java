package com.codecool.queststore.dao.balance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BalanceDaoTest {

    static BalanceDao balanceDao;

    @BeforeEach
    void setUpDao(){
        balanceDao = new BalanceDao("jdbc:h2:~/test_db", "test", "");
    }

    @Test
    void ShouldReturnAllBalances_When_GetAll(){
        List<Balance> balanceList = Collections.singletonList(new Balance().setAmount(1000).setTotalEarned(2000).setUserId(1));
        assertEquals(balanceList.toString(), balanceDao.get("1=1").toString());
    }

    @Test
    void ShouldReturnTrue_When_InsertSuccessful(){
        Balance balance = new Balance().setAmount(1000).setTotalEarned(2000).setUserId(1);
        assertTrue(balanceDao.insert(balance));
    }

    @Test
    void ShouldReturnTrue_When_UpdateSuccessful(){
        Balance balance = new Balance().setAmount(5000).setTotalEarned(2000).setUserId(1);
        assertTrue(balanceDao.update(balance));
    }

    @Test
    void ShouldReturnTrue_When_DeleteSuccessful(){
        Balance balance = new Balance().setAmount(1000).setTotalEarned(2000).setUserId(1);
        assertTrue(balanceDao.delete(balance));
    }
}