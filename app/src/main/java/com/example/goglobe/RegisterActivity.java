package com.example.goglobe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    Button btn_register, btnBack;
    EditText et_username , et_password , et_password_confirm ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_register = findViewById(R.id.btn_register);
        btnBack = findViewById(R.id.btn_back_to_main);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_password_confirm = findViewById(R.id.et_password_confirm);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = et_password.getText().toString();
                String b = et_password_confirm.getText().toString();
                DataBaseHelper dbHelper = new DataBaseHelper(RegisterActivity.this ) ;
                if (!a.equals(b)){
                    //If the passwords do not match
                    AlertDialog.Builder adBuilder = new AlertDialog.Builder(RegisterActivity.this);
                    adBuilder.setMessage("Please retry").setTitle("Password Do Not Match");
                    AlertDialog notify = adBuilder.create();
                    notify.show();
                }
                else if (et_username.getText().toString().equals("")){
                    //If the username is empty
                    AlertDialog.Builder adBuilder = new AlertDialog.Builder(RegisterActivity.this);
                    adBuilder.setMessage("Please retry").setTitle("Username is Empty");
                    AlertDialog notify = adBuilder.create();
                    notify.show();
                } else if (dbHelper.searchForUser(et_username.getText().toString())) {
                    AlertDialog.Builder adBuilder = new AlertDialog.Builder(RegisterActivity.this);
                    adBuilder.setMessage("Please retry").setTitle("Username Already Exists");
                    AlertDialog notify = adBuilder.create();
                    notify.show();
                } else{
                    if(dbHelper.addUser(et_username.getText().toString(), et_password.getText().toString())){
                        //If this returns true we have succesfully added a user into the user table
                        AlertDialog.Builder adBuilder = new AlertDialog.Builder(RegisterActivity.this);
                        adBuilder.setMessage("Your account has successfully been created").setTitle("Welcome "+ et_username.getText().toString())
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                RegisterActivity.this.startActivity(intent);
                                            }
                                        });
                        AlertDialog notify = adBuilder.create();
                        notify.show();

                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to the MainActivity
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}