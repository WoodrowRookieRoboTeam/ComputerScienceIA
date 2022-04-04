package com.example.android.student_planner_ia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task>{
    public TaskAdapter (Context context, ArrayList<Task> item){
        super(context, 0, item);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        Task task = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.assignment_cell, parent, false);
        }

        TextView cellName = (TextView) convertView.findViewById(R.id.cell_name);
        TextView cellDate = (TextView) convertView.findViewById(R.id.cell_date);
        TextView cellAssociate = (TextView) convertView.findViewById(R.id.cell_period);
        TextView cellTime = (TextView) convertView.findViewById(R.id.cell_time);

        cellName.setText(task.name);
        cellDate.setText(task.dueDate.getDate() + "/" + (task.dueDate.getMonth() + 1) + "/" + task.dueDate.getYear());
        cellAssociate.setText(task.assignment);
        cellTime.setText(task.dueDate.getHours() + ":" + task.dueDate.getMinutes());

        return convertView;
    }

}
