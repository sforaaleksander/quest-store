package com.codecool.queststore.dao.userClassrooms;

public class UserClassroom {

    private int userId;
    private int classroomId;

    public int getUserId() {
        return userId;
    }

    public UserClassroom setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public UserClassroom setClassroomId(int classroomId) {
        this.classroomId = classroomId;
        return this;
    }
}
