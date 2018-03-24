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
        Bundle bundle = getIntent().getExtras();
         name = bundle.getString("name");
         Log.d("name",name);
         username = bundle.getString("username");
         Log.d("username",username);
         password = bundle.getString("password");
         Log.d("password",password);
         email = bundle.getString("email");
         Log.d("email",email);
         mobile = bundle.getString("mobile");
         Log.d("mobile",mobile);
         profile = bundle.getString("profile");
         Log.d("profile","done"+profile);
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

                JSONObject params = new JSONObject();
                try{        // Adding All values to Params.
                        params.put("name", name);
                        params.put("password",password);
                        params.put("email", email);
                        params.put("mobile",mobile);
                        params.put("profile",profile);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("s", "onClick: cdxs");

                }
                String message = params.toString();
                Log.d("s", "onClick: "+message);
                BackgroundTask b=new BackgroundTask();
                b.execute(message);

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

    class BackgroundTask extends AsyncTask<String,Void,String> {

        String add_info_url;
        @Override
        protected void onPreExecute() {
            add_info_url ="http://159.65.144.10/miniproject/insertion.php";

        }

        @Override
        protected String doInBackground(String... args) {
            try{
                Log.d(";", "doInBackground: "+args[0]);
                URL url=new URL(add_info_url);
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                conn.setReadTimeout( 10000 /*milliseconds*/ );
                conn.setConnectTimeout( 15000 /* milliseconds */ );
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(args[0].getBytes().length);
                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
                //open
                conn.connect();
                //setup send
                OutputStream os = new BufferedOutputStream(conn.getOutputStream());
                os.write(args[0].getBytes());
                //clean up
                os.flush();
                Log.d("kjhg", "doInBackground: done");
//                OutputStream os=httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                int responseCode = conn.getResponseCode();
                StringBuffer response;
                System.out.println("responseCode" + responseCode);
                switch (responseCode) {
                   // case 200:
                     default:
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;

                        response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        Log.d("def", "doInBackground: "+response.toString());
                        return response.toString();
                }



            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String r) {

        }
    }
}