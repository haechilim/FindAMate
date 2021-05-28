package com.example.findamate.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static java.sql.Types.INTEGER;

public class DataBaseStudentList extends SQLiteOpenHelper {
    public DataBaseStudentList(@Nullable Context context) {
        super(context, "studentList", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE studentList(studentId INTEGER PRIMARY KEY, studentName TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addStudent(int studentId, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO studentList VALUES ('" + studentId + "', '" + name + "');");
    }
}
