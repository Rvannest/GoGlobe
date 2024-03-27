package com.example.goglobe;

import android.os.Bundle;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import android.widget.Toast;
import android.widget.Button;
import android.content.SharedPreferences;

public class CreateTripActivity extends AppCompatActivity {
    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private EditText editTextTripName;
    private EditText editTextDestination;
    private Button buttonSubmitTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_trip);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        editTextStartDate = findViewById(R.id.editTextStartDate);
        editTextEndDate = findViewById(R.id.editTextEndDate);
        editTextStartDate.setOnClickListener(view -> showDatePickerDialog(editTextStartDate));
        editTextEndDate.setOnClickListener(view -> showDatePickerDialog(editTextEndDate));
        editTextDestination = findViewById(R.id.editTextDestination);


        editTextTripName = findViewById(R.id.editTextTripName);
        buttonSubmitTrip = findViewById(R.id.buttonSubmitTrip);
        buttonSubmitTrip.setOnClickListener(view -> submitTrip());
    }


    private void showDatePickerDialog(final EditText dateField) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateTripActivity.this,
                (view, year1, monthOfYear, dayOfMonth) -> dateField.setText(String.format("%d-%02d-%02d", year1, monthOfYear + 1, dayOfMonth)), year, month, day);
        datePickerDialog.show();
    }

    private void submitTrip() {
        String tripName = editTextTripName.getText().toString();
        String destination = editTextDestination.getText().toString();
        String startDate = editTextStartDate.getText().toString();
        String endDate = editTextEndDate.getText().toString();

        if (tripName.isEmpty() || destination.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Retrieve user ID
        int userId = getUserIdFromSession();

        // Save trip data to the database
        DataBaseHelper dbHelper = new DataBaseHelper(CreateTripActivity.this);
        boolean success = dbHelper.addTrip(userId, tripName, destination, startDate, endDate);
        if (success) {
            Toast.makeText(this, "Trip saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save trip", Toast.LENGTH_SHORT).show();
        }
    }

    private int getUserIdFromSession() {
        SharedPreferences sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("userId", -1); // If ID not found
    }
}