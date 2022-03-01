package com.example.android.student_planner_ia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout dailyPeriods;
    List<Assignment> assignmentList;
    List<Task> taskList;

    Button button1View;
    Button button2View;
    Button button3View;
    Button button4View;
    Button button5View;

    boolean isADay;

    String[] classNumbers, classPeriods;
    int[] assignNum, taskNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_classes_view);



        dailyPeriods = (LinearLayout) findViewById(R.id.daily_periods);
        button1View = (Button) findViewById(R.id.button1);
        button2View = (Button) findViewById(R.id.button2);
        button3View = (Button) findViewById(R.id.button3);
        button4View = (Button) findViewById(R.id.button4);
        button5View = (Button) findViewById(R.id.button5);

        isADay = true;

        classNumbers = new String[5];
        classPeriods = new String[8];

        classNumbers[0] = "1A/B";
        classNumbers[4] = "5A/B";

        setABDay();




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

    public void assignmentButton(){

    }

    public void showCalendar(View view){


        setContentView(R.layout.calendar_view);
    }

    public void showDaily(View view){
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

        button1View.setText("1A/B - " + classPeriods[0] + "\n\n" + assignNum[0] + " assignments \t" + taskNum[0] + " tasks");
        if (isADay){
            button2View.setText("2A - " + classPeriods[1] + "\n\n" + assignNum[1] + " assignments \t" + taskNum[1] + " tasks");
            button3View.setText("3A - " + classPeriods[2] + "\n\n" + assignNum[2] + " assignments \t" + taskNum[2] + " tasks");
            button4View.setText("4A - " + classPeriods[3] + "\n\n" + assignNum[3] + " assignments \t" + taskNum[3] + " tasks");
        }
        else{
            button2View.setText("2B - " + classPeriods[4] + "\n\n" + assignNum[4] + " assignments \t" + taskNum[4] + " tasks");
            button3View.setText("3B - " + classPeriods[5] + "\n\n" + assignNum[5] + " assignments \t" + taskNum[5] + " tasks");
            button4View.setText("4B - " + classPeriods[6] + "\n\n" + assignNum[6] + " assignments \t" + taskNum[6] + " tasks");
        }
        button5View.setText("5A/B - " + classPeriods[7] + "\n\n" + assignNum[7] + " assignments \t" + taskNum[7] + " tasks");

        setContentView(R.layout.daily_classes_view);
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