package com.example.android.student_planner_ia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout dailyPeriods = (LinearLayout) findViewById(R.id.daily_periods);
    List<Assignment> assignmentList;
    List<Task> taskList;

    Button button1View = (Button) findViewById(R.id.button1);
    Button button2View = (Button) findViewById(R.id.button2);
    Button button3View = (Button) findViewById(R.id.button3);
    Button button4View = (Button) findViewById(R.id.button4);
    Button button5View = (Button) findViewById(R.id.button5);

    boolean isADay;

    String[] classNumbers, classPeriods;
    int[] assignNum, taskNum;

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

        resetAssignments();

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
        resetAssignments();
        for (int i = 0; i < classNumbers.length; i++){
            for (Assignment assignment : assignmentList){
                if (assignment.classPeriod == classNumbers[i]){
                    assignNum[i]++;
                }
            }
            for (Task task : taskList){
                if (task.classPeriod == classNumbers[i]){
                    taskNum[i]++;
                }
            }
        }

        button1View.setText(classPeriods);

        setContentView(R.layout.calendar_view);
    }

    public void showDaily(View view){
        setContentView(R.layout.daily_view);
    }

    public void showAsList(View view){
        setContentView(R.layout.assignment_list_view);
    }


    public void resetAssignments(){
        for (int i = 0; i < 7; i++){
            assignNum[i] = 0;
            taskNum[i] = 0;
        }
    }
}