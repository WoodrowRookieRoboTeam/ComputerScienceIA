package com.example.android.student_planner_ia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
    public List<Assignment> assignmentList;
    public List<Task> taskList;

    boolean scheduleDone, isADay;

    String[] classNumbers, classPeriods, classKeys;
    int[] assignNum, taskNum;
    int assignNet, taskNet;
    int lastAssign, lastTask, classClicked;

    AssignmentAdapter assignAdapter;
    TaskAdapter taskAdapter;

    Date currentDate;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentDate = new Date();
        calendar = Calendar.getInstance();

        //currentDate = new Date(2022, 5, 7);
        if (calendar.get(Calendar.DAY_OF_WEEK) == 2 || calendar.get(Calendar.DAY_OF_WEEK) == 4 || calendar.get(Calendar.DAY_OF_WEEK) == 7){
            isADay = true;
        }
        else if (calendar.get(Calendar.DAY_OF_WEEK) == 1 || calendar.get(Calendar.DAY_OF_WEEK) == 3 || calendar.get(Calendar.DAY_OF_WEEK) == 5){
            isADay = false;
        }
        else if (calendar.get(Calendar.DAY_OF_WEEK) == 6){
            isADay = isFridayA();
        }


        // Sets up shared preference
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        //sharedPref.edit().clear().commit();

        scheduleDone = sharedPref.getBoolean("scheduleDone", false);

        // initialize all variables
        assignmentList = new ArrayList<Assignment>();
        taskList = new ArrayList<Task>();

        classNumbers = new String[8]; // 8 block periods
        classPeriods = new String[8]; // 8 class names
        classKeys = new String[8];

        for (int i = 0; i < classKeys.length; i++){
            classKeys[i] = "class" + i;
        }

        // Name of each class period
        classNumbers[0] = "1A/B";
        classNumbers[1] = "2A";
        classNumbers[2] = "3A";
        classNumbers[3] = "4A";
        classNumbers[4] = "5A/B";
        classNumbers[5] = "2B";
        classNumbers[6] = "3B";
        classNumbers[7] = "4B";

        // List of how many assignments and tasks there are for each class period
        assignNum = new int[8];
        taskNum = new int[8];
        resetAssignments();

        assignNet = 0;
        taskNet = 0;

        lastAssign = -1;
        lastTask = -1;
        classClicked = -1;


        if (scheduleDone == false){
            setContentView(R.layout.create_schedule);
            Button cancelButton = (Button) findViewById(R.id.cancel_schedule);
            cancelButton.setVisibility(View.INVISIBLE);
        }
        else {
            for (int i = 0; i < classPeriods.length; i++){
                classPeriods[i] = sharedPref.getString("class" + i, "Failed to retrieve class");
            }

            assignNet = sharedPref.getInt("assignTotal", 0);
            taskNet = sharedPref.getInt("taskTotal", 0);

            Gson gson = new Gson();
            Type assignType = new TypeToken<ArrayList<Assignment>>() {}.getType();
            Type taskType = new TypeToken<ArrayList<Task>>() {}.getType();

            String assignJson = sharedPref.getString("assignKey", "failed to retrieve assignments");
            assignmentList = gson.fromJson(assignJson, assignType);

            String taskJson = sharedPref.getString("taskKey", "failed to retrieve tasks");
            taskList = gson.fromJson(taskJson, taskType);


            sortItems();
            setContentView(R.layout.daily_classes_view);
            displayDaily();
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
        sortItems();

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


    public void showDaily(View view){
        setContentView(R.layout.daily_classes_view);
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

        listItems(assignmentList, taskList);
    }

    public void listItems(List<Assignment> aList, List<Task> tList){

        assignAdapter = new AssignmentAdapter(this, (ArrayList)aList);
        taskAdapter = new TaskAdapter(this, (ArrayList)tList);

        assignAdapter.notifyDataSetChanged();
        taskAdapter.notifyDataSetChanged();

        ListView assignLV = (ListView) findViewById(R.id.assignment_list);
        ListView taskLV = (ListView) findViewById(R.id.task_list);

        assignLV.setAdapter(assignAdapter);
        taskLV.setAdapter(taskAdapter);

        assignLV.setOnItemClickListener(this :: onAssignClick);
        taskLV.setOnItemClickListener(this :: onTaskClick);
    }

    public void classButton1(View view){
        classClicked = 0;
        listOneClass();
    }

    public void classButton2(View view){
        if (isADay){
            classClicked = 1;
        }
        else{
            classClicked = 5;
        }
        listOneClass();
    }

    public void classButton3(View view){
        if (isADay){
            classClicked = 2;
        }
        else{
            classClicked = 6;
        }
        listOneClass();
    }

    public void classButton4(View view){
        if (isADay){
            classClicked = 3;
        }
        else{
            classClicked = 7;
        }
        listOneClass();
    }

    public void classButton5(View view){
        classClicked = 4;
        listOneClass();
    }

    public void listOneClass(){
        setContentView(R.layout.assignment_list_view);

        List<Assignment> classAssigns = new ArrayList<Assignment>();
        List<Task> classTasks = new ArrayList<Task>();

        for (Assignment assignment : assignmentList){
            if (assignment.classPeriod.equals(classNumbers[classClicked])){
                classAssigns.add(assignment);
                for (Task task : taskList){
                    if (task.assignment.equals(assignment.name)){
                        classTasks.add(task);
                    }
                }
            }
        }
        listItems(classAssigns, classTasks);

    }

    public void onAssignClick(AdapterView<?> adapterView, View view, int position, long l){
        lastAssign = position;
        assignmentList.remove(lastAssign);
        sortItems();
        setContentView(R.layout.daily_classes_view);
        displayDaily();
    }

    public void onTaskClick(AdapterView<?> adapterView, View view, int position, long l){
        lastTask = position;
        taskList.remove(lastTask);
        sortItems();
        setContentView(R.layout.daily_classes_view);
        displayDaily();
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
        month = Integer.parseInt(getDate.getText().toString().substring(3, 5)) - 1;
        year = Integer.parseInt(getDate.getText().toString().substring(6, 10));
        hour = Integer.parseInt(getTime.getText().toString().substring(0, 2));
        minute = Integer.parseInt(getTime.getText().toString().substring(3, 5));

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
        EditText getAssociated = (EditText) findViewById(R.id.task_associated);
        EditText getDate = (EditText) findViewById(R.id.task_date);
        EditText getTime = (EditText) findViewById(R.id.task_time);

        String setName = getName.getText().toString();
        String setAssociated = getAssociated.getText().toString();

        int day, month, year, hour, minute;
        day = Integer.parseInt(getDate.getText().toString().substring(0, 2));
        month = Integer.parseInt(getDate.getText().toString().substring(3, 5)) - 1;
        year = Integer.parseInt(getDate.getText().toString().substring(6, 10));
        hour = Integer.parseInt(getTime.getText().toString().substring(0, 2));
        minute = Integer.parseInt(getTime.getText().toString().substring(3, 5));

        taskList.add(new Task(setName, setAssociated, new Date(year, month, day, hour, minute)));

        setContentView(R.layout.daily_classes_view);
        sortItems();
        displayDaily();


    }


    public void resetAssignments(){
        for (int i = 0; i < 7; i++){
            assignNum[i] = 0;
            taskNum[i] = 0;
        }
    }

    public void sortItems(){
        Gson gson = new Gson();

        // Sorts assignments by due date
        if (assignmentList.size() > 1) {
            Collections.sort(assignmentList, new Comparator<Assignment>() {
                public int compare(Assignment a, Assignment b) {
                    return a.dueDate.compareTo(b.dueDate);
                }
            });
        }
        editor.putString("assignKey", gson.toJson(assignmentList)).apply();
        assignNet = assignmentList.size();
        //assignAdapter.notifyDataSetChanged();

        if (taskList.size() > 1) {
            Collections.sort(taskList, new Comparator<Task>() {
                public int compare(Task a, Task b) {
                    return a.dueDate.compareTo(b.dueDate);
                }
            });
        }
        editor.putString("taskKey", gson.toJson(taskList)).apply();
        taskNet = taskList.size();
        //taskAdapter.notifyDataSetChanged();


        // Recalculates number of assignments in each class
        resetAssignments();
        for (int i = 0; i < classNumbers.length; i++){
            for (Assignment assignment : assignmentList){
                if (assignment.classPeriod.equals(classNumbers[i])){
                    for (Task task : taskList){
                        if (task.assignment.equals(assignment.name)){
                            taskNum[i]++;
                        }
                    }
                    assignNum[i]++;
                }
            }
        }
    }

    // Fridays alternate between A and B days - checks if Friday in question is an A day
    public Boolean isFridayA(){
        Date [] aDays = new Date[19];
        aDays[0] = new Date (2021, 8, 27);
        aDays[1] = new Date (2021, 9, 3);
        aDays[2] = new Date (2021, 9, 17);
        aDays[3]= new Date (2021, 10, 1);
        aDays[4] = new Date (2021, 10, 29);
        aDays[5] = new Date (2021, 11, 12);
        aDays[6] = new Date (2021, 12, 3);
        aDays[7] = new Date (2021, 12, 17);
        aDays[8] = new Date (2022, 1, 14);
        aDays[9] = new Date (2022, 1, 21);
        aDays[10] = new Date (2022, 2, 4);
        aDays[11] = new Date (2022, 2, 18);
        aDays[12] = new Date (2022, 2, 25);
        aDays[13] = new Date (2022, 3, 11);
        aDays[14] = new Date (2022, 4, 1);
        aDays[15] = new Date (2022, 4, 22);
        aDays[16] = new Date (2022, 5, 7);
        aDays[17] = new Date (2022, 5, 20);
        aDays[18] = new Date (2022, 5, 27);

        for (int i = 0; i < aDays.length; i++){
            if (currentDate.equals(aDays[i])){
                return true;
            }
        }
        return false;
    }
}