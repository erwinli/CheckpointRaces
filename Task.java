package com.li.tritonia.wildlife;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tritonia on 2015-03-16.
 */
public class Task implements Parcelable{

    private int taskID;
    private String taskName;
    private String taskDescription;
    private double gpsLat;
    private double gpsLong;

    public Task(){}

    public Task(int id, String name, String description, double lat, double lon) {
        taskID = id;
        taskName = name;
        taskDescription = description;
        gpsLat = lat;
        gpsLong = lon;

    }

    public Task(Parcel dest){
        taskID = dest.readInt();
        taskName = dest.readString();
        taskDescription = dest.readString();
        gpsLat = dest.readDouble();
        gpsLong = dest.readDouble();

    }

    //Setter and getters
    public double getGpsLong() {
        return gpsLong;
    }

    public void setGpsLong(double gpsLong) {
        this.gpsLong = gpsLong;
    }

    public double getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(double gpsLat) {
        this.gpsLat = gpsLat;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getTaskname() {
        return taskName;
    }

    public void setTaskname(String taskname) {
        this.taskName = taskname;
    }

    public String getTaskdescription() {
        return taskDescription;
    }

    public void setTaskdescription(String taskdescription) {
        this.taskDescription = taskdescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(taskID);
        dest.writeString(taskName);
        dest.writeString(taskDescription);
        dest.writeDouble(gpsLat);
        dest.writeDouble(gpsLong);

    }

    public static final Parcelable.Creator<Task> CREATOR
            = new Parcelable.Creator<Task>() {
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
