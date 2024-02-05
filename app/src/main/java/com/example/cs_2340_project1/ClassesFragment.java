package com.example.cs_2340_project1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassesFragment extends Fragment implements Serializable {

    // Add button

    Button add_btn;

    // Creating ArrayList for bundle data
    ArrayList<ClassesModel> classesBundle = new ArrayList<>();
    ArrayList<ClassesModel> classesMon = new ArrayList<>();
    ArrayList<ClassesModel> classesTues = new ArrayList<>();
    ArrayList<ClassesModel> classesWed = new ArrayList<>();
    ArrayList<ClassesModel> classesThur = new ArrayList<>();
    ArrayList<ClassesModel> classesFri = new ArrayList<>();

    private RecyclerView recyclerViewMon;
    private RecyclerView recyclerViewTues;
    private RecyclerView recyclerViewWed;
    private RecyclerView recyclerViewThurs;

    private RecyclerView recyclerViewFri;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClassesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClassesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassesFragment newInstance(String param1, String param2) {
        ClassesFragment fragment = new ClassesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle sentBundle = getArguments();
        if (sentBundle != null) {
            classesBundle = (ArrayList<ClassesModel>) sentBundle.getSerializable("userClasses");
            for (ClassesModel i: classesBundle) {
                if (i.getRepeat().contains("Monday")) {
                    classesMon.add(i);
                } if (i.getRepeat().contains("Tuesday")) {
                    classesTues.add(i);
                } if (i.getRepeat().contains("Wednesday")) {
                    classesWed.add(i);
                } if (i.getRepeat().contains("Thursday")) {
                    classesThur.add(i);
                } if (i.getRepeat().contains("Friday")) {
                    classesFri.add(i);
                }// if
            } // for
        } // if

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
        View view = inflater.inflate(R.layout.fragment_classes, container, false);



        // For the recycler view

        recyclerViewMon = view.findViewById(R.id.monday);
        RecycleViewAdapter adapter = new RecycleViewAdapter(classesMon, classesBundle);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewMon.setLayoutManager(mLayoutManager);
        recyclerViewMon.setAdapter(adapter);

        recyclerViewTues = view.findViewById(R.id.tuesday);
        TuesdayAdapter adapterTues = new TuesdayAdapter(classesTues, classesBundle);
        RecyclerView.LayoutManager tLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTues.setLayoutManager(tLayoutManager);
        recyclerViewTues.setAdapter(adapterTues);

        recyclerViewWed = view.findViewById(R.id.wednesday);
        WednesdayAdapter adapterWed = new WednesdayAdapter(classesWed, classesBundle);
        RecyclerView.LayoutManager wLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewWed.setLayoutManager(wLayoutManager);
        recyclerViewWed.setAdapter(adapterWed);


        recyclerViewThurs = view.findViewById(R.id.thursday);
        ThursdayAdapter adapterThurs = new ThursdayAdapter(classesThur, classesBundle);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewThurs.setLayoutManager(rLayoutManager);
        recyclerViewThurs.setAdapter(adapterThurs);

        recyclerViewFri = view.findViewById(R.id.friday);
        FridayAdapter adapterFri = new FridayAdapter(classesFri, classesBundle);
        RecyclerView.LayoutManager fLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFri.setLayoutManager(fLayoutManager);
        recyclerViewFri.setAdapter(adapterFri);

        add_btn = view.findViewById(R.id.add_class);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddClass addFragment = new AddClass();
                Bundle bundle2 =  new Bundle();
                bundle2.putSerializable("send", classesBundle);
                addFragment.setArguments(bundle2);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_main, addFragment).commit();


            }
        });
        return view;
    }
}