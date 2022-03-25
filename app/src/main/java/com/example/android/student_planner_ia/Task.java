package com.example.android.student_planner_ia;

import java.sql.Time;
import java.util.Date;

public class Task extends CalItem{

    public String name, classPeriod;
    Date dueDate;

    public Task(){
        super();
    }

    public Task(String name, String classPeriod, Assignment assign, Date dueDate){
        this.name = name;
        this.classPeriod = classPeriod;
        this.dueDate = dueDate;

    }


}
