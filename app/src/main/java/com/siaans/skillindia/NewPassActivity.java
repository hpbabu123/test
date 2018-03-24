package com.siaans.skillindia;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewPassActivity extends AppCompatActivity {
    private static EditText npass;
    private static TextView submit;
    private static Animation shake;
    private static LinearLayout forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass);
        //intizaling the variables
        npass = findViewById(R.id.npass);
        submit = findViewById(R.id.npass_submit);
        shake = AnimationUtils.loadAnimation(this,R.anim.shake);
        forget = findViewById(R.id.newpass_Layout);
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
        NewPassActivity.BackgroundTask b = new NewPassActivity.BackgroundTask();
        b.execute(bun.getString("Email"),bun.getString("flag"));
    }

    class BackgroundTask extends AsyncTask<String,Void,String> {

        String add_info_url;

        @Override
        protected void onPreExecute() {
            add_info_url = "http://159.65.144.10/miniproject/update.php";

        }

        @Override
        protected String doInBackground(String... args) {
            try {
                URL url = new URL(add_info_url + "?email=" + args[0] + "&password=" + npass.getText().toString()+"&flag="+args[1]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int responseCode = conn.getResponseCode();
                StringBuffer response;
                System.out.println("responseCode" + responseCode);
                switch (responseCode) {
                    case 200:
                    default:
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;

                        response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        if(response.toString().equals("Updated")) {
                            if(args[1].equals("1")) {
                                Intent i = new Intent(NewPassActivity.this, TCloginActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else{
                                Intent i = new Intent(NewPassActivity.this, Trainee_Login_Activity.class);
                                startActivity(i);
                                finish();
                            }

                        }else{
                            if(args[1].equals("1")) {
                                Intent i = new Intent(NewPassActivity.this, TCloginActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else{
                                Intent i = new Intent(NewPassActivity.this, Trainee_Login_Activity.class);
                                startActivity(i);
                                finish();
                            }
                        }
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
