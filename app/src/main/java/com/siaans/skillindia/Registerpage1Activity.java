package com.siaans.skillindia;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;


public class Registerpage1Activity extends AppCompatActivity implements LocationListener {
    String name,username,password,email,mobile,profile;
    Bitmap profilePic;
    Button getLocationBtn;
    TextView locationText;
    Spinner state,ci;
    LocationManager locationManager;
    Button register;
    //RequestQueue requestQueue;
    String HttpUrl = "http://159.65.144.10/miniproject/insertion.php";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage1);
        getLocationBtn = (Button)findViewById(R.id.getLocationBtn);
        locationText = (TextView)findViewById(R.id.locationText);
        state = findViewById(R.id.state);
        ci=findViewById(R.id.spinner2);
        register=findViewById(R.id.Register);

//        requestQueue = Volley.newRequestQueue(Registerpage1Activity.this);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }


        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

    }
    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            locationText.setText(locationText.getText() + "\n"+addresses.get(0).getLocality()+", "+
                    addresses.get(0).getAdminArea()+", "+addresses.get(0).getSubLocality()+", "+addresses.get(0).getPostalCode()+", "+addresses.get(0).getCountryName());

            String[] State=getResources().getStringArray(R.array.State_items);
            Log.d(";", "onLocationChanged: "+State.length);
            for (int i=0;i<State.length;i++) {
                if(State[i].equals(addresses.get(0).getAdminArea())){
                        state.setSelection(i);
                        int city= this.getResources().getIdentifier(addresses.get(0).getAdminArea()+"_items", "array", this.getPackageName());
                    Log.d("kj", "onLocationChanged: "+city);

                    String[] cit=getResources().getStringArray(city);
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,cit); //selected item will look like a spinner set from XML
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    ci.setAdapter(spinnerArrayAdapter);
                }
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(Registerpage1Activity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }


}