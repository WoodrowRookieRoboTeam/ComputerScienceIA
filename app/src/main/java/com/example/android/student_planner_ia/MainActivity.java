package com.example.android.student_planner_ia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;


    //LinearLayout dailyPeriods;
    List<Assignment> assignmentList;
    List<Task> taskList;

    List<String> assignmentKeys, taskKeys;

    boolean scheduleDone;
    boolean isADay;
    boolean isScheduleTemp;

    String[] classNumbers, classPeriods, classKeys;
    int[] assignNum, taskNum;
    int assignNet, taskNet;

    Date currentDate;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentDate = new Date();
        calendar = Calendar.getInstance();

        if (calendar.get(Calendar.DAY_OF_WEEK) == 2 || calendar.get(Calendar.DAY_OF_WEEK) == 4 || calendar.get(Calendar.DAY_OF_WEEK) == 7){
            isADay = true;
        }
        else if (calendar.get(Calendar.DAY_OF_WEEK) == 1 || calendar.get(Calendar.DAY_OF_WEEK) == 3 || calendar.get(Calendar.DAY_OF_WEEK) == 5){
            isADay = false;
        }
        else if (calendar.get(Calendar.DAY_OF_WEEK) == 6){
            // add full list of dates here
        }




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
        classKeys = new String[8];

        classKeys[0] = "class0";
        classKeys[1] = "class1";
        classKeys[2] = "class2";
        classKeys[3] = "class3";
        classKeys[4] = "class4";
        classKeys[5] = "class5";
        classKeys[6] = "class6";
        classKeys[7] = "class7";

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

        assignNet = 0;
        taskNet = 0;

        isScheduleTemp = true;
        //isADay = true; // temporary declaration
        setABDay();


        resetAssignments();


        if (scheduleDone == false){
            setContentView(R.layout.create_schedule);
            Button cancelButton = (Button) findViewById(R.id.cancel_schedule);
            cancelButton.setVisibility(View.INVISIBLE);

        }
        else {
            // these need to be later replaced with the "createSchedule" function

            for (int i = 0; i < classPeriods.length; i++){
                classPeriods[i] = sharedPref.getString("class" + i, "Failed to retrieve class");
            }

            assignNet = sharedPref.getInt("assignTotal", -1);
            taskNet = sharedPref.getInt("taskTotal", -1);
            /*
            for (int i = 0; i < assignNet; i++){
                Gson gson = new Gson();
                String json = sharedPref.getString("assignKey" + i, "failed to retrieve assignment");
                assignmentList.add(gson.fromJson(json, Assignment.class));
            }

            for (int i = 0; i < taskNet; i++){
                Gson gson = new Gson();
                String json = sharedPref.getString("taskKey" + i, "failed to retrieve task");
                taskList.add(gson.fromJson(json, Task.class));
            }
            */
            //showDaily();
            setContentView(R.layout.daily_classes_view);
            displayDaily();
        }
    }

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

        for (int i = 0; i < classPeriods.length; i++) {
            classPeriods[i] = getPeriods[i].getText().toString();
            editor.putString(classKeys[i], getPeriods[i].getText().toString()).apply();
        }


        assignmentList.clear();
        taskList.clear();

        scheduleDone = true;
        editor.putBoolean("scheduleDone", true).apply();

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
        allAssignmentView.setText("View all Classes: \n\n" + assignNet + " assigments \t" + taskNet + " tasks");
    }

    public void showAsList(View view){
        setContentView(R.layout.assignment_list_view);

        listItems();
    }

    public void listItems(){
        LinearLayout listLayout = findViewById(R.id.assignment_list);

        List<TextView> assignIcons = new ArrayList<TextView>();
        List<TextView> taskIcons = new ArrayList<TextView>();

        int a = 0;
        int t = 0;
        for (Assignment assignment : assignmentList){
            for (Task task : taskList){
                if (task.isSorted != true){
                    if (assignment.dueDate.compareTo(task.dueDate) < 0){
                        assignIcons.add(new TextView(this));

                        assignIcons.get(a).setLayoutParams(new LinearLayout.LayoutParams(300, 100));
                        assignIcons.get(a).setText(assignment.name + "\n\n" + assignment.dueDate.getHours() + ":" + assignment.dueDate.getMinutes() + "\n" + assignment.dueDate.getDay() + "/" + assignment.dueDate.getMonth() + "/" + assignment.dueDate.getYear() + "\t" + assignment.classPeriod);

                        a++;
                        break;
                    }
                    else{
                        taskIcons.add(new TextView(this));

                        taskIcons.get(t).setLayoutParams(new LinearLayout.LayoutParams(300, 100));
                        taskIcons.get(t).setText(task.name + "\n\n" + task.dueDate.getHours() + ":" + task.dueDate.getMinutes() + "\n" + task.dueDate.getDay() + "/" + task.dueDate.getMonth() + "/" + task.dueDate.getYear() + "\t" + task.classPeriod + "\n\nfor assignment " + task.assignment);

                        task.setSorted(true);
                        t++;
                    }
                }
            }
        }
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
        sortItems();
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

        taskList.add(new Task(setName, setPeriod, setAssociated, new Date(year, month, day, hour, minute)));

        setContentView(R.layout.daily_classes_view);
        //sortItems();
        displayDaily();


    }


    public void resetAssignments(){
        for (int i = 0; i < 7; i++){
            assignNum[i] = 0;
            taskNum[i] = 0;
        }
    }

    public void sortItems(){
        assignmentKeys.clear();
        if (assignmentList.size() > 1) {
            Collections.sort(assignmentList, new Comparator<Assignment>() {
                public int compare(Assignment a, Assignment b) {
                    return a.dueDate.compareTo(b.dueDate);
                }
            });
        }
        assignNet = assignmentList.size();
        editor.putInt("assignTotal", assignNet).apply();
        int a = 0;
        for (Assignment assignment : assignmentList){
            Gson gson = new Gson();
            assignmentKeys.add(gson.toJson(assignment));
            editor.putString("assignKey" + a, assignmentKeys.get(a)).apply();
            a++;

        }

        taskKeys.clear();
        Collections.sort(taskList, new Comparator<Task>() {
            public int compare(Task a, Task b) {
                return a.dueDate.compareTo(b.dueDate);
            }
        });
        taskNet = taskList.size();
        editor.putInt("taskTotal", taskNet).apply();
        int t = 0;
        for (Task task : taskList){
            Gson gson = new Gson();
            taskKeys.add(gson.toJson(task));
            editor.putString("taskKey" + t, taskKeys.get(t)).apply();
            t++;
        }

    }
}