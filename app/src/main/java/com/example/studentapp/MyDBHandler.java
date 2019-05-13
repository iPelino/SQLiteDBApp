package com.example.studentapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by iPelino
 */

public class MyDBHandler extends SQLiteOpenHelper {

    // database objects: defining database structure
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "studentDB.db";
    public static final String TABLE_NAME = "Student";
    public static final String COLUMN_ID = "StudentId";
    public static final String COLUMN_NAME = "StudentName";

    // helper constructor
    public MyDBHandler( Context context,  String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called
    @Override
    public void onCreate(SQLiteDatabase db) {
        //string to create the database table
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                "("+ COLUMN_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_NAME + " TEXT NOT NULL );";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //to be used in case database is upgraded
    }

    public String loadData() {
        /*
        * retrieves data from sqlite database  and returns cursor(array) of results
        * */
        String result = "";
        String query = "Select * FROM " + TABLE_NAME; // select query
        SQLiteDatabase db = this.getWritableDatabase(); // initialize database
        Cursor cursor = db.rawQuery(query, null); // assign query result in cursor
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += result_0 + " " + result_1 + System.getProperty("line.separator");
        }
        cursor.close(); // close cursor
        db.close(); // close db connection
        return result;
    }


    /*
    * insert new record in database
    * student object from Student class
    * */
    public void addNewRecord(Student student) {
        ContentValues studentValues = new ContentValues();
        studentValues.put(COLUMN_ID,student.getmStudentId());
        studentValues.put(COLUMN_NAME,student.getmStudentName());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME,null,studentValues);
        db.close();
    }
    public Student findRecord(String studentname) {
        String query = "Select * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_NAME + " = " + "'"+ studentname + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Student student = new Student();
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            student.setmStudentId(Integer.parseInt(cursor.getString(0)));
            student.setmStudentName(cursor.getString(1));
            cursor.close();
        }else{
            student = null;
        }
        db.close();
        return student;

    }
    public boolean deleteRecord(int id) {
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "= '" + id + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Student student = new Student();
        if (cursor.moveToFirst()){
            student.setmStudentId(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_NAME, COLUMN_ID + "=?",
                    new String[]{
                            String.valueOf(student.getmStudentId())
                    });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    public boolean updateRecord(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_ID, id);
        args.put(COLUMN_NAME, name);
        return db.update(TABLE_NAME, args, COLUMN_ID + "=" + id, null) > 0;
    }

}
