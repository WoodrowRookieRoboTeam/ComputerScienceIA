package com.example.android.student_planner_ia;

import java.sql.Time;
import java.util.Date;

public class Task{

    public String name, assignment, key;
    boolean isSorted;
    Date dueDate;

    public Task(){
        super();
    }

    public Task(String name, String assignment, Date dueDate){
        this.name = name;
        this.assignment = assignment;
        this.dueDate = dueDate;
        isSorted = false;

        this.key = this.name + "Key";
    }

    public void setSorted(boolean newSort){
        isSorted = newSort;
    }

}
