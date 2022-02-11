package com.example.android.student_planner_ia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout dailyPeriods = (LinearLayout) findViewById(R.id.daily_periods);
    List<Assignment> assignmentList;

    boolean isADay;

    String[] classNumbers, classPeriods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_view);

        setABDay();

        classNumbers[0] = "1A/B";
        classNumbers[4] = "5A/B";


        // these need to be later replaced with the "createSchedule" function
        classPeriods[0] = "Macroeconomics";
        classPeriods[1] = "IB Film";
        classPeriods[2] = "IB Math";
        classPeriods[3] = "IB Literature";
        classPeriods[4] = "IB History";
        classPeriods[5] = "IB French";
        classPeriods[6] = "IB Computer Science";
        classPeriods[7] = "IB Global Politics";


        //runProgram();
    }


    public void setABDay(){
        if (isADay){
            classNumbers[1] = "2A";
            classNumbers[2] = "3A";
            classNumbers[3] = "4A";
        }
        else{
            classNumbers[1] = "2B";
            classNumbers[2] = "3B";
            classNumbers[3] = "4B";
        }
    }

    public void showCalendar(View view){
        for (Assignment assignment : assignMent
        }
        setContentView(R.layout.calendar_view);
    }

    public void showDaily(View view){
        setContentView(R.layout.daily_view);
    }

    public void showAsList(View view){
        setContentView(R.layout.assignment_list_view);
    }

}