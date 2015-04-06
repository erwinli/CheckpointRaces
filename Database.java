package com.li.tritonia.wildlife;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

/**
 * Created by Tritonia on 2015-03-31.
 */
public class Database {

    //Database info
    public static final String DATABASE_FILENAME = "tasks.db";
    public static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase db;
    public static SQLiteOpenHelper dbHelper;

    //Database table names
    public static final String TABLE_TASK = "Tasks";
    public static final String TABLE_HUNT = "Hunts";

    //Tasks table field names
    public static final String _id = "id";
    public static final String _taskId = "taskId";
    public static final String _huntId = "huntId";
    public static final String _taskName = "taskName";
    public static final String _taskDesc = "taskDesc";
    public static final String _gpsLat = "gpsLat";
    public static final String _gpsLon = "gpsLon";

    //Hunts table field names
    public static final String _huntName = "huntName";
    public static final String _huntDesc = "huntDesc";

    //Create table SQL statement
    String CREATE_STATEMENT_TASK = "CREATE TABLE " + TABLE_TASK + "("
            + _id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + _taskId + " INTEGER NOT NULL, "
            + _huntId + " INTEGER NOT NULL, "
            + _taskName + " TEXT NOT NULL, "
            + _taskDesc + " TEXT NOT NULL, "
            + _gpsLat + " REAL NOT NULL, "
            + _gpsLon + " REAL NOT NULL )";

    String CREATE_STATEMENT_HUNT = "CREATE TABLE " + TABLE_HUNT + "("
            + _id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + _huntId + " INTEGER NOT NULL, "
            + _huntName + " TEXT NOT NULL, "
            + _huntDesc + " TEXT NOT NULL )";

    //Drop table SQL statement
    public static final String DROP_STATEMENT = "DROP TABLE IF EXISTS " + TABLE_TASK;
    public static final String DROP_STATEMENT_HUNT = "DROP TABLE " + TABLE_HUNT;


    //Debugging
    public static final String LOGTAG = "DB_TAG";

    //constructor
    public Database(Context context){
        dbHelper = new DBOpenHelper(context);

    }

    //open database
    public void open(){
        db = dbHelper.getWritableDatabase();
    }

    //close database
    public void close(){
        db.close();
    }

    //return task name depending on task and hunt ID
    public String getTaskName(int task, int hunt){

        String taskName = "";

        String whereClause = _taskId + " = '" + Integer.toString(task) + "' and " + _huntId + " = '" + Integer.toString(hunt) + "'";
        open();
        Cursor c = db.query(
                TABLE_TASK,
                null,
                whereClause,
                null,
                null,
                null,
                null);

        c.moveToFirst();
        Log.d(LOGTAG, String.valueOf(c.getColumnIndex(_taskName)));
        taskName = c.getString(c.getColumnIndex(_taskName));
        close();
        return taskName;
    }

    //return task description depending on task and hunt ID
    public String getTaskDesc(int task, int hunt){

        String taskDesc = "";

        String whereClause = _taskId + " = '" + Integer.toString(task) + "' and " + _huntId + " = '" + Integer.toString(hunt) + "'";

        open();
        Cursor c = db.query(
                TABLE_TASK,
                null,
                whereClause,
                null,
                null,
                null,
                null);
        c.moveToFirst();
        taskDesc = c.getString(c.getColumnIndex(_taskDesc));

        close();

        return taskDesc;
    }

    //return GPS Latitude depending on task and hunt ID
    public double getGPSLat(int task, int hunt){

        String gpsLat = "";
        double output;
        String whereClause = _taskId + " = '" + Integer.toString(task) + "' and " + _huntId + " = '" + Integer.toString(hunt) + "'";

        open();
        Cursor c = db.query(
                TABLE_TASK,
                null,
                whereClause,
                null,
                null,
                null,
                null);
        c.moveToFirst();
        gpsLat = c.getString(c.getColumnIndex(_gpsLat));
        close();

        output = Double.parseDouble(gpsLat);
        return output;
    }

    //return GPS longitude depending on task and hunt ID
    public double getGPSLon(int task, int hunt){

        String gpsLon = "";
        double output;

        String whereClause = _taskId + " = '" + Integer.toString(task) + "' and " + _huntId + " = '" + Integer.toString(hunt) + "'";

        open();
        Cursor c = db.query(
                TABLE_TASK,
                null,
                whereClause,
                null,
                null,
                null,
                null);
        c.moveToFirst();
        gpsLon = c.getString(c.getColumnIndex(_gpsLon));
        close();

        output = Double.parseDouble(gpsLon);

        return output;
    }

