package com.example.goglobe;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class TripDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        TextView tripTitle = findViewById(R.id.trip_title);
        TextView tripLocation = findViewById(R.id.trip_location);
        TextView tripStartDate = findViewById(R.id.trip_start_date);
        TextView tripEndDate = findViewById(R.id.trip_end_date);
        TextView tripDuration = findViewById(R.id.trip_duration);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String tripName = extras.getString("trip_name");
            String location = extras.getString("trip_location");
            long startDateMillis = extras.getLong("start_date");
            long endDateMillis = extras.getLong("end_date");

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
    }
}