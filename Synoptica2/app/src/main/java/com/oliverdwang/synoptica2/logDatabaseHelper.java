package com.oliverdwang.synoptica2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class logDatabaseHelper extends SQLiteOpenHelper {

    private static logDatabaseHelper sInstance;

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "logEntry_db";

    public static synchronized logDatabaseHelper getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new logDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public logDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(logEntry.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + logEntry.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertLog(String dayOfWeek, int month, int day, int year, String prompt, int mood, String uri) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` will be inserted automatically.
        values.put(logEntry.COLUMN_DAYOFWEEK, dayOfWeek);
        values.put(logEntry.COLUMN_MONTH, month);
        values.put(logEntry.COLUMN_DAY, day);
        values.put(logEntry.COLUMN_YEAR, year);
        values.put(logEntry.COLUMN_PROMPT, prompt);
        values.put(logEntry.COLUMN_MOOD, mood);
        values.put(logEntry.COLUMN_URI, uri);

        // insert row
        long id = db.insert(logEntry.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public logEntry getLog(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(logEntry.TABLE_NAME,
                new String[]{logEntry.COLUMN_ID, logEntry.COLUMN_DAYOFWEEK, logEntry.COLUMN_MONTH, logEntry.COLUMN_DAY, logEntry.COLUMN_YEAR, logEntry.COLUMN_PROMPT, logEntry.COLUMN_MOOD, logEntry.COLUMN_URI},
                logEntry.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        logEntry log = new logEntry();
        log.setId(cursor.getInt(cursor.getColumnIndex(log.COLUMN_ID)));
        log.setDayOfWeek(cursor.getString(cursor.getColumnIndex(log.COLUMN_DAYOFWEEK)));
        log.setMonth(cursor.getInt(cursor.getColumnIndex(log.COLUMN_MONTH)));
        log.setDay(cursor.getInt(cursor.getColumnIndex(log.COLUMN_DAY)));
        log.setYear(cursor.getInt(cursor.getColumnIndex(log.COLUMN_YEAR)));
        log.setPrompt(cursor.getString(cursor.getColumnIndex(log.COLUMN_PROMPT)));
        log.setMood(cursor.getInt(cursor.getColumnIndex(log.COLUMN_MOOD)));
        log.setUri(cursor.getString(cursor.getColumnIndex(log.COLUMN_URI)));

        // close the db connection
        cursor.close();

        return log;
    }

    public List<logEntry> getAllLogs() {
        List<logEntry> logs = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + logEntry.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                logEntry log = new logEntry();
                log.setId(cursor.getInt(cursor.getColumnIndex(logEntry.COLUMN_ID)));
                log.setDayOfWeek(cursor.getString(cursor.getColumnIndex(logEntry.COLUMN_DAYOFWEEK)));
                log.setMonth(cursor.getInt(cursor.getColumnIndex(logEntry.COLUMN_MONTH)));
                log.setDay(cursor.getInt(cursor.getColumnIndex(logEntry.COLUMN_DAY)));
                log.setYear(cursor.getInt(cursor.getColumnIndex(logEntry.COLUMN_YEAR)));
                log.setPrompt(cursor.getString(cursor.getColumnIndex(logEntry.COLUMN_PROMPT)));
                log.setMood(cursor.getInt(cursor.getColumnIndex(logEntry.COLUMN_MOOD)));
                log.setUri(cursor.getString(cursor.getColumnIndex(logEntry.COLUMN_URI)));

                logs.add(log);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return logs;
    }

    public int getLogCount() {
        String countQuery = "SELECT  * FROM " + logEntry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
}
