package com.codecool.queststore.dao.roles;

import com.codecool.queststore.dao.Dao;
import com.codecool.queststore.dao.PostgreSqlJDBC;

import java.util.List;

public class RoleDao extends PostgreSqlJDBC implements Dao<Role> {
    @Override
    public List<Role> get(String condition) {
        return null;
    }

    @Override
    public boolean insert(Role role) {
        return false;
    }

    @Override
    public boolean update(Role role, Role u) {
        return false;
    }

    @Override
    public boolean delete(Role role) {
        return false;
    }
}
