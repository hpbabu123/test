package com.siaans.skillindia;

import android.content.Context;
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
import android.widget.ProgressBar;
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
    ProgressBar send;
    String email,flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_tc);
        //intizaling the variables
        otp = findViewById(R.id.otp);
        submit = findViewById(R.id.otp_submit);
        send=findViewById(R.id.submiting);
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
        send.setVisibility(View.VISIBLE);
        send.setIndeterminate(true);
        submit.setVisibility(View.INVISIBLE);
        submit.setEnabled(false);
            Intent i=getIntent();
            Bundle bun=i.getExtras();
            email=bun.getString("Email");
            flag=bun.getString("flag");
            if(otp.getText().toString().length()==4) {
                OtpTCActivity.BackgroundTask b = new BackgroundTask(this);
                b.execute(bun.getString("Email"), bun.getString("flag"));
            }else{
                Toast.makeText(this,"Invalid OTP",Toast.LENGTH_SHORT).show();
        }
    }

    class BackgroundTask extends AsyncTask<String,Void,String> {
        Context ctx;
        String add_info_url;

        BackgroundTask(Context ctx){
            this.ctx=ctx;
        }

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
            if(res.toString().equals(" Verified! Set Your New Password")) {
                Toast.makeText(ctx," Verified! Set Your New Password",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(OtpTCActivity.this, NewPassActivity.class);
                Bundle b = new Bundle();
                b.putString("Email", email);
                b.putString("flag", flag);
                i.putExtras(b);
                startActivity(i);
                finish();
            }
            else{
                send.setVisibility(View.INVISIBLE);
                send.setIndeterminate(false);
                submit.setVisibility(View.VISIBLE);
                submit.setEnabled(true);
                Toast.makeText(ctx,"Invalid OTP",Toast.LENGTH_SHORT).show();

            }

        }
    }
}