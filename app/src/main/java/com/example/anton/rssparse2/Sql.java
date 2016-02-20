package com.example.anton.rssparse2;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Anton on 18.02.2016.
 */
public class Sql extends SQLiteOpenHelper implements BaseColumns {
//
    private static final String DATABASE_NAME = "rss.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE = "news";
    public static final String TITLE_COLUMN = "title";
    public static final String DATE_COLUMN = "date";
    public static final String IMAGE_COLUMN = "image";
    public static final String DESC_COLUMN = "description";
    public static final String LINK_COLUMN = "link";
    private static final String DATABASE_CREATE= "create table "
            + DATABASE_TABLE + " (" + BaseColumns._ID
            + " integer primary key autoincrement, " + TITLE_COLUMN
            + " text not null, " + DATE_COLUMN + " text not null unique, " + IMAGE_COLUMN
            + " text not null, " + DESC_COLUMN + " text not null, " + LINK_COLUMN
            + " text not null);";



    public Sql(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public Sql(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
