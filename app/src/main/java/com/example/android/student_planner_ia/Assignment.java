package com.example.android.student_planner_ia;

import java.sql.Time;
import java.util.Date;

public class Assignment extends CalItem{

    String name, classPeriod;
    Date dueDate;



    public Assignment(String name, String classPeriod, Date dueDate){
        this.name = name;
        this.classPeriod = classPeriod;
        this.dueDate = dueDate;
    }

    public void addTask(){

    }
}
