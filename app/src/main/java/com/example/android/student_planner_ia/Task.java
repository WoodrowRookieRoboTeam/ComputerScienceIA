package com.example.android.student_planner_ia;

import java.sql.Time;
import java.util.Date;

public class Task extends CalItem{

    public String name, classPeriod, assignment;
    Date dueDate;

    public Task(){
        super();
    }

    public Task(String name, String classPeriod, String assignment, Date dueDate){
        this.name = name;
        this.classPeriod = classPeriod;
        this.assignment = assignment;
        this.dueDate = dueDate;

    }


}
