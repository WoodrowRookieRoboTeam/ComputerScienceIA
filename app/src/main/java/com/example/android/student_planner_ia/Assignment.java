package com.example.android.student_planner_ia;

import android.widget.CheckBox;
import android.widget.TextView;

import java.sql.Time;
import java.util.Date;

public class Assignment{

    String name, classPeriod, key;
    Date dueDate;

    public Assignment(String name, String classPeriod, Date dueDate){
        this.name = name;
        this.classPeriod = classPeriod;
        this.dueDate = dueDate;

        this.key = this.name + "Key";
    }
}
