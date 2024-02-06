package com.example.cs_2340_project1;

import static com.example.cs_2340_project1.AssignmentFragment.updateAssignmentList;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleViewAdapterAssignment extends RecyclerView.Adapter<RecycleViewAdapterAssignment.MyViewHolder> {

    ArrayList<AssignmentModel> assignmentBundle;
    ArrayList<AssignmentModel> assignmentDay;

    public RecycleViewAdapterAssignment(ArrayList<AssignmentModel> assignmentDay, ArrayList<AssignmentModel> assignmentBundle) {
        this.assignmentBundle = assignmentBundle;
        this.assignmentDay = assignmentDay;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_assignment,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.className.setText(assignmentDay.get(position).getCourseName());
        holder.title.setText(assignmentDay.get(position).getTitle());
        holder.time.setText(assignmentDay.get(position).getTime().toString());
        holder.date.setText(assignmentDay.get(position).getDate().toString());
        holder.location.setText(assignmentDay.get(position).getLocation());

        holder.isComplete.setChecked(assignmentDay.get(position).isComplete);
        holder.isComplete.setOnClickListener(v -> {
            assignmentDay.get(position).setComplete(!assignmentDay.get(position).isComplete());
            updateAssignmentList();
        });

        holder.parentLayout.setOnClickListener(v -> {
            AddAssignment addAssignment = new AddAssignment();

            Bundle bundle2 = new Bundle();
            bundle2.putSerializable("send", assignmentBundle);
            bundle2.putSerializable("position", holder.getAbsoluteAdapterPosition());
            addAssignment.setArguments(bundle2);
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main, addAssignment).commit();
        });
    }

    @Override
    public int getItemCount() {
        return assignmentDay.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView className;
        TextView title;
        TextView date;
        TextView time;
        TextView location;
        CheckBox isComplete;
        CardView parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.cl_class);
            title = itemView.findViewById(R.id.cl_title);
            date = itemView.findViewById(R.id.cl_date);
            time = itemView.findViewById(R.id.cl_time);
            location = itemView.findViewById(R.id.cl_location);
            isComplete = itemView.findViewById(R.id.isCompleteCheckbox);
            parentLayout = itemView.findViewById(R.id.one_line_assignment);
        }
    } // MyViewHolder
}
