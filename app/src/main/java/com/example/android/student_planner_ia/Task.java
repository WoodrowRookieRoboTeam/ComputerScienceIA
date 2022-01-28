package com.example.android.student_planner_ia;

import java.util.Date;

public class Task extends CalItem{

    String name;
    Date dueDate;

    public Task(){
        super();
    }

    public Task(String name, Date dueDate, Assignment assign){
        this.name = name;
        this.dueDate = dueDate;

    }


}
