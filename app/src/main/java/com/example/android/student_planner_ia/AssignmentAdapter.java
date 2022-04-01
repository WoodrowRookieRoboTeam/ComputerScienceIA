package com.example.android.student_planner_ia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AssignmentAdapter extends ArrayAdapter<Assignment> {

    public AssignmentAdapter(Context context, ArrayList<Assignment> item){
        super(context, 0, item);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        Assignment assignment = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.assignment_cell, parent, false);
        }

        TextView cellName = (TextView) convertView.findViewById(R.id.cell_name);
        TextView cellDate = (TextView) convertView.findViewById(R.id.cell_date);
        TextView cellPeriod = (TextView) convertView.findViewById(R.id.cell_period);

        cellName.setText(assignment.name);
        cellDate.setText(assignment.dueDate.getHours() + ":" + assignment.dueDate.getMinutes() + "\t\t" + assignment.dueDate.getDay() + "/" + assignment.dueDate.getMonth() + "/" + assignment.dueDate.getYear());
        cellDate.setText(assignment.classPeriod);

        return convertView;
    }


}
