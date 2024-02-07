package com.example.cs_2340_project1;

import static com.example.cs_2340_project1.GlobalControllerService.assignmentModels;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddClass#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAssignment extends Fragment implements Serializable {

    // Button for sending class data
    Button submit_btn;
    Button cancel_btn;
    Button del_btn;

    // Variables
    String course, date, title, location, time;
    String[] days;
    boolean[] checkedItems;

    // Different EditTexts
    EditText courseInput, dateInput, titleInput, timeInput, locationInput;
    RadioButton radioItem, radioExam, radioAssignment;
    RadioGroup radioGroup;


    int id, hour, minute, year, month, day;
    // Creating ArrayList for data
    ArrayList<AssignmentModel> assignmentModel = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        days = getResources().getStringArray(R.array.daysOfWeek);
        checkedItems =  new boolean[days.length];
        Bundle recievedBundle = getArguments();
        id = recievedBundle.getInt("position", -1);
        if (recievedBundle != null) {
            assignmentModel = (ArrayList<AssignmentModel>) recievedBundle.getSerializable("send");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_assignment, container, false);

        submit_btn = view.findViewById(R.id.submit_btn);
        cancel_btn = view.findViewById(R.id.cancel_btn);
        del_btn = view.findViewById(R.id.del_btn);

        courseInput = view.findViewById(R.id.assignment_name);
        dateInput = view.findViewById(R.id.assignment_date);
        timeInput = view.findViewById(R.id.assignment_time);
        titleInput = view.findViewById(R.id.assignment_title);
        locationInput = view.findViewById(R.id.assignment_location);

        radioGroup = view.findViewById(R.id.itemTypeRadioGroup);
        radioItem = view.findViewById(R.id.radio_listitem);
        radioExam = view.findViewById(R.id.radio_exam);
        radioAssignment = view.findViewById(R.id.radio_assignment);

        Bundle recievedBundle = getArguments();
        id = recievedBundle.getInt("position", -1);
        if (id >= 0) {
            // Setting the edit field
            del_btn.setVisibility(view.VISIBLE);
            courseInput.setText(assignmentModels.get(id).getCourseName());
            timeInput.setText(assignmentModels.get(id).getTime().toString());
            dateInput.setText(assignmentModels.get(id).getDate().toString());
            titleInput.setText(assignmentModels.get(id).getTitle());
            locationInput.setText(assignmentModels.get(id).getLocation());

            radioItem.setChecked(assignmentModels.get(id).getItemType() == 0);
            radioAssignment.setChecked(assignmentModels.get(id).getItemType() == 1);
            radioExam.setChecked(assignmentModels.get(id).getItemType() == 2);

            boolean itemTypeSelectedIsListItem = assignmentModels.get(id).getItemType() == 0;
            locationInput.setVisibility( (itemTypeSelectedIsListItem) ? View.GONE : View.VISIBLE );
            courseInput.setVisibility( (itemTypeSelectedIsListItem) ? View.GONE : View.VISIBLE );

        } else {
            del_btn.setVisibility(view.GONE);
        }

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssignmentFragment assignmentFragment = new AssignmentFragment();

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_main, assignmentFragment).commit();
            }
        });

        // Onclick for submit button
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id >= 0) { // Edit Form

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setMessage("Are you sure you want to edit this assignment?").setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Update Element in List
                            assignmentModels.get(id).setCourseName(courseInput.getText().toString());
                            assignmentModels.get(id).setDate(
                                    LocalDate.parse(dateInput.getText().toString(),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                            );
                            assignmentModels.get(id).setTime(LocalTime.parse(timeInput.getText().toString()));
                            assignmentModels.get(id).setTitle(titleInput.getText().toString());
                            assignmentModels.get(id).setLocation(locationInput.getText().toString());
                            assignmentModels.get(id).setItemType(getItemTypeSelected());

                            AssignmentFragment assignmentFragment = new AssignmentFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("userAssignment", assignmentModel);
                            assignmentFragment.setArguments(bundle);
                            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.content_main, assignmentFragment).commit();
                        }
                    }) .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // editing
                            AssignmentFragment assignmentFragment = new AssignmentFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("userAssignment", assignmentModel);
                            assignmentFragment.setArguments(bundle);
                            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.content_main, assignmentFragment).commit();
                        }
                    });
                    AlertDialog mDialog = alertDialog.create();
                    mDialog.show();

                } else { //New Assignment Form
                    dateInput = getActivity().findViewById(R.id.assignment_date);
                    timeInput = getActivity().findViewById(R.id.assignment_time);
                    titleInput = getActivity().findViewById(R.id.assignment_title);
                    courseInput = getActivity().findViewById(R.id.assignment_name);
                    locationInput = getActivity().findViewById(R.id.assignment_location);

                    course = courseInput.getText().toString();
                    date = dateInput.getText().toString();
                    time = timeInput.getText().toString();
                    title = titleInput.getText().toString();
                    location = locationInput.getText().toString();

                    assignmentModels.add(new AssignmentModel(
                            title,
                            LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                            LocalTime.parse(time),
                            getItemTypeSelected(),
                            location,
                            course,
                            false));

                    // Creating bundle for data transfer
                    AssignmentFragment assignmentFragment = new AssignmentFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userAssignment", assignmentModel);
                    assignmentFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content_main, assignmentFragment).commit();
                }
            }
        });


        del_btn.setOnClickListener(v -> {
            // Alert to confirm delete
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setMessage("Are you sure you want to delete this assignment?").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    assignmentModels.remove(id);
                    AssignmentFragment assignmentFragment = new AssignmentFragment();

                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content_main, assignmentFragment).commit();
                }
            }) .setNegativeButton("Cancel", (dialog, which) -> {
                dialog.dismiss();
            });
            AlertDialog mDialog = alertDialog.create();
            mDialog.show();
        });

        timeInput = view.findViewById(R.id.assignment_time);
        timeInput.setOnClickListener(v -> popTimePicker(view));

        dateInput = view.findViewById(R.id.assignment_date);
        dateInput.setOnClickListener(v -> datePicker(view));

        radioItem = view.findViewById(R.id.radio_listitem);
        radioItem.setOnClickListener(v -> {
            locationInput.setVisibility(View.GONE);
            courseInput.setVisibility(View.GONE);
        });
        radioAssignment = view.findViewById(R.id.radio_assignment);
        radioAssignment.setOnClickListener(v -> {
            locationInput.setVisibility(View.VISIBLE);
            courseInput.setVisibility(View.VISIBLE);
        });
        radioExam = view.findViewById(R.id.radio_exam);
        radioExam.setOnClickListener(v -> {
            locationInput.setVisibility(View.VISIBLE);
            courseInput.setVisibility(View.VISIBLE);
        });
        return view;
    }

    public void popTimePicker(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (view1, selectedHour, selectedMinute) -> {
            hour = selectedHour;
            minute = selectedMinute;
            timeInput.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), onTimeSetListener, hour, minute, true);
        timePickerDialog.show();
    } // popTimePicker

    public void datePicker(View view) {
        DatePickerDialog.OnDateSetListener onDateSetListener = (view1, selectedYear, selectedMonth, selectedDay) -> {
                year = selectedYear;
                month = selectedMonth+1;
                day = selectedDay;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate selectedDate = LocalDate.of(year, month, day);
            dateInput.setText(formatter.format(selectedDate));
        };
        DatePickerDialog timePickerDialog = new DatePickerDialog(getActivity(), onDateSetListener,
                (year == 0) ? LocalDate.now().getYear() : year,
                (month-1 == -1) ? LocalDate.now().getMonthValue()-1 : month-1,
                (day == 0) ? LocalDate.now().getDayOfMonth() : day
        );
        timePickerDialog.show();
    }// datePicker

    public int getItemTypeSelected() {
        switch(radioGroup.getCheckedRadioButtonId()){
            case R.id.radio_listitem:
                return 0;

            case R.id.radio_assignment:
                return 1;

            case R.id.radio_exam:
                return 2;
        }

        return -1;
    }

}