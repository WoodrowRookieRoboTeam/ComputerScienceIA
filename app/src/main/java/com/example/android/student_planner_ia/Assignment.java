package com.example.android.student_planner_ia;

import android.widget.CheckBox;
import android.widget.TextView;

import java.sql.Time;
import java.util.Date;

public class Assignment{

    // Class serves solely to store assignment attributes

    String name, classPeriod;
    Date dueDate;

    public Assignment(String name, String classPeriod, Date dueDate){
        this.name = name;
        this.classPeriod = classPeriod;
        this.dueDate = dueDate;
    }
}
