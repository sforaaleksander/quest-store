package com.codecool.queststore.dao.userClassrooms;

import com.codecool.queststore.dao.Dao;
import com.codecool.queststore.dao.PostgreSqlJDBC;
import com.codecool.queststore.dao.classrooms.Classroom;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserClassroomsDao extends PostgreSqlJDBC implements Dao<UserClassrooms> {

    @Override
    public List<UserClassrooms> get(String condition) {
        List<UserClassrooms> userClassrooms = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getConnection()
                    .prepareStatement("SELECT * FROM classrooms WHERE ?");
            preparedStatement.setString(1, condition);
            ResultSet resultSet = preparedStatement.executeQuery();
            userClassrooms = createUserClassrooms(resultSet);
            preparedStatement.close();
            resultSet.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userClassrooms;
    }

    private List<UserClassrooms> createUserClassrooms(ResultSet resultSet) throws SQLException {
        List<UserClassrooms> userClassroomsList = new ArrayList<>();
        UserClassrooms userClassrooms;
        while (resultSet.next()) {
            int userId = resultSet.getInt("user_id");
            int classroomId = resultSet.getInt("classroom_id");
            userClassrooms = new UserClassrooms(userId, classroomId);
            userClassroomsList.add(userClassrooms);
        }
        return userClassroomsList;
    }

    @Override
    public boolean insert(UserClassrooms userClassrooms) {
        try {
            String insertTemplate = "INSERT INTO user_classrooms VALUES ('?','?');";
            PreparedStatement preparedStatement = getConnection()
                    .prepareStatement(insertTemplate);
            preparedStatement.setInt(1,userClassrooms.getUserId());
            preparedStatement.setInt(2,userClassrooms.getClassroomId());
            ResultSet resultSet = preparedStatement.executeQuery();
            preparedStatement.close();
            resultSet.close();
            closeConnection();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(UserClassrooms userClassrooms, UserClassrooms updatedUserClassrooms) {
        try {
            String updateTemplate = "UPDATE user_classrooms SET user_id='?' AND classroom_id='?' WHERE user_id='?' AND classroom_id='?';";
            PreparedStatement preparedStatement = getConnection()
                    .prepareStatement(updateTemplate);
            preparedStatement.setInt(1,userClassrooms.getUserId());
            preparedStatement.setInt(2,userClassrooms.getClassroomId());
            preparedStatement.setInt(3,userClassrooms.getUserId());
            preparedStatement.setInt(4,userClassrooms.getClassroomId());
            ResultSet resultSet = preparedStatement.executeQuery();
            preparedStatement.close();
            resultSet.close();
            closeConnection();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(UserClassrooms userClassrooms) {
        try {
            String deleteTemplate = "DELETE FROM user_classrooms WHERE user_id='?' AND classroom_id='?';";
            PreparedStatement preparedStatement = getConnection()
                    .prepareStatement(deleteTemplate);
            preparedStatement.setInt(1,userClassrooms.getUserId());
            preparedStatement.setInt(2,userClassrooms.getClassroomId());
            ResultSet resultSet = preparedStatement.executeQuery();
            preparedStatement.close();
            resultSet.close();
            closeConnection();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
