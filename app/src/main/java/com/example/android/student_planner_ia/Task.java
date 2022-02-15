package com.example.android.student_planner_ia;

import java.util.Date;

public class Task extends CalItem{

    String name, classPeriod;
    Date dueDate;

    public Task(){
        super();
    }

    public Task(String name, String classPeriod, Date dueDate, Assignment assign){
        this.name = name;
        this.classPeriod = classPeriod;
        this.dueDate = dueDate;

    }


}
