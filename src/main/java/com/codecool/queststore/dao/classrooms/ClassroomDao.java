package com.codecool.queststore.dao.classrooms;

import com.codecool.queststore.dao.Dao;
import com.codecool.queststore.dao.PostgreSqlJDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassroomDao extends PostgreSqlJDBC implements Dao<Classroom> {
    @Override
    public List<Classroom> get(String condition) {
        List<Classroom> classrooms = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getConnection()
                    .prepareStatement("SELECT * FROM classrooms WHERE ?");
            preparedStatement.setString(1, condition);
            ResultSet resultSet = preparedStatement.executeQuery();
            classrooms = createClassroomsFromResultSet(resultSet);
            preparedStatement.close();
            resultSet.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classrooms;
    }

    private List<Classroom> createClassroomsFromResultSet(ResultSet resultSet) {
        return null;
    }

    @Override
    public boolean insert(Classroom classroom) {
        return false;
    }

    @Override
    public boolean update(Classroom classroom, Classroom updatedClassroom) {
        return false;
    }

    @Override
    public boolean delete(Classroom classroom) {
        return false;
    }
}
