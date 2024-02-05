package com.example.cs_2340_project1;

import static com.example.cs_2340_project1.GlobalControllerService.assignmentModels;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AssignmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AssignmentFragment extends Fragment implements Serializable {

    // Add button
    Button add_btn;

    Spinner sortByOptionsSpinner;
    String[] sortingOptions = {"Incomplete", "By Course", "Completed", "All"};
    public static int selectedSort = 0;

    // Creating ArrayList for bundle data
    ArrayList<AssignmentModel> assignmentBundle = new ArrayList<>();
    private static ArrayList<AssignmentModel> assignmentList = new ArrayList<>();

    private static RecyclerView recyclerViewList;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AssignmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AssignmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AssignmentFragment newInstance(String param1, String param2) {
        AssignmentFragment fragment = new AssignmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeAssignmentList();

        // Grabbing sent over bundle
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assignment, container, false);
        // For the recycler view

        recyclerViewList = view.findViewById(R.id.assignments_list);
        RecycleViewAdapterAssignment adapter = new RecycleViewAdapterAssignment(assignmentList, assignmentBundle);

        recyclerViewList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewList.setAdapter(adapter);


        add_btn = view.findViewById(R.id.add_assignment);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAssignment addFragment = new AddAssignment();
                Bundle bundle2 =  new Bundle();
                bundle2.putSerializable("send", assignmentBundle);
                addFragment.setArguments(bundle2);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_main, addFragment).commit();
            }
        });

        //Sorting Option Dropdown
        sortByOptionsSpinner = view.findViewById(R.id.sortByOptions);
        sortByOptionsSpinner.setAdapter(new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                sortingOptions)
        );
        sortByOptionsSpinner.setOnItemSelectedListener(
            new AdapterView.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                   selectedSort = i;
                   updateAssignmentList();
               }

               @Override
               public void onNothingSelected(AdapterView<?> adapterView) {
                   //Do nothing
               }
           }
        );
        return view;
    }

    public static void updateAssignmentList(){
        initializeAssignmentList();
        switch(selectedSort) {
            case 0: //Incomplete
                assignmentList.removeIf(i -> i.isComplete);
                break;

            case 1: //By Course
                assignmentList.sort((a,b) -> a.getCourseName().compareTo(b.getCourseName()) >= 1 ? 1 : -1);
                break;

            case 2: //Completed
                assignmentList.removeIf(i -> i.isComplete == false);
                break;
        }

        RecycleViewAdapterAssignment adapter = new RecycleViewAdapterAssignment(assignmentList, new ArrayList<>());
        recyclerViewList.setAdapter(adapter);
    }

    private static void initializeAssignmentList(){
        assignmentList.clear();
        assignmentList.addAll(assignmentModels);

        //Sort by Due Date
        assignmentList.sort((a,b) -> {
            if (a.date.equals(b.date)){
                return a.time.isAfter(b.time) ? 1 : -1;
            } else {
                return a.date.isAfter(b.date) ? 1 : -1;
            }
        });
    }

    @Override
    public void onDestroy() {
        assignmentModels.clear();
        assignmentModels.addAll(assignmentList);
        super.onDestroy();
    }
}