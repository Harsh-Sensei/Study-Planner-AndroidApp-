package com.example.studyplanner;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AssignmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AssignmentFragment extends Fragment {

    RecyclerView recyclerView;
    Database DB;
    SQLiteDatabase EventsDB;
    ArrayList<String> events_arr;
    ArrayList<String> date_arr;
    ArrayList<String> time_arr;
    ArrayList<String> description_arr;

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

    @SuppressLint("Range")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        DB = new Database(getContext());
        EventsDB = DB.getDatabase();
        events_arr = new ArrayList<>();
        date_arr = new ArrayList<>();
        time_arr = new ArrayList<>();
        description_arr = new ArrayList<>();

        Cursor cursor = EventsDB.rawQuery("Select * from EventDetails where eventType=?", new String[] {"Assignments"});

        if (cursor.moveToFirst()) {
            do {
                events_arr.add(cursor.getString(cursor.getColumnIndex("event")));
                date_arr.add(cursor.getString(cursor.getColumnIndex("date")));
                time_arr.add(cursor.getString(cursor.getColumnIndex("startTime"))+"-"+
                        cursor.getString(cursor.getColumnIndex("endTime"))
                );
                description_arr.add(cursor.getString(cursor.getColumnIndex("description")));

            } while (cursor.moveToNext());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment, container, false);


        recyclerView = view.findViewById(R.id.recycler_view_assignment);

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getContext(), events_arr, date_arr, time_arr, description_arr);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);


        return view;
    }
}