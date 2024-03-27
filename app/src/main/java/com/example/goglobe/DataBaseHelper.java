package com.example.goglobe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USER_USERNAME = "USER_USERNAME";
    public static final String COLUMN_USER_PASSWORD = "USER_PASSWORD";
    public static final String COLUMN_ID = "ID";
    public static final String TRIP_TABLE = "TRIP_TABLE";
    public static final String COLUMN_TRIP_ID = "TRIP_ID";
    public static final String COLUMN_TRIP_USER_ID = "USER_ID";
    public static final String COLUMN_TRIP_NAME = "NAME";
    public static final String COLUMN_TRIP_DESTINATION = "DESTINATION";
    public static final String COLUMN_TRIP_START_DATE = "START_DATE";
    public static final String COLUMN_TRIP_END_DATE = "END_DATE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "goglobe.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_USERNAME + " TEXT, " + COLUMN_USER_PASSWORD + " TEXT)";
        db.execSQL(createTableStatement);

        String createTripTableStatement = "CREATE TABLE " + TRIP_TABLE + " (" + COLUMN_TRIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TRIP_USER_ID + " INTEGER, " + COLUMN_TRIP_NAME + " TEXT, " + COLUMN_TRIP_DESTINATION + " TEXT, " + COLUMN_TRIP_START_DATE + " TEXT, " + COLUMN_TRIP_END_DATE + " TEXT, FOREIGN KEY(" + COLUMN_TRIP_USER_ID + ") REFERENCES " + USER_TABLE + "(" + COLUMN_ID + "))";
        db.execSQL(createTripTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    //Please add you methods below for the database
    public boolean searchForUser(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] {COLUMN_ID,COLUMN_USER_USERNAME} ;
        String selection = COLUMN_USER_USERNAME + " = ? " ;
        String[] selectionArgs = new String[] {username} ;
        Cursor cursor = db.query(USER_TABLE,columns,selection, selectionArgs,null, null,null);
        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean addUser(String username , String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_USERNAME, username);
        cv.put(COLUMN_USER_PASSWORD, password);
        long test_addition = db.insert(USER_TABLE, null, cv);
        if(test_addition == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean validLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_USER_USERNAME + " = ? AND " + COLUMN_USER_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        boolean isValid = cursor.moveToFirst();
        cursor.close();
        db.close();
        return isValid;
    }

    public boolean addTrip(int userId, String name, String destination, String startDate, String endDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TRIP_USER_ID, userId);
        cv.put(COLUMN_TRIP_NAME, name);
        cv.put(COLUMN_TRIP_DESTINATION, destination);
        cv.put(COLUMN_TRIP_START_DATE, startDate); // Store date as string
        cv.put(COLUMN_TRIP_END_DATE, endDate); // Store date as string

        long insert = db.insert(TRIP_TABLE, null, cv);
        db.close();
        return insert != -1;
    }

    public int getUserId(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_ID + " FROM " + USER_TABLE + " WHERE " + COLUMN_USER_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        int id = -1; // ID not found
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0); // Get ID
        }
        cursor.close();
        db.close();
        return id;
    }

}
