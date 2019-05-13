package com.example.studentapp;

/**
 * Created by iPelino
 */

public class Student {

    private int mStudentId;
    private String mStudentName;

    public Student() {
    }

    public Student(int mStudentId, String mStudentName) {
        this.mStudentId = mStudentId;
        this.mStudentName = mStudentName;
    }

    public void setmStudentId(int mStudentId) {
        this.mStudentId = mStudentId;
    }

    public int getmStudentId() {
        return mStudentId;
    }

    public void setmStudentName(String mStudentName) {
        this.mStudentName = mStudentName;
    }

    public String getmStudentName() {
        return mStudentName;
    }
}
