package com.example.goglobe;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class TripDetailsActivity extends AppCompatActivity {

    private TextView tripTitle;
    private TextView tripLocation;
    private TextView tripStartDate;
    private TextView tripEndDate;
    private TextView tripDuration;
    private int tripID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        tripTitle = findViewById(R.id.trip_title);
        tripLocation = findViewById(R.id.trip_location);
        tripStartDate = findViewById(R.id.trip_start_date);
        tripEndDate = findViewById(R.id.trip_end_date);
        tripDuration = findViewById(R.id.trip_duration);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String tripName = extras.getString("trip_name");
            String location = extras.getString("trip_location");
            long startDateMillis = extras.getLong("start_date");
            long endDateMillis = extras.getLong("end_date");
            tripID = extras.getInt("trip_id");
            android.util.Log.d("Set trip_id", "setting trip_id with ID: " + tripID); // log statement

            Date startDate = new Date(startDateMillis);
            Date endDate = new Date(endDateMillis);

            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault());

            long durationInMilliSeconds = endDateMillis - startDateMillis;
            long durationInDays = TimeUnit.MILLISECONDS.toDays(durationInMilliSeconds);

            tripTitle.setText(tripName);
            tripLocation.setText(location);
            tripStartDate.setText(dateFormat.format(startDate));
            tripEndDate.setText(dateFormat.format(endDate));

            //Set the number of days to TextView
            String durationText = getString(R.string.trip_duration, durationInDays);
            tripDuration.setText(durationText);
        }

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTrip();
            }
        });

    }

    private void deleteTrip() {
        android.util.Log.d("DeleteTrip", "Deleting trip with ID: " + tripID); // log statement

        if(tripID != -1) {
            android.util.Log.d("DeleteTrip", "Inside if statement NOT -1 ... Deleting trip with ID: " + tripID); // log statement
            DataBaseHelper dbHelper = new DataBaseHelper(TripDetailsActivity.this);
            boolean success = dbHelper.deleteTrip(tripID);
            if (success) {
                Toast.makeText(TripDetailsActivity.this, "Trip deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TripDetailsActivity.this, "Failed to delete trip", Toast.LENGTH_SHORT).show();
            }
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}