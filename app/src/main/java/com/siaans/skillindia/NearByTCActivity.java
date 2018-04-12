package com.siaans.skillindia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NearByTCActivity extends AppCompatActivity {
    String JSON_URL="http://159.65.144.10/miniproject/tc.php";
    ListView listView;
    List<TCenter> heroList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_tc);
        Intent i=getIntent();
        Bundle b= i.getExtras();
        JSON_URL=JSON_URL+"?city="+b.getString("City")+"&state="+b.getString("State")+"&sector="+b.getString("Sector")+"&course='"+b.getString("Course")+"'";
        JSON_URL = JSON_URL.replaceAll(" ", "%20");
        Log.d("", "onCreate: "+JSON_URL);
        listView = (ListView) findViewById(R.id.TCItems);
        heroList = new ArrayList<>();
        call();

    }

    void call() {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        progressBar.setVisibility(View.INVISIBLE);


                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray heroArray = obj.getJSONArray("response_server");
                            if(heroArray.length()==0){
                                Toast.makeText(NearByTCActivity.this,"No TC Available Nearby",Toast.LENGTH_LONG);
                            }
                            else {
                                //now looping through all the elements of the json array
                                for (int i = 0; i < heroArray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject heroObject = heroArray.getJSONObject(i);

                                    //creating a hero object and giving them the values from json object
                                    TCenter hero = new TCenter(heroObject.getString("tp"),
                                            heroObject.getString("tcname"),
                                            heroObject.getString("tcspocname"),
                                            heroObject.getString("mobno"),
                                            heroObject.getString("emailid"),
                                            heroObject.getString("pincode"),
                                            heroObject.getString("address"),
                                            heroObject.getString("bid"),
                                            heroObject.getString("stime"),
                                            heroObject.getString("etime"),
                                            heroObject.getString("batchname"),
                                            heroObject.getString("totalseats"),
                                            heroObject.getString("filled"));

                                    //adding the hero to herolist
                                    heroList.add(hero);
                                }

                                //creating custom adapter object
                                ListViewAdapter adapter = new ListViewAdapter(heroList, getApplicationContext());

                                //adding the adapter to listview
                                listView.setAdapter(adapter);//
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.INVISIBLE);
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

//adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}