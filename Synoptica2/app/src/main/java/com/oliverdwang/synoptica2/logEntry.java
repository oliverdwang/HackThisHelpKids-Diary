package com.oliverdwang.synoptica2;

public class logEntry {

    public static final String TABLE_NAME = "logsV1";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DAYOFWEEK = "dayOfWeek";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_PROMPT = "prompt";
    public static final String COLUMN_MOOD = "mood";
    public static final String COLUMN_URI = "uri";


    private int id;

    private String dayOfWeek;
    private int month;
    private int day;
    private int year;
    private String prompt;
    private int mood;
    /*
    0=horrible
    1=bad
    2=neutral
    3=good
    4=great
     */
    private String uri;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_DAYOFWEEK + " TEXT,"
                    + COLUMN_MONTH + " INTEGER,"
                    + COLUMN_DAY + " INTEGER,"
                    + COLUMN_YEAR + " INTEGER,"
                    + COLUMN_PROMPT + " TEXT,"
                    + COLUMN_MOOD + " TEXT,"
                    + COLUMN_URI + " TEXT"
                    + ")";

    public logEntry() {
        id = -1;
        dayOfWeek="";
        month=-1;
        day=-1;
        year=-1;
        prompt="";
        mood=-1;
        uri="";
    }

    public String toString() {
        return "Id=" + id + " dayOfWeek=" + dayOfWeek + " month=" + month + " day=" + day + " year=" + year + " prompt=" + prompt + " mood=" + mood + " uri=" + uri;
    }

    public logEntry(int id, String dayOfWeek, int month, int day, int year, String prompt, int mood, String uri) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.month = month;
        this.day = day;
        this.year = year;
        this.prompt = prompt;
        this.mood = mood;
        this.uri = uri;
    }

    public logEntry(String dayOfWeek, int month, int day, int year, String prompt, int mood, String uri) {
        this.dayOfWeek = dayOfWeek;
        this.month = month;
        this.day = day;
        this.year = year;
        this.prompt = prompt;
        this.mood = mood;
        this.uri = uri;
    }

    public int getId() {
        return id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    public String getPrompt() {
        return prompt;
    }

    public int getMood() {
        return mood;
    }

    public String getUri() {
        return uri;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
