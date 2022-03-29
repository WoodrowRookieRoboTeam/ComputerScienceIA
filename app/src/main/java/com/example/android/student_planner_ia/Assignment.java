package com.example.android.student_planner_ia;

import android.widget.CheckBox;
import android.widget.TextView;

import java.sql.Time;
import java.util.Date;

public class Assignment extends CalItem{

    String name, classPeriod;
    Date dueDate;

    TextView icon;
    CheckBox checkBox;

    public Assignment(String name, String classPeriod, Date dueDate){
        this.name = name;
        this.classPeriod = classPeriod;
        this.dueDate = dueDate;

        //icon = new TextView();
        //checkBox = new CheckBox();


    }

    public void addTask(){

    }


}
