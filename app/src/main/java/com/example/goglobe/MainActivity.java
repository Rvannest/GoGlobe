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

import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {

    private Button buttonLogin, buttonRegister, buttonMakeTrip;
    private TextView tripsHeader;
    private RecyclerView tripsRecyclerView;
    private DataBaseHelper dbHelper;
    private TripsAdapter tripsAdapter;
    private ArrayList<TripsDataSet> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = MainActivity.this;                    // sets the context for creating intents

        // initialize components
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonMakeTrip = findViewById(R.id.buttonMakeTrip);
        tripsHeader = findViewById(R.id.tripsHeader);
        tripsRecyclerView = findViewById(R.id.tripsRecyclerView);
        dbHelper = new DataBaseHelper(MainActivity.this);
        dataList = new ArrayList<>();

        // set the linearLayoutManager for the tripsRecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        tripsRecyclerView.setLayoutManager(linearLayoutManager);

        // TEMPORARY: Just to test the DataSet
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2024, Calendar.DECEMBER, 1);
//        Date tempStartDate = calendar.getTime();
//        calendar.set(2024, Calendar.DECEMBER, 20);
//        Date tempEndDate = calendar.getTime();
//        dataList.add(new TripsDataSet(0, "Quebec Fun", "Montreal, Quebec, Canada",
//                tempStartDate, tempEndDate, 0));
//        dataList.add(new TripsDataSet(0, "Ottawa Outing", "Ottawa, Ontario, Canada",
//                tempStartDate, tempEndDate, 0));
//        dataList.add(new TripsDataSet(0, "Tokyo Trip", "Tokyo, Japan",
//                tempStartDate, tempEndDate, 0));
//        dataList.add(new TripsDataSet(0, "Shanghai Sightseeing", "Shanghai, China",
//                tempStartDate, tempEndDate, 0));
//        dataList.add(new TripsDataSet(0, "Stockholm Searching", "Stockholm, Sweden",
//                tempStartDate, tempEndDate, 0));
//        dataList.add(new TripsDataSet(0, "Hanoi Hideaway", "Hanoi, Vietnam",
//                tempStartDate, tempEndDate, 0));

        dataList = dbHelper.getUserTrips(getUserIdFromSession());
        tripsAdapter = new TripsAdapter(dataList, MainActivity.this);
        tripsRecyclerView.setAdapter(tripsAdapter);

        buttonLogin.setOnClickListener(new View.OnClickListener() {         // handle login button click
            @Override
            public void onClick(View v) {
                if (getUserIdFromSession() == -1) {
                    // Start an activity to login
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }
                else {
                    // Start an activity to login
                    SharedPreferences sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("userId", -1);
                    editor.apply();
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }

            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {      // handle register button click
            @Override
            public void onClick(View v) {
                // Start an activity to register
                Intent intent = new Intent(context, RegisterActivity.class);
                context.startActivity(intent);
            }
        });

        buttonMakeTrip.setOnClickListener(new View.OnClickListener() {      // handle create trip button click
            @Override
            public void onClick(View v) {
                // Check if the user is logged in
                int userId = getUserIdFromSession();
                if (userId != -1) {
                    // User is logged in, use user ID in create trip activity
                    Intent intent = new Intent(context, CreateTripActivity.class);
                    startActivity(intent);
                } else {
                    // User not logged in, do not let start activity
                    Toast.makeText(context, "Please log in to create a trip.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // check login status here, as the activity is about to start interacting with the user
        // if user not logged in, disable make trip button and prompt user to login
        if (getUserIdFromSession() == -1) {
            tripsHeader.setText("Please Register or Log-in.");
            buttonMakeTrip.setEnabled(false);

        }
        // if user is logged in, enable make trip button and set a header for recyclerView
        else {
            String username = dbHelper.getColumnUserUsername(getUserIdFromSession());
            tripsHeader.setText(username + "'s Trips:");
            buttonMakeTrip.setEnabled(true);
            dataList = dbHelper.getUserTrips(getUserIdFromSession());
            tripsAdapter = new TripsAdapter(dataList, MainActivity.this);
            tripsRecyclerView.setAdapter(tripsAdapter);
            buttonLogin.setText("Logout");
        }
    }

    // function to retrieve user ID from SharedPreferences for Create Trip Start
    private int getUserIdFromSession() {
        SharedPreferences sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("userId", -1); // If ID not found
    }
}