    //return total number of task
    public int numberOfTasks(int huntID){
        int count = 0;
        String hunt = Integer.toString(huntID);

        open();
        final SQLiteStatement stmt = db
                .compileStatement("SELECT MAX(taskId) FROM Tasks WHERE huntId = " + hunt);
        count = (int) stmt.simpleQueryForLong();

        return count;

    }

    //insert a new task row
    public boolean insertTaskData(int taskID, int huntID, String taskName, String taskDesc, double gpsLat, double gpsLon) {

        boolean loadSuccessful = false;

        ContentValues values = new ContentValues();

        values.put(_taskId, taskID);
        values.put(_huntId, huntID);
        values.put(_taskName, taskName);
        values.put(_taskDesc, taskDesc);
        values.put(_gpsLat, gpsLat);
        values.put(_gpsLon, gpsLon);

        open();
        loadSuccessful = db.insert(TABLE_TASK, null, values) > 0;
        close();

        return loadSuccessful;
    }

    //updates an existing task
    public void updateTaskData(int taskID, int huntID, String taskName, String taskDesc, double gpsLat, double gpsLon){

        ContentValues values = new ContentValues();

        values.put(_taskId, taskID);
        values.put(_huntId, huntID);
        values.put(_taskName, taskName);
        values.put(_taskDesc, taskDesc);
        values.put(_gpsLat, gpsLat);
        values.put(_gpsLon, gpsLon);

        open();
        db.update(TABLE_TASK, values, _taskId + " = '" + taskID + "' AND " + _huntId + " = '" + huntID + "'", null);
        close();

    }
    //Return hunt id depending on hunt ID
    public String getHuntId(int hunt){

        String huntName = "";

        String whereClause = _huntId + " = '" + Integer.toString(hunt) + "'";

        open();
        Cursor c = db.query(
                TABLE_HUNT,
                null,
                whereClause,
                null,
                null,
                null,
                null);
        c.moveToFirst();
        huntName = c.getString(c.getColumnIndex(_huntId));
        close();

        return huntName;
    }

    //Return hunt name depending on hunt ID
    public String getHuntName(int hunt){

        String huntName = "";

        String whereClause = _huntId + " = '" + Integer.toString(hunt) + "'";

        open();
        Cursor c = db.query(
                TABLE_HUNT,
                null,
                whereClause,
                null,
                null,
                null,
                null);
        c.moveToFirst();
        huntName = c.getString(c.getColumnIndex(_huntName));
        close();

        return huntName;
    }

    //Return hunt description depending on hunt ID
    public String getHuntDesc(int hunt){

        String huntDesc = "";

        String whereClause = _huntId + " = '" + Integer.toString(hunt) + "'";

        open();
        Cursor c = db.query(
                TABLE_HUNT,
                null,
                whereClause,
                null,
                null,
                null,
                null);
        c.moveToFirst();
        huntDesc = c.getString(c.getColumnIndex(_huntDesc));
        close();

        return huntDesc;
    }

    //insert a new hunt row
    public boolean insertHuntData(int huntID, String huntName, String huntDesc) {

        boolean loadSuccessful = false;

        ContentValues values = new ContentValues();

        values.put(_huntId, huntID);
        values.put(_huntName, huntName);
        values.put(_huntDesc, huntDesc);

        open();
        loadSuccessful = db.insert(TABLE_HUNT, null, values) > 0;
        close();

        return loadSuccessful;
    }

    //return total number of hunts
    public int numberOfHunts(){
        int count = 0;

        open();
        final SQLiteStatement stmt = db
                .compileStatement("SELECT MAX(huntId) FROM Hunts");
        count = (int) stmt.simpleQueryForLong();

        return count;

    }

    //helper class
    public class DBOpenHelper extends SQLiteOpenHelper {

        public DBOpenHelper(Context context) {
            super(context, DATABASE_FILENAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_STATEMENT_TASK);
            db.execSQL(CREATE_STATEMENT_HUNT);
            Log.i(LOGTAG, "onCreate: Table has been created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {

            db.execSQL(DROP_STATEMENT);
            db.execSQL(DROP_STATEMENT_HUNT);
            db.execSQL(CREATE_STATEMENT_TASK);
            db.execSQL(CREATE_STATEMENT_HUNT);
            Log.i(LOGTAG, "onUpgrade: Table has been updated");
        }


    }
}
