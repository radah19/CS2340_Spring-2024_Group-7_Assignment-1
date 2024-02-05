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

    String[] days;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    // Variables
    String course, date, title, location, time;
    boolean isComplete;
    // Different EditTexts
    EditText courseInput;
    EditText dateInput;
    EditText titleInput;
    EditText timeInput;
    EditText locationInput;
    CheckBox isCompleteInput;


    int id, hour, minute, year, month, day;
    // Creating ArrayList for data
    ArrayList<AssignmentModel> assignmentModel = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddAssignment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddAssignment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddAssignment newInstance(String param1, String param2) {
        AddAssignment fragment = new AddAssignment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TESTING!!!!
        /*
        Bundle recievedBundle2 = getArguments();
        if (recievedBundle2 != null) {
           Toast.makeText(getActivity(), String.valueOf(recievedBundle2.getSerializable("position")), Toast.LENGTH_SHORT).show();
        }
         */
        days = getResources().getStringArray(R.array.daysOfWeek);
        checkedItems =  new boolean[days.length];
        Bundle recievedBundle = getArguments();
        id = recievedBundle.getInt("position", -1);
        if (recievedBundle != null) {
            assignmentModel = (ArrayList<AssignmentModel>) recievedBundle.getSerializable("send");
            Toast.makeText(getActivity(), String.valueOf(assignmentModel.size()), Toast.LENGTH_SHORT).show();
        }
        // whether edit or blank form

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_assignment, container, false);
        // Different EditTexts
        submit_btn = view.findViewById(R.id.submit_btn);
        cancel_btn = view.findViewById(R.id.cancel_btn);
        del_btn = view.findViewById(R.id.del_btn);
        // Assigning
        courseInput = (EditText) view.findViewById(R.id.assignment_name);
        dateInput = (EditText) view.findViewById(R.id.assignment_date);
        timeInput =  (EditText) view.findViewById(R.id.assignment_time);
        titleInput = (EditText) view.findViewById(R.id.assignment_title);
        locationInput = (EditText) view.findViewById(R.id.assignment_location);
        // Setting the edit field
        // Onclick for cancel button
        Bundle recievedBundle = getArguments();
        id = recievedBundle.getInt("position", -1);
        if (id >= 0) {
            // Setting the edit field
            courseInput.setText(assignmentModels.get(id).getCourseName());
            timeInput.setText(assignmentModels.get(id).getTime().toString());
            dateInput.setText(assignmentModels.get(id).getDate().toString());
            titleInput.setText(assignmentModels.get(id).getTitle());
            locationInput.setText(assignmentModels.get(id).getLocation());
        } // if


        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //cancelButtonPressed();

                AssignmentFragment assignmentFragment = new AssignmentFragment();
                Bundle bundle =  new Bundle();
                bundle.putSerializable("userAssignments", assignmentModel);
                assignmentFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_main, assignmentFragment).commit();
            }
        });

        // Onclick for submit button
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id >= 0) {
                    // Alert to confirm delete
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setMessage("Are you sure you want to edit this assignment?").setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            assignmentModels.get(id).setCourseName(courseInput.getText().toString());
                            assignmentModels.get(id).setDate(
                                    LocalDate.parse(dateInput.getText().toString(),
                                    DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                            );
                            assignmentModels.get(id).setTime(LocalTime.parse(timeInput.getText().toString()));
                            assignmentModels.get(id).setTitle(titleInput.getText().toString());
                            assignmentModels.get(id).setLocation(locationInput.getText().toString());
                            // editing
                            AssignmentFragment assignmentFragment = new AssignmentFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("userAssignment", assignmentModel);
                            assignmentFragment.setArguments(bundle);
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
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
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.content_main, assignmentFragment).commit();
                        }
                    });
                    AlertDialog mDialog = alertDialog.create();
                    mDialog.show();

                } else {
                    // blank form
                    // storeAssignmentInformation(view);
                    courseInput = (EditText) getActivity().findViewById(R.id.assignment_name);
                    dateInput = (EditText) getActivity().findViewById(R.id.assignment_date);
                    timeInput = (EditText) getActivity().findViewById(R.id.assignment_time);
                    titleInput = (EditText) getActivity().findViewById(R.id.assignment_title);
                    locationInput = (EditText) getActivity().findViewById(R.id.assignment_location);
                    // Assigning variables
                    course = courseInput.getText().toString();
                    date = dateInput.getText().toString();
                    time = timeInput.getText().toString();
                    title = titleInput.getText().toString();
                    location = locationInput.getText().toString();

                    DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    assignmentModels.add(new AssignmentModel(location, title, course, LocalDate.from(df.parse(date)), LocalTime.parse(time), false));
                    // Creating bundle for data transfer
                    AssignmentFragment assignmentFragment = new AssignmentFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userAssignment", assignmentModel);
                    assignmentFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content_main, assignmentFragment).commit();
                } // else
            }
        });
        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                // Alert to confirm delete
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Are you sure you want to delete this assignment?").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        assignmentModels.remove(id);
                        AssignmentFragment assignmentFragment = new AssignmentFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userAssignment", assignmentModel);
                        assignmentFragment.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content_main, assignmentFragment).commit();
                    }
                }) .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AssignmentFragment assignmentFragment = new AssignmentFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userAssignment", assignmentModel);
                        assignmentFragment.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content_main, assignmentFragment).commit();
                    }
                });
                AlertDialog mDialog = alertDialog.create();
                mDialog.show();
            }
        });

        timeInput = view.findViewById(R.id.assignment_time);
        timeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker(view);
            }
        });

        dateInput = view.findViewById(R.id.assignment_date);
        dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(view);
            }
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



}