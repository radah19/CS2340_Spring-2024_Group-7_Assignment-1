package com.example.cs_2340_project1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

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

    private RecyclerView recyclerView;
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
        recyclerView = view.findViewById(R.id.classList);
        recyclerView.setHasFixedSize(true);
        mAdapter = new RecycleViewAdapter(classesBundle);
        RecycleViewAdapter adapter = new RecycleViewAdapter(classesBundle);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        add_btn = view.findViewById(R.id.add_class);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddClass addFragment = new AddClass();
                Bundle bundle2 =  new Bundle();
                bundle2.putSerializable("send", classesBundle);
                addFragment.setArguments(bundle2);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.Maincontainer, addFragment).commit();


            }
        });
        return view;
    }
}