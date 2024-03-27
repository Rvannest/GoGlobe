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

    public DataBaseHelper(@Nullable Context context) {
        super(context, "goglobe.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_USERNAME + " TEXT, " + COLUMN_USER_PASSWORD + " TEXT)";
        db.execSQL(createTableStatement);
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

}