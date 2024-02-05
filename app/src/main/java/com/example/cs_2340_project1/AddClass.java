package com.example.cs_2340_project1;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddClass#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddClass extends Fragment implements Serializable {

    // Button for sending class data
    Button submit_btn;
    Button cancel_btn;
    Button del_btn;

    String[] days;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    // Variables
    String course, date, professor, section, location, time;
    // Different EditTexts
    EditText courseInput;
    EditText dateInput;
    EditText timeInput;
    EditText professorInput;
    EditText sectionInput;
    EditText locationInput;


    int id, hour, minute;
    // Creating ArrayList for data
    ArrayList<ClassesModel> classesModel = new ArrayList<>();

    ArrayList<ClassesModel> classesDays = new ArrayList<>();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddClass() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddClass.
     */
    // TODO: Rename and change types and number of parameters
    public static AddClass newInstance(String param1, String param2) {
        AddClass fragment = new AddClass();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        days = getResources().getStringArray(R.array.daysOfWeek);
        checkedItems =  new boolean[days.length];
        Bundle recievedBundle = getArguments();
        id = recievedBundle.getInt("position", -1);
        if (recievedBundle != null) {
            classesModel = (ArrayList<ClassesModel>) recievedBundle.getSerializable("send");
            classesDays = (ArrayList<ClassesModel>) recievedBundle.getSerializable("day");
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
        View view = inflater.inflate(R.layout.fragment_add_class, container, false);
        // Different EditTexts
        submit_btn = view.findViewById(R.id.submit_btn);
        cancel_btn = view.findViewById(R.id.cancel_btn);
        del_btn = view.findViewById(R.id.del_btn);
        // Assigning
        courseInput = (EditText) view.findViewById(R.id.class_name);
        dateInput = (EditText) view.findViewById(R.id.class_date);
        timeInput =  (EditText) view.findViewById(R.id.class_time);
        professorInput = (EditText) view.findViewById(R.id.class_professor);
        sectionInput = (EditText) view.findViewById(R.id.class_section);
        locationInput = (EditText) view.findViewById(R.id.class_location);
        // Setting the edit field
        // Onclick for cancel button
        Bundle recievedBundle = getArguments();
        id = recievedBundle.getInt("position", -1);
        if (id >= 0) {
            // Setting the edit field
            courseInput.setText(classesDays.get(id).getCourseName());
            timeInput.setText(classesDays.get(id).getDateAndtime());
            dateInput.setText(classesDays.get(id).getRepeat());
            professorInput.setText(classesDays.get(id).getProfessor());
            sectionInput.setText(classesDays.get(id).getSection());
            locationInput.setText(classesDays.get(id).getLocation());
        } // if


        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //cancelButtonPressed();

                ClassesFragment classFragment = new ClassesFragment();
                Bundle bundle =  new Bundle();
                bundle.putSerializable("userClasses", classesModel);
                classFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_main, classFragment).commit();
            }
        });

        // Onclick for submit button
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id >= 0) {
                    // Alert to confirm delete
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setMessage("Are you sure you want to edit this class?").setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            classesModel.get(id).setCourseName(courseInput.getText().toString());
                            classesModel.get(id).setDateAndtime(timeInput.getText().toString());
                            classesModel.get(id).setRepeat(dateInput.getText().toString());
                            classesModel.get(id).setSection(sectionInput.getText().toString());
                            classesModel.get(id).setLocation(locationInput.getText().toString());
                            classesModel.get(id).setProfessor(professorInput.getText().toString());
                            // editing
                            ClassesFragment classFragment = new ClassesFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("userClasses", classesModel);
                            classFragment.setArguments(bundle);
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.content_main, classFragment).commit();
                        }
                    }) .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // editing
                            ClassesFragment classFragment = new ClassesFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("userClasses", classesModel);
                            classFragment.setArguments(bundle);
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.content_main, classFragment).commit();
                        }
                    });
                    AlertDialog mDialog = alertDialog.create();
                    mDialog.show();

                } else {
                    // blank form
                    // storeClassInformation(view);
                    courseInput = (EditText) getActivity().findViewById(R.id.class_name);
                    dateInput = (EditText) getActivity().findViewById(R.id.class_date);
                    timeInput = (EditText) getActivity().findViewById(R.id.class_time);
                    professorInput = (EditText) getActivity().findViewById(R.id.class_professor);
                    sectionInput = (EditText) getActivity().findViewById(R.id.class_section);
                    locationInput = (EditText) getActivity().findViewById(R.id.class_location);
                    // Assigning variables
                    course = courseInput.getText().toString();
                    date = dateInput.getText().toString();
                    time = timeInput.getText().toString();
                    professor = professorInput.getText().toString();
                    section = sectionInput.getText().toString();
                    location = locationInput.getText().toString();
                    classesModel.add(new ClassesModel(location, section, professor, course, time, date));
                    // Creating bundle for data transfer
                    ClassesFragment classFragment = new ClassesFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userClasses", classesModel);
                    classFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content_main, classFragment).commit();
                } // else
            }
        });
        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                // Alert to confirm delete
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Are you sure you want to delete this class?").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        classesModel.remove(id);
                        ClassesFragment classFragment = new ClassesFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userClasses", classesModel);
                        classFragment.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content_main, classFragment).commit();
                    }
                }) .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClassesFragment classFragment = new ClassesFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userClasses", classesModel);
                        classFragment.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content_main, classFragment).commit();
                    }
                });
                AlertDialog mDialog = alertDialog.create();
                mDialog.show();
            }
        });

        timeInput = view.findViewById(R.id.class_time);
        timeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker(view);
            }
        });

        dateInput = view.findViewById(R.id.class_date);
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
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        mBuilder.setTitle("What days do you have this class?");
        mBuilder.setMultiChoiceItems(days, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                if (isChecked) {
                    if (! mUserItems.contains(position)){
                        mUserItems.add(position);
                    } else {
                        mUserItems.remove(position);
                    } // if
                } // if
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item = "";
                for (int i = 0; i < mUserItems.size(); i++) {
                    item = item + days[mUserItems.get(i)];
                    if (i != mUserItems.size() - 1) {
                        item = item + ", ";
                    } // if
                } // for
                dateInput.setText(item);
            }
        });
        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();;
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }// datePicker



}