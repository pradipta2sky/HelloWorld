package com.bhoomi.pradhans.helloworld;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    double longitudeBest, latitudeBest;
    TextView longitudeValueBest, latitudeValueBest;
    TextView longitudeValueGPS, latitudeValueGPS;
    private ListView mainListView;
    private LatLongAdapter listAdapter;
    private ArrayList<LatLongHolder> planetList = new ArrayList<LatLongHolder>();
    private String serialNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mainListView = (ListView) findViewById(R.id.mainListView);

        listAdapter = new LatLongAdapter(this, planetList);
        mainListView.setAdapter(listAdapter);
        Button addButton = (Button) findViewById(R.id.button1);

        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            serialNumber = (String) (get.invoke(c, "ro.serialno", "unknown"));
        } catch (Exception ignored) {
        }
        checkLocation();

    }

    public void saveLocation(View view) {
        Toast.makeText(this, "Serial Number is " + serialNumber, Toast.LENGTH_LONG).show();
    }

    public void removeLastLocation(View view) {
        int count = listAdapter.getCount();
        if (count > 0) {
            listAdapter.remove(listAdapter.getItem(count));
        } else {
            Toast.makeText(this, "No Item to remove.", Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void toggleBestUpdates(View view) {
        if (!checkLocation())
            return;
        Button button = (Button) view;

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, true);
        if (provider != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(provider, 2 * 60 * 1000, 10, locationListenerBest);
            Toast.makeText(this, "Best Provider is " + provider, Toast.LENGTH_LONG).show();
        }
    }

    private final LocationListener locationListenerBest = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeBest = location.getLongitude();
            latitudeBest = location.getLatitude();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "Best Provider update", Toast.LENGTH_SHORT).show();
                    LatLongHolder latLongHolder = new LatLongHolder(latitudeBest + "", longitudeBest + "");
                    int count = listAdapter.getCount();
                    latLongHolder.setLocationNumber(count + 1);
                    listAdapter.add(latLongHolder);
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };


}