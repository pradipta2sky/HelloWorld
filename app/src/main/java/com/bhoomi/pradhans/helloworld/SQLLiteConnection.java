package com.bhoomi.pradhans.helloworld;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rinky on 15-10-2017.
 */

public class SQLLiteConnection extends SQLiteOpenHelper {

    private String createTableQuery = "CREATE TABLE IF NOT EXISTS bhutatra(id integer PRIMARY KEY AUTOINCREMENT,"+
            " deviceID text not null, "+
            "geo_points text not null, name text not null,area real)";
    private String dropTableQuery = "DROP TABLE IF EXISTS bhutatr";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ibhoomi.db";

    public SQLLiteConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropTableQuery);
        onCreate(db);
    }
}
