package com.li.tritonia.wildlife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class AddTaskActivity extends ActionBarActivity {

    SharedPreferences prefs;

    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds

    protected LocationManager locationManager;
    protected Button retrieveLocationButton;
    private TextView latText;
    private TextView lonText;
    private EditText tagName;
    private EditText descriptionText;
    private int idCount;

    ArrayList<Task> taskList;

    Task newTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        retrieveLocationButton = (Button) findViewById(R.id.readyButton);
        latText = (TextView)findViewById(R.id.latitudeTextView);
        lonText = (TextView)findViewById(R.id.longitudeTextView);
        tagName = (EditText)findViewById(R.id.tagNameEditView);
        descriptionText = (EditText)findViewById(R.id.descriptionEditView);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        prefs = this.getSharedPreferences("com.li.tritonia.wildlife", Context.MODE_PRIVATE);
        idCount = prefs.getInt("idCount", 0);




        //GPS
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener()
        );

        retrieveLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                final Intent changeActivityWriteNFC = new Intent(AddTaskActivity.this, WriteNFCActivity.class);

                if(location != null){
                    //taskList.add(new Task(idCount, tagName.getText().toString(), descriptionText.getText().toString(), location.getLatitude(), location.getLongitude()));
                    newTask = new Task(idCount, tagName.getText().toString(), descriptionText.getText().toString(), location.getLatitude(), location.getLongitude());
                    //newTask = new Task(3, "Owl", "sNow", 1.4, 1.5);
                    taskList.add(newTask);
                    idCount++;
                    changeActivityWriteNFC.putParcelableArrayListExtra("list", taskList);
                    changeActivityWriteNFC.putExtra("nfcValue", tagName.getText().toString());
                    startActivity(changeActivityWriteNFC);
                }


            }
        });

    }

    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            String message = String.format(
                    "New Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
        }

        public void onStatusChanged(String s, int i, Bundle b) {
        }

        public void onProviderDisabled(String s) {
        }

        public void onProviderEnabled(String s) {
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        prefs.edit().putInt("idCount", idCount).apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
