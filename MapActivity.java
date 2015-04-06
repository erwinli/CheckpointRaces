package com.li.tritonia.wildlife;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;


public class MapActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, OnMapReadyCallback {

    final int RQS_GooglePlayServices = 1;
    private String TAG = "GPSTEST";
    private TextView mLocationView;
    private GoogleApiClient mGoogleApiClient;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    double lat, lon;
    int taskIdVal, huntIdVal;
    double taskLat, taskLon;
    GoogleMap googleMap;
    Button foundTaskBtn, moveMapBtn;
    LocationRequest mLocationRequest;
    Location mCurrentLocation;
    Marker userMarker, taskMarker;
    Intent intent;
    Polyline polyline;
    PolylineOptions polylineOptions;
    MarkerOptions userMarkerOptions, taskMarkerOptions;
    List<LatLng> points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationView = new TextView(this);
        setContentView(R.layout.activity_map);

        // Location awareness API
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        MapFragment map = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);

        intent = getIntent();
        taskIdVal = intent.getIntExtra("taskIdValue", 0);
        huntIdVal = intent.getIntExtra("huntIdValue", 0);

        taskLat = MainActivity.dataBase.getGPSLat(taskIdVal, huntIdVal);
        taskLon = MainActivity.dataBase.getGPSLon(taskIdVal, huntIdVal);

        foundTaskBtn = (Button)findViewById(R.id.foundTaskButton);
        moveMapBtn = (Button)findViewById(R.id.moveMapToUserButton);

        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

    }


    @Override
    protected void onResume() {
        super.onResume();

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (resultCode == ConnectionResult.SUCCESS){
            Toast.makeText(getApplicationContext(), "Google Play services is available.",
                    Toast.LENGTH_LONG).show();
        } else {
            GooglePlayServicesUtil.getErrorDialog(resultCode, this, RQS_GooglePlayServices).show();
        }

        foundTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToTapActivity = new Intent(MapActivity.this, CheckpointTapActivity.class);
                moveToTapActivity.putExtra("taskIdValue", taskIdVal);
                moveToTapActivity.putExtra("huntIdValue", huntIdVal);

                startActivity(moveToTapActivity);
            }
        });

        moveMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToMe();
            }
        });

        userMarkerOptions = new MarkerOptions().position(new LatLng(50.674129, -120.334982)).title("It's me");
        taskMarkerOptions = new MarkerOptions().position(new LatLng(50.674129, -120.334982)).title("Find Me");

        polylineOptions = new PolylineOptions().add(new LatLng(50.674129, -120.334982), new LatLng(taskLat, taskLon)).width(5).color(Color.RED);
        polyline = googleMap.addPolyline(polylineOptions);
        userMarker = googleMap.addMarker(userMarkerOptions);
        taskMarker = googleMap.addMarker(taskMarkerOptions);
    }

    @Override
    protected void onStop() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();


        return super.onOptionsItemSelected(item);
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);

    }

    protected void moveToMe(){
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLocation.getLatitude()
                , mCurrentLocation.getLongitude()), 24));
    }

    @Override
    public void onConnected(Bundle bundle) {

        createLocationRequest();
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "GoogleApiClient connection has been suspend");
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();

        List<LatLng> points = polyline.getPoints();
        points.remove(1);
        points.add(new LatLng(lat, lon));
        polyline.setPoints(points);

        userMarker.setPosition(new LatLng(lat, lon));
        mCurrentLocation = location;
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) { // Log the error
                e.printStackTrace();
            }
        } else {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, CONNECTION_FAILURE_RESOLUTION_REQUEST).show();

        }
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        // Decide what to do based on the original request code
        switch (requestCode) {
            case CONNECTION_FAILURE_RESOLUTION_REQUEST :
            /*
            * If the result code is Activity.RESULT_OK, try * to connect again
            */
                switch (resultCode) {
                    case Activity.RESULT_OK : /*
            * Try the request again
            */
                        break;
                }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

}
