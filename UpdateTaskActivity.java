package com.li.tritonia.wildlife;

import android.content.Context;
import android.content.Intent;
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


public class UpdateTaskActivity extends ActionBarActivity {

    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds

    protected LocationManager locationManager;
    protected Button retrieveLocationButton;
    private TextView latText;
    private TextView lonText;
    private EditText tagName;
    private EditText descriptionText;
    private int taskIdVal, huntIdVal;
    private String taskName, taskDesc;
    private double gpsLat, gpsLon;

    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        //Variable initialization
        retrieveLocationButton = (Button) findViewById(R.id.readyButton);
        latText = (TextView)findViewById(R.id.latitudeTextView);
        lonText = (TextView)findViewById(R.id.longitudeTextView);
        tagName = (EditText)findViewById(R.id.tagNameEditView);
        descriptionText = (EditText)findViewById(R.id.descriptionEditView);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //GPS on
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        intent = getIntent();

        // Data from existing task if user wants to edit
        huntIdVal = intent.getIntExtra("huntIdValue", 99);
        taskIdVal = intent.getIntExtra("taskIdValue", 0);
        taskName = MainActivity.dataBase.getTaskName(taskIdVal, huntIdVal);
        taskDesc = MainActivity.dataBase.getTaskDesc(taskIdVal, huntIdVal);

        // Setting fields to existing data
        tagName.setText(taskName);
        descriptionText.setText(taskDesc);

        // pass task data to NFC write activity
        retrieveLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                final Intent changeActivityWriteNFC = new Intent(UpdateTaskActivity.this, UpdateNFCActivity.class);

                if(location != null){
                    changeActivityWriteNFC.putExtra("taskIdValue", taskIdVal);
                    changeActivityWriteNFC.putExtra("huntIdValue", huntIdVal);
                    changeActivityWriteNFC.putExtra("passTaskName", tagName.getText().toString());
                    changeActivityWriteNFC.putExtra("passTaskDesc", descriptionText.getText().toString());
                    changeActivityWriteNFC.putExtra("passGPSLat", location.getLatitude());
                    changeActivityWriteNFC.putExtra("passGPSLon", location.getLongitude());

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
