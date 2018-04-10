package com.siaans.skillindia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class NewFindTCActivity extends AppCompatActivity {
    Spinner citySpinner,stateSpinner;
    ArrayList<String> stringArrayState ;
    ArrayList<String> stringArrayCity = new ArrayList<String>();
    String spinnerStateValue, city;
    ArrayAdapter<String> adapterCity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_find);
        init();
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
}
