package com.siaans.skillindia;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OtpTCActivity extends AppCompatActivity {

    private static EditText otp;
    private static TextView submit;
    private static Animation shake;
    private static LinearLayout forget;
    String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_tc);
        //intizaling the variables
        otp = findViewById(R.id.otp);
        submit = findViewById(R.id.otp_submit);
        shake = AnimationUtils.loadAnimation(this,R.anim.shake);
        forget = findViewById(R.id.otp_Layout);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitButtonTask();
            }
        });
    }


    private void submitButtonTask() {
            Intent i=getIntent();
            Bundle bun=i.getExtras();

            OtpTCActivity.BackgroundTask b = new BackgroundTask();
            b.execute(bun.getString("Email"),bun.getString("flag"));
            Toast.makeText(this,res,Toast.LENGTH_LONG);
    }

    class BackgroundTask extends AsyncTask<String,Void,String> {

        String add_info_url;

        @Override
        protected void onPreExecute() {
            add_info_url = "http://159.65.144.10/miniproject/otp.php";

        }

        @Override
        protected String doInBackground(String... args) {
            try {
                URL url = new URL(add_info_url + "?email=" + args[0] + "&otp=" + otp.getText().toString()+"&flag="+args[1]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int responseCode = conn.getResponseCode();
                StringBuffer response;
                System.out.println("responseCode" + responseCode);
                switch (responseCode) {
                    case 200:
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;

                        response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        Log.d("s", "doInBackground: "+response.toString());
                        if(response.toString().equals(" Verified! Set Your New Password")) {
                            Intent i = new Intent(OtpTCActivity.this, NewPassActivity.class);
                            Bundle b = new Bundle();
                            b.putString("Email", args[0]);
                            b.putString("flag", args[1]);
                            i.putExtras(b);
                            startActivity(i);
                            finish();
                        }
                        res=response.toString();
                        in.close();
                        return response.toString();
                }
            }catch (Exception e) {
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