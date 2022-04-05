package com.example.android.student_planner_ia;

import java.sql.Time;
import java.util.Date;

public class Task{

    // class serves solely to store task attributes

    String name, assignment;
    Date dueDate;

    public Task(String name, String assignment, Date dueDate){
        this.name = name;
        this.assignment = assignment;
        this.dueDate = dueDate;
    }
}
