package com.example.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

public class MyOpener extends SQLiteOpenHelper {
    protected final static String DATABASE_NAME ="Lab_5DB";
    protected final static int VERSION_NUM = 1;
    public final static  String TABLE_NAME = "ToDoList";
    public final static String COL_ITEMS= "Item";
    public final static String COL_ID= "_ID";

    public final static String COL_URGENT = "Urgent";

    public MyOpener(Context ctx) {super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }


    @Override
    public void onCreate( SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " ( " +
                COL_ID + " INTEGER PRIMARY KEY," +
                COL_ITEMS + " TEXT," +
                COL_URGENT + " BOOLEAN)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);

    }
}
