package com.example.android.student_planner_ia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
        TextView cellTime = (TextView) convertView.findViewById(R.id.cell_time);

        cellName.setText(assignment.name);
        cellDate.setText(assignment.dueDate.getDate() + "/" + (assignment.dueDate.getMonth() + 1) + "/" + assignment.dueDate.getYear());
        cellPeriod.setText(assignment.classPeriod);
        cellTime.setText(assignment.dueDate.getHours() + ":" + assignment.dueDate.getMinutes());

        return convertView;
    }


}
