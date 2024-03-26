package com.example.goglobe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Boolean isLoggedIn; // keeps track if user is logged in or not
        ArrayList<TripsDataSet> dataList = new ArrayList<>();

        Context context = MainActivity.this;

        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonRegister = findViewById(R.id.buttonRegister);
        Button buttonMakeTrip = findViewById(R.id.buttonMakeTrip);
        RecyclerView tripsRecyclerView;

        tripsRecyclerView = findViewById(R.id.tripsRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        tripsRecyclerView.setLayoutManager(linearLayoutManager);

        // TEMPORARY: Just to test the DataSet
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 1);
        Date tempStartDate = calendar.getTime();
        calendar.set(2024, Calendar.DECEMBER, 20);
        Date tempEndDate = calendar.getTime();

        dataList.add(new TripsDataSet(0, "Quebec Fun", "Montreal, Quebec, Canada",
                tempStartDate, tempEndDate, 0));
        dataList.add(new TripsDataSet(0, "Ottawa Outing", "Ottawa, Ontario, Canada",
                tempStartDate, tempEndDate, 0));
        dataList.add(new TripsDataSet(0, "Tokyo Trip", "Tokyo, Japan",
                tempStartDate, tempEndDate, 0));
        dataList.add(new TripsDataSet(0, "Shanghai Sightseeing", "Shanghai, China",
                tempStartDate, tempEndDate, 0));
        dataList.add(new TripsDataSet(0, "Stockholm Searching", "Stockholm, Sweden",
                tempStartDate, tempEndDate, 0));
        dataList.add(new TripsDataSet(0, "Hanoi Hideaway", "Hanoi, Vietnam",
                tempStartDate, tempEndDate, 0));

        TripsAdapter tripsAdapter = new TripsAdapter(dataList, MainActivity.this);
        tripsRecyclerView.setAdapter(tripsAdapter);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start an activity to login
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start an activity to register
                Intent intent = new Intent(context, RegisterActivity.class);
                context.startActivity(intent);
            }
        });

        buttonMakeTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start an activity to make a new trip
                Intent intent = new Intent(context, CreateTripActivity.class);
                context.startActivity(intent);
            }
        });
    }
}