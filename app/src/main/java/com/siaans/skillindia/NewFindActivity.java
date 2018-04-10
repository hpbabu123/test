package com.siaans.skillindia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class NewFindActivity extends AppCompatActivity {

    String india[]={"Maharashtra","Bihar","Uttar Pradesh"};
    String m[]={"PUNE","Mumbai","Solapur"};
    String b[]={"PATNA","MUZAFFARPUR","GAYA"};
    String u[]={"LUCKNOW","KANPUR","FAIZABAD"};

    Spinner s,s1,s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_find);

        s=(Spinner)findViewById(R.id.state);
        s1=(Spinner)findViewById(R.id.city);


        final ArrayAdapter<String> ia=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,india);

        final ArrayAdapter<String>ma=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);

        final ArrayAdapter<String>ba=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,b);

        final ArrayAdapter<String>ua=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,u);

        ia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(ia);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (parent.getId()) {
                    case R.id.state: {
                        if (india[position].equals("Maharashtra")) {

                            ma.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            s1.setAdapter(ma);

                        }
                        if (india[position].equals("Bihar")) {

                            ba.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            s1.setAdapter(ba);

                        }
                        if (india[position].equals("Uttar Pradesh")) {

                            ua.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            s1.setAdapter(ua);

                        }

                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}