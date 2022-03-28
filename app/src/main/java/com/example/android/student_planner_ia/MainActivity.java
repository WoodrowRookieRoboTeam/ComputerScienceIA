package com.example.android.student_planner_ia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;


    //LinearLayout dailyPeriods;
    List<Assignment> assignmentList;
    List<Task> taskList;

    boolean scheduleDone;
    boolean isADay;
    boolean isScheduleTemp;

    String[] classNumbers, classPeriods;
    int[] assignNum, taskNum;

    Date currentDate = new Date(2022, 03, 23, 24, 30);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sets up shared preference
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        //sharedPref.edit().clear().commit();

        scheduleDone = sharedPref.getBoolean("scheduleDone", false);

        // initialize all variables
        assignmentList = new ArrayList<Assignment>();
        taskList = new ArrayList<Task>();

        classNumbers = new String[5]; // 5 class periods in a day
        classPeriods = new String[8]; // 8 total school classes

        // 1st and 5th period occur everyday regardless of whether it is an A or B day
        classNumbers[0] = "1A/B";
        classNumbers[4] = "5A/B";

        // List of how many assignments and tasks there are for each class period
        assignNum = new int[8];
        taskNum = new int[8];

        for (int i = 0; i < assignNum.length; i++){
            assignNum[i] = 0;
            taskNum[i] = 0;
        }

        isScheduleTemp = true;
        isADay = false; // temporary declaration
        setABDay();


        resetAssignments();


        if (scheduleDone == false){
            setContentView(R.layout.create_schedule);
            Button cancelButton = (Button) findViewById(R.id.cancel_schedule);
            cancelButton.setVisibility(View.INVISIBLE);
            scheduleDone = true;
            editor.putBoolean("scheduleDone", true).apply();
        }
        else {
            // these need to be later replaced with the "createSchedule" function

            classPeriods[0] = sharedPref.getString("classPeriods[0]", "Economics");
            classPeriods[1] = sharedPref.getString(classPeriods[1], "Film");
            classPeriods[2] = sharedPref.getString(classPeriods[2], "Math");
            classPeriods[3] = sharedPref.getString(classPeriods[3], "English");
            classPeriods[4] = sharedPref.getString(classPeriods[4], "History");
            classPeriods[5] = sharedPref.getString(classPeriods[5], "French");
            classPeriods[6] = sharedPref.getString(classPeriods[6], "Computer Science");
            classPeriods[7] = sharedPref.getString(classPeriods[7], "Global Politics");

            /*
            classPeriods[0] = "Macroeconomics";
            classPeriods[1] = "IB Film";
            classPeriods[2] = "IB Math";
            classPeriods[3] = "IB Literature";
            classPeriods[4] = "IB History";
            classPeriods[5] = "IB French";
            classPeriods[6] = "IB Computer Science";
            classPeriods[7] = "IB Global Politics";
            */
            //showDaily();
            setContentView(R.layout.daily_classes_view);
            displayDaily();
        }
    }

    /*
    public void setList(String key, List list){
        Gson gson = new Gson();
        String json = gson.toJson(list);

        editor.putString(key, json);
    }

    public List getList(){
        List list;
        String serializedObject = sharedPref.getString(, null);
        if (serializedObject != null){
            Gson gson = new Gson();
            Type type = new TypeToken<List>(){}.getType();
            list = gson.fromJson(serializedObject, type);
        }
    }*/

    // Checks whether it is an A or B day and adjusts daily schedule accordingly
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


    // Following 2 methods concern creation and editing of schedule

    public void saveSchedule(View view){
        EditText[] getPeriods= new EditText[8];

        getPeriods[0] = (EditText) findViewById(R.id.oneAB_class_name);
        getPeriods[1] = (EditText) findViewById(R.id.twoA_class_name);
        getPeriods[2] = (EditText) findViewById(R.id.threeA_class_name);
        getPeriods[3] = (EditText) findViewById(R.id.fourA_class_name);
        getPeriods[4] = (EditText) findViewById(R.id.fiveAB_class_name);
        getPeriods[5] = (EditText) findViewById(R.id.twoB_class_name);
        getPeriods[6] = (EditText) findViewById(R.id.threeB_class_name);
        getPeriods[7] = (EditText) findViewById(R.id.fourB_class_name);

        for(int i = 0; i < classPeriods.length; i++){
            classPeriods[i] = getPeriods[i].getText().toString();
            editor.putString(classPeriods[i], getPeriods[i].getText().toString()).apply();
        }

        assignmentList.clear();
        taskList.clear();

        setContentView(R.layout.daily_classes_view);
        displayDaily();
    }

    public void resetSchedule(View view){
        setContentView(R.layout.create_schedule);

        TextView scheduleTitle = (TextView) findViewById(R.id.create_schedule_title);
        scheduleTitle.setText("Reset Schedule");

        Button cancelButton = (Button) findViewById(R.id.cancel_schedule);
        cancelButton.setVisibility(View.VISIBLE);
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
                    if (assignment.classPeriod.equals(classNumbers[i])) {
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

        displayDaily();
    }

    public void displayDaily(){
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
            button2View.setText("2B - " + classPeriods[5] + "\n\n" + assignNum[5] + " assignments \t" + taskNum[5] + " tasks");
            Button button3View = (Button) findViewById(R.id.button3);
            button3View.setText("3B - " + classPeriods[6] + "\n\n" + assignNum[6] + " assignments \t" + taskNum[6] + " tasks");
            Button button4View = (Button) findViewById(R.id.button4);
            button4View.setText("4B - " + classPeriods[7] + "\n\n" + assignNum[7] + " assignments \t" + taskNum[7] + " tasks");
        }
        Button button5View = (Button) findViewById(R.id.button5);
        button5View.setText("5A/B - " + classPeriods[4] + "\n\n" + assignNum[4] + " assignments \t" + taskNum[4] + " tasks");

        Button allAssignmentView = (Button) findViewById(R.id.all_daily_assignments);
        allAssignmentView.setText("View all Classes: \n\n" + assignmentList.size() + " assigments \t" + taskList.size() + " tasks");
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

        String setName = getName.getText().toString();
        String setPeriod = getPeriod.getText().toString();

        int day, month, year, hour, minute;
        day = Integer.parseInt(getDate.getText().toString().substring(0, 2));
        month = Integer.parseInt(getDate.getText().toString().substring(3, 5));
        year = Integer.parseInt(getDate.getText().toString().substring(6, 10));
        hour = Integer.parseInt(getTime.getText().toString().substring(0, 2));
        minute = Integer.parseInt(getTime.getText().toString().substring(3, 4));

        assignmentList.add(new Assignment(setName, setPeriod, new Date(year, month, day, hour, minute)));
        setContentView(R.layout.daily_classes_view);
        displayDaily();
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

        String setName = getName.getText().toString();
        String setPeriod = getPeriod.getText().toString();
        String setAssociated = getAssociated.getText().toString();

        int day, month, year, hour, minute;
        day = Integer.parseInt(getDate.getText().toString().substring(0, 2));
        month = Integer.parseInt(getDate.getText().toString().substring(3, 5));
        year = Integer.parseInt(getDate.getText().toString().substring(6, 10));
        hour = Integer.parseInt(getTime.getText().toString().substring(0, 2));
        minute = Integer.parseInt(getTime.getText().toString().substring(3, 4));

        for (Assignment assignment : assignmentList){
            if (assignment.name.equals(setAssociated)){
                taskList.add(new Task(setName, setPeriod, assignment, new Date(year, month, day, hour, minute)));
            }
        }
        setContentView(R.layout.daily_classes_view);
        displayDaily();


    }


    public void resetAssignments(){
        for (int i = 0; i < 7; i++){
            assignNum[i] = 0;
            taskNum[i] = 0;
        }
    }
}