package com.siaans.skillindia;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.siaans.skillindia.activity.NavigationActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NewFindTCActivity extends AppCompatActivity implements LocationListener {
    Spinner citySpinner,stateSpinner,courseSpinner,sectorSpinner;
    ArrayList<String> stringArrayState ;
    ArrayList<String> stringArrayCity = new ArrayList<String>();
    ArrayList<String> stringArraySector ;
    ArrayList<String> stringArrayCourse = new ArrayList<String>();
    String spinnerStateValue, city,spinnerSectorValue , course;
    ArrayAdapter<String> adapterCity;
    ArrayAdapter<String> adapterCourse;
    Button find;
    LocationManager locationManager;
    Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_find);
        find = (Button)findViewById(R.id.find);
        inits();
        init();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });
        register=findViewById(R.id.findTC);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stateSpinner.getSelectedItem().toString().contains("...")){
                        Toast.makeText(NewFindTCActivity.this,"Please Select state first",Toast.LENGTH_LONG).show();
                }
               else if(citySpinner.getSelectedItem().toString().contains("...")){
                    Toast.makeText(NewFindTCActivity.this,"Please Select city first",Toast.LENGTH_LONG).show();
                }
               else if(sectorSpinner.getSelectedItem().toString().contains("...")){
                    Toast.makeText(NewFindTCActivity.this,"Please Select sector first",Toast.LENGTH_LONG).show();
                }
              else if(courseSpinner.getSelectedItem().toString().contains("...")){
                    Toast.makeText(NewFindTCActivity.this,"Please Select course first",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent i= new Intent(NewFindTCActivity.this, NearByTCActivity.class);
                  Bundle b=new Bundle();
                  b.putString("State",stateSpinner.getSelectedItem().toString());
                    b.putString("City",  citySpinner.getSelectedItem().toString());
                    b.putString("Sector", sectorSpinner.getSelectedItem().toString());
                    b.putString("Course",  courseSpinner.getSelectedItem().toString());
                    i.putExtras(b);
                    startActivity(i);

              }


            }
        });
    }
    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 5, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }
    void inits() {
        courseSpinner = (Spinner) findViewById(R.id.course);
        sectorSpinner = (Spinner) findViewById(R.id.Sector);
        stringArraySector = new ArrayList<String>();
        stringArrayCourse = new ArrayList<String>();
        adapterCourse = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, stringArrayCourse);
        courseSpinner.setAdapter(adapterCourse);
        try {
            JSONObject obj1 = new JSONObject(loadJSONFromAssetsector());
            JSONArray n_jArry = obj1.getJSONArray("sectorlist");

            for (int i = 0; i < n_jArry.length(); i++) {
                JSONObject jo_inside1 = n_jArry.getJSONObject(i);
                String sector = jo_inside1.getString("Sector");

                String id = jo_inside1.getString("id");


                stringArraySector.add(sector);
            }
            ArrayAdapter<String> adapterc = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, stringArraySector);
            sectorSpinner.setAdapter(adapterc);
        } catch (Exception e) {
            Log.d("", "init: " + e.toString());
            e.printStackTrace();
        }
        sectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  Log.d("xs", "init: ");
                Object item = parent.getItemAtPosition(position);
                // Log.d("xs", "init: ");
                String Text = sectorSpinner.getSelectedItem().toString();
                Log.d("xs", "init: ");


                spinnerSectorValue = String.valueOf(sectorSpinner.getSelectedItem());
                stringArrayCourse.clear();

                try {
                    JSONObject obj = new JSONObject(loadJSONFromAssetCourse());
                    JSONArray n_jArry = obj.getJSONArray("courselist");
                        stringArrayCourse.add("Select Course...");
                    for (int i = 0; i < n_jArry.length(); i++) {
                        JSONObject jo_inside = n_jArry.getJSONObject(i);
                        String sector = jo_inside.getString("Sector");
                        String courseid = jo_inside.getString("id");

                        if (spinnerSectorValue.equalsIgnoreCase(sector)) {
                            course = jo_inside.getString("course");
                            stringArrayCourse.add(course);
                        }

                    }

                    //notify adapter city for getting selected value according to state
                    adapterCourse.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spinnerCourseValue = String.valueOf(courseSpinner.getSelectedItem());
                Log.e("SpinnerCourseValue",spinnerCourseValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    void init() {
        citySpinner = (Spinner) findViewById(R.id.city);
        stateSpinner = (Spinner) findViewById(R.id.state);

        stringArrayState = new ArrayList<String>();
        stringArrayCity = new ArrayList<String>();

        //set city adapter
        adapterCity = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, stringArrayCity);
        citySpinner.setAdapter(adapterCity);

        //Get state json value from assets folder
        try {
            JSONObject obj = new JSONObject(loadJSONFromAssetState());
            JSONArray m_jArry = obj.getJSONArray("statelist");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String state = jo_inside.getString("State");
                String id = jo_inside.getString("id");
                stringArrayState.add(state);
            }
            Log.d("xs", "init: " + stringArrayState.size());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_layout, R.id.txt, stringArrayState);
            stateSpinner.setAdapter(adapter);
        } catch (Exception e) {
            Log.d("", "init: "+e.toString());
            e.printStackTrace();
        }




        //state spinner item selected listner with the help of this we get selected value

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("xs", "init: ");

                Object item = parent.getItemAtPosition(position);
                Log.d("xs", "init: ");

                String Text = stateSpinner.getSelectedItem().toString();
                Log.d("xs", "init: ");


                spinnerStateValue = String.valueOf(stateSpinner.getSelectedItem());
                stringArrayCity.clear();

                try {
                    JSONObject obj = new JSONObject(loadJSONFromAssetCity());
                    JSONArray m_jArry = obj.getJSONArray("citylist");
                    stringArrayCity.add("Select City...");
                    for (int i = 0; i < m_jArry.length(); i++) {
                        JSONObject jo_inside = m_jArry.getJSONObject(i);
                        String state = jo_inside.getString("State");
                        String cityid = jo_inside.getString("id");

                        if (spinnerStateValue.equalsIgnoreCase(state)) {
                            city = jo_inside.getString("city");
                            stringArrayCity.add(city);
                        }

                    }

                    //notify adapter city for getting selected value according to state
                    adapterCity.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });






        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spinnerCityValue = String.valueOf(citySpinner.getSelectedItem());
                Log.e("SpinnerCityValue",spinnerCityValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    public String loadJSONFromAssetState() {
        String json = null;
        try {
            InputStream is = getAssets().open("state.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public String loadJSONFromAssetCity() {
        String json = null;
        try {
            InputStream is = getAssets().open("cityState.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public String loadJSONFromAssetsector() {
        String json = null;
        try {
            InputStream is = getAssets().open("Sector.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public String loadJSONFromAssetCourse() {
        String json = null;
        try {
            InputStream is = getAssets().open("Course.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String[] State= stringArrayState.toArray(new String[stringArrayState.size()]);
            Log.d(";", "onLocationChanged: "+State.length);
            for (int i=0;i<State.length;i++) {
                Log.d("kj"+State[i],"lkjhg"+addresses.get(0).getAdminArea());
                if(State[i].equals(addresses.get(0).getAdminArea())){
                    stateSpinner.setSelection(i);
                    break;
//                    int city= this.getResources().getIdentifier(addresses.get(0).getAdminArea()+"_items", "array", this.getPackageName());
//                    Log.d("kj", "onLocationChanged: "+city);
//
//                    String[] cit=getResources().getStringArray(city);
//                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,cit); //selected item will look like a spinner set from XML
//                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//                    citySpinner.setAdapter(spinnerArrayAdapter);
                }
            }
            String[] city= stringArrayCity.toArray(new String[stringArrayCity.size()]);
            for (int j=0;j<city.length;j++) {
                Log.d(""+city[j],""+addresses.get(0).getLocality());
                if(city[j].equals(addresses.get(0).getLocality())){
                    citySpinner.setSelection(j);
                    //    break;
                }
            }


        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(NewFindTCActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
