package com.example.studyplanner;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalenderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalenderFragment extends Fragment {
    CalendarView calendarView;
    TextView table_date;
    TextView study_events;
    TextView assgn_events;
    TextView lecture_events;
    TextView exam_events;

    Database DB;
    SQLiteDatabase EventsDB;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalenderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalenderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalenderFragment newInstance(String param1, String param2) {
        CalenderFragment fragment = new CalenderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_calender, container, false);

        DB = new Database(getContext());
        EventsDB = DB.getDatabase();

        table_date = view.findViewById(R.id.table_date_id);
        study_events = view.findViewById(R.id.table_study_id);
        assgn_events = view.findViewById(R.id.table_assgn_id);
        lecture_events = view.findViewById(R.id.table_lect_id);
        exam_events = view.findViewById(R.id.table_exam_id);

        calendarView = view.findViewById(R.id.calendarView);

        calendarView
                .setOnDateChangeListener(
                        new CalendarView
                                .OnDateChangeListener() {
                            @Override

                            // In this Listener have one method
                            // and in this method we will
                            // get the value of DAYS, MONTH, YEARS
                            public void onSelectedDayChange(
                                    @NonNull CalendarView view,
                                    int year,
                                    int month,
                                    int dayOfMonth)
                            {

                                // Store the value of date with
                                // format in String type Variable
                                // Add 1 in month because month
                                // index is start with 0
                                String Date
                                        = dayOfMonth + "-"
                                        + (month + 1) + "-" + year;


                                ArrayList<String> events_info = getEventsInfo(Date);
                                // set this date in TextView for Display
                                table_date.setText(Date);

                                study_events.setText(events_info.get(0));
                                assgn_events.setText(events_info.get(1));
                                lecture_events.setText(events_info.get(2));
                                exam_events.setText(events_info.get(3));

                            }
                        });

        return view;
    }

    public ArrayList<String> getEventsInfo(String Date)
    {
        ArrayList<String> result = new ArrayList<>();

        Cursor cursor = EventsDB.rawQuery("Select * from EventDetails where eventType=? AND date=?", new String[] {"Study Plan", Date});
        result.add(""+cursor.getCount());

        cursor = EventsDB.rawQuery("Select * from EventDetails where eventType=? AND date=?", new String[] {"Assignments", Date});
        result.add(""+cursor.getCount());

        cursor = EventsDB.rawQuery("Select * from EventDetails where eventType=? AND date=?", new String[] {"Lectures", Date});
        result.add(""+cursor.getCount());

        cursor = EventsDB.rawQuery("Select * from EventDetails where eventType=? AND date=?", new String[] {"Exams", Date});
        result.add(""+cursor.getCount());


        return result;
    }
}