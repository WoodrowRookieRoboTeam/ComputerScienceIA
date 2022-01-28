package com.example.android.student_planner_ia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    String[] classPeriods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        classPeriods[0] = "1A/B";
        classPeriods[1] = "2A";
        classPeriods[2] = "3A";
        classPeriods[3] = "4A";
        classPeriods[4] = "5A/B";
        classPeriods[5] = "2B";
        classPeriods[6] = "3B";
        classPeriods[7] = "4B";
        //runProgram();
    }

    /*
    // Runs the program continuously
    public void runProgram(){
        while (0 == 0){
            switch (tab) {
                case Calendar:
                    runCalendar();
                    break;
                case Daily:
                    runDaily();
                    break;
                case AssignmentList:
                    runAsList(view);
                    break;
                case Other:
                    break;
            }
        }

    }
    */

    // Runs each individual enum


    // runs



}