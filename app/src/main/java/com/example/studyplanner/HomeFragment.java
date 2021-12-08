package com.example.studyplanner;

import static java.lang.Math.abs;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import com.example.studyplanner.Database;
import com.github.sundeepk.compactcalendarview.domain.Event;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    Database DB;
    SQLiteDatabase EventsDB;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        DB = new Database(getContext());
        EventsDB = DB.getDatabase();

        ArrayList<Integer> getNum = getEventCount();


        PieChart pieChart = view.findViewById(R.id.pieChart_view);

        ArrayList<PieEntry> parameter = new ArrayList<>();
        try{
            parameter.add(new PieEntry(getNum.get(0), "Study Plan"));
            parameter.add(new PieEntry(getNum.get(1), "Assignments"));
            parameter.add(new PieEntry(getNum.get(2), "Lectures"));
            parameter.add(new PieEntry(getNum.get(3), "Exams"));
        }
        catch(Exception e){
            e.printStackTrace();
        }

        PieDataSet pieDataSet = new PieDataSet(parameter, "");

        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        pieChart.getDescription().setEnabled(false);

        if(getNum.get(0)==0 && getNum.get(1)==0 && getNum.get(2)==0 && getNum.get(3)==0)
        {
            pieChart.setCenterText("Add events");
        }
        else
        {
            pieChart.setCenterText("Events");
        }
        pieChart.setData(pieData);
        pieChart.animate();
        pieChart.invalidate();

        return view;
    }

    public ArrayList<Integer> getEventCount()
    {

        ArrayList<Integer> result = new ArrayList<>();

        Cursor cursor = EventsDB.rawQuery("Select * from EventDetails where eventType=?", new String[] {"Study Plan"});
        result.add(cursor.getCount());

        cursor = EventsDB.rawQuery("Select * from EventDetails where eventType=?", new String[] {"Assignments"});
        result.add(cursor.getCount());

        cursor = EventsDB.rawQuery("Select * from EventDetails where eventType=?", new String[] {"Lectures"});
        result.add(cursor.getCount());

        cursor = EventsDB.rawQuery("Select * from EventDetails where eventType=?", new String[] {"Exams"});
        result.add(cursor.getCount());

        return result;

    }

}