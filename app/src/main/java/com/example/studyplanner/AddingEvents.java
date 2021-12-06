package com.example.studyplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import android.database.sqlite.SQLiteDatabase;
import com.example.studyplanner.Database;

public class AddingEvents extends AppCompatActivity {

    Button add_event_btn;
    EditText eventName, date, day, startTime, endTime, description;
    Spinner eventType;

    Database DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_events);

        add_event_btn = findViewById(R.id.add_btn_id);
        eventName = findViewById(R.id.eventName_id);
        date = findViewById(R.id.date_id);
        startTime = findViewById(R.id.startTime_id);
        endTime = findViewById(R.id.endTime_id);
        description = findViewById(R.id.description_id);
        eventType = findViewById(R.id.spinner_id);

        DB = new Database(this);

        add_event_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                String eventName_str = eventName.getText().toString();
                String date_str = date.getText().toString();
                String startTime_str = startTime.getText().toString();
                String endTime_str = endTime.getText().toString();
                String description_str = description.getText().toString();
                String eventType_str = eventType.getSelectedItem().toString();

                Cursor cursor = DB.getData(eventName_str);

                if(cursor.getCount()>0)
                {
                    Toast.makeText(getApplicationContext(),"Event already exists", Toast.LENGTH_LONG).show();
                }
                else
                {
                    DB.insertData(eventName_str,date_str, eventType_str, startTime_str, endTime_str, description_str);
                    Toast.makeText(getApplicationContext(),"Event successfully created",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddingEvents.this, TabbedPlanner.class);
                    startActivity(intent);
                    finish();
                }



            }
        });


    }
}