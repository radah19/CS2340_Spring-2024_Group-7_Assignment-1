package com.example.cs_2340_project1;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ThursdayAdapter extends RecyclerView.Adapter<ThursdayAdapter.MyViewHolder> {

    ArrayList<ClassesModel> classesBundle;
    ArrayList<ClassesModel> classesDay;

    public ThursdayAdapter(ArrayList<ClassesModel> classesDay, ArrayList<ClassesModel> classesBundle) {
        this.classesBundle = classesBundle;
        this.classesDay = classesDay;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_class,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.className.setText(classesDay.get(position).getCourseName());
        holder.section.setText(classesDay.get(position).getSection());
        holder.professor.setText(classesDay.get(position).getProfessor());
        holder.date.setText(classesDay.get(position).getDateAndtime());
        holder.repeat.setText(classesDay.get(position).getRepeat());
        holder.location.setText(classesDay.get(position).getLocation());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddClass addClass = new AddClass();
                /*
                Bundle bundle = new Bundle();
                Toast.makeText(v.getContext(), String.valueOf(holder.getAbsoluteAdapterPosition()), Toast.LENGTH_SHORT).show();
                bundle.putSerializable("position", holder.getAbsoluteAdapterPosition());
                addClass.setArguments(bundle);
                 */
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("send", classesBundle);
                bundle2.putSerializable("position", holder.getAbsoluteAdapterPosition());
                addClass.setArguments(bundle2);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main, addClass).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return classesDay.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView className;
        TextView section;
        TextView professor;
        TextView date;
        TextView repeat;
        TextView location;
        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.cl_class);
            section = itemView.findViewById(R.id.cl_section);
            professor = itemView.findViewById(R.id.cl_professor);
            date = itemView.findViewById(R.id.cl_date);
            repeat = itemView.findViewById(R.id.cl_repeat);
            location = itemView.findViewById(R.id.cl_location);
            parentLayout = itemView.findViewById(R.id.one_line_class);
        }
    } // MyViewHolder
}
