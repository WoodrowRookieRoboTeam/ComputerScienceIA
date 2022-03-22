package com.example.android.student_planner_ia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //LinearLayout dailyPeriods;
    List<Assignment> assignmentList;
    List<Task> taskList;

    /*Button button1View;
    Button button2View;
    Button button3View;
    Button button4View;
    Button button5View;*/

    boolean isADay;

    String[] classNumbers, classPeriods;
    int[] assignNum, taskNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assignmentList = new ArrayList<Assignment>();
        taskList = new ArrayList<Task>();


        //dailyPeriods = (LinearLayout) findViewById(R.id.daily_periods);
        /*button1View = (Button) findViewById(R.id.button1);
        button2View = (Button) findViewById(R.id.button2);
        button3View = (Button) findViewById(R.id.button3);
        button4View = (Button) findViewById(R.id.button4);
        button5View = (Button) findViewById(R.id.button5);*/

        isADay = true;

        classNumbers = new String[5];
        classPeriods = new String[8];

        classNumbers[0] = "1A/B";
        classNumbers[4] = "5A/B";

        assignNum = new int[8];
        taskNum = new int[8];

        for (int i = 0; i < assignNum.length; i++){
            assignNum[i] = 0;
            taskNum[i] = 0;
        }

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

        //showDaily();
        setContentView(R.layout.daily_classes_view);
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


    // Following 3 methods concern main three menu screens

    public void showCalendar(View view){


        setContentView(R.layout.calendar_view);
    }

    public void showDaily(View view){
        resetAssignments();
        LinearLayout dailyPeriods = (LinearLayout) findViewById(R.id.daily_periods);
        setContentView(R.layout.daily_classes_view);

        for (int i = 0; i < classNumbers.length; i++){
            if (assignmentList.size() > 0) {
                for (Assignment assignment : assignmentList) {
                    if (assignment.classPeriod == classNumbers[i]) {
                        assignNum[i]++;
                    }
                }
            }
            if (taskList.size() > 0) {
                for (Task task : taskList) {
                    if (task.classPeriod == classNumbers[i]) {
                        taskNum[i]++;
                    }
                }
            }
        }

        Button button1View = (Button) findViewById(R.id.button1);
        button1View.setText("1A/B - " + classPeriods[0] + "\n\n" + assignNum[0] + " assignments \t" + taskNum[0] + " tasks");
        if (isADay){
            Button button2View = (Button) findViewById(R.id.button2);
            button2View.setText("2A - " + classPeriods[1] + "\n\n" + assignNum[1] + " assignments \t" + taskNum[1] + " tasks");
            Button button3View = (Button) findViewById(R.id.button3);
            button3View.setText("3A - " + classPeriods[2] + "\n\n" + assignNum[2] + " assignments \t" + taskNum[2] + " tasks");
            Button button4View = (Button) findViewById(R.id.button4);
            button4View.setText("4A - " + classPeriods[3] + "\n\n" + assignNum[3] + " assignments \t" + taskNum[3] + " tasks");
        }
        else{
            Button button2View = (Button) findViewById(R.id.button2);
            button2View.setText("2B - " + classPeriods[4] + "\n\n" + assignNum[4] + " assignments \t" + taskNum[4] + " tasks");
            Button button3View = (Button) findViewById(R.id.button3);
            button3View.setText("3B - " + classPeriods[5] + "\n\n" + assignNum[5] + " assignments \t" + taskNum[5] + " tasks");
            Button button4View = (Button) findViewById(R.id.button4);
            button4View.setText("4B - " + classPeriods[6] + "\n\n" + assignNum[6] + " assignments \t" + taskNum[6] + " tasks");
        }
        Button button5View = (Button) findViewById(R.id.button5);
        button5View.setText("5A/B - " + classPeriods[7] + "\n\n" + assignNum[7] + " assignments \t" + taskNum[7] + " tasks");
    }

    public void showAsList(View view){
        setContentView(R.layout.assignment_list_view);
    }


    // Following methods concern item creation

    public void newAssignment(View view){
        setContentView(R.layout.edit_assignment);

        Button createItem = (Button) findViewById(R.id.add_item);

    }

    public void saveAssignment(View view){
        EditText getName = (EditText) findViewById(R.id.assignment_name);
        EditText getPeriod = (EditText) findViewById(R.id.assignment_period);
        EditText getDate = (EditText) findViewById(R.id.assignment_date);
        EditText getTime = (EditText) findViewById(R.id.assignment_time);
        EditText getDescription = (EditText) findViewById(R.id.assignment_description);

        assignmentList.add(new Assignment(getName.getText().toString(), getPeriod.getText().toString(), getDate.getText().toString(), getTime.getText().toString(), getDescription.getText().toString()));
    }

    public void newTask(View view){
        setContentView(R.layout.edit_task);
    }

    public void saveTask(View view){
        EditText getName = (EditText) findViewById(R.id.task_name);
        EditText getPeriod = (EditText) findViewById(R.id.task_period);
        EditText getAssociated = (EditText) findViewById(R.id.task_associated);
        EditText getDate = (EditText) findViewById(R.id.task_date);
        EditText getTime = (EditText) findViewById(R.id.task_time);
        EditText getDescription = (EditText) findViewById(R.id.task_description);

        taskList.add(new Task(getName.getText().toString(), getPeriod.getText().toString(), getAssociated.getText().toString(), getDate.getText().toString(), getTime.getText().toString(), getDescription.getText().toString()));
    }


    public void resetAssignments(){
        for (int i = 0; i < 7; i++){
            assignNum[i] = 0;
            taskNum[i] = 0;
        }
    }
}