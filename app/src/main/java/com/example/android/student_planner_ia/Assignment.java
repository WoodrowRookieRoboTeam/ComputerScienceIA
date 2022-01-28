package com.example.android.student_planner_ia;

import java.util.Date;

public class Assignment extends CalItem{

    String name, assignType, description;
    Date dueDate;



    public Assignment(String name, String description, String assignType, Date dueDate){
        this.name = name;
        this.description = description;
        this.assignType = assignType;
        this.dueDate = dueDate;
    }

    public void addTask(){

    }
}
