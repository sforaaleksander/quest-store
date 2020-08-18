package com.codecool.queststore.dao.userClassrooms;

public class UserClassroom {
    private int userId;
    private int classroomId;

    public UserClassroom(int userId, int classroomId) {
        this.userId = userId;
        this.classroomId = classroomId;
    }

    public int getUserId() {
        return userId;
    }

    public int getClassroomId() {
        return classroomId;
    }
}
