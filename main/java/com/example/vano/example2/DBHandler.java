package com.example.vano.example2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Stations.db";
    public static final String TABLE_NAME = "Stations";
    public static final String COLUMN_ID = "StationID";
    public static final String COLUMN_NAME = "StationName";
    public String dbAnswer;
    SQLiteDatabase myDB;

    public DBHandler(Context context){//}, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        myDB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null,null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL();
        //db.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
    public String loadHandler(){
        dbAnswer = myDB.getPath();
        return dbAnswer;
    }
}
