package com.siaans.skillindia;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    ProgressBar send;
    String res,flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass);
        //intizaling the variables
        npass = findViewById(R.id.npass);
        submit = findViewById(R.id.npass_submit);
        send=findViewById(R.id.submiting);
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
        Boolean pass=validatePassword(npass.getText().toString());
        flag=bun.getString("flag");
        if(pass) {
            NewPassActivity.BackgroundTask b = new NewPassActivity.BackgroundTask(this);
            b.execute(bun.getString("Email"), bun.getString("flag"));
        }
    }
    private boolean validatePassword(String string) {
        if (string.equals("")) {
            Toast.makeText(this,"Enter Your Password",Toast.LENGTH_SHORT).show();
            return false;
        } else if (string.length() > 32) {
            Toast.makeText(this,"Maximum 32 Characters",Toast.LENGTH_SHORT).show();
            return false;
        } else if (string.length() < 8) {
            Toast.makeText(this,"Minimum 8 Characters",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    class BackgroundTask extends AsyncTask<String,Void,String> {
        Context ctx;
        String add_info_url;

        BackgroundTask(Context ctx){
            this.ctx=ctx;
        }
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
            if(res.toString().equals("Updated")) {
                Toast.makeText(ctx,"Password updated",Toast.LENGTH_SHORT).show();
                if(flag.equals("1")) {
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
                Toast.makeText(ctx,"Not updated! Internal Server Error",Toast.LENGTH_SHORT).show();
                if(flag.equals("1")) {
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
        }
    }
}
