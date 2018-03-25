package com.siaans.skillindia;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TCForgotActivity extends AppCompatActivity {
    private static EditText emailId;
    private static TextView submit;
    private static Animation shake;
    private static LinearLayout forget;
    private static String flag;
    String res;
    ProgressBar send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcforgot);
        //intizaling the variables
        emailId = findViewById(R.id.registered_emailid);
        submit = findViewById(R.id.forgot_button);
        shake = AnimationUtils.loadAnimation(this,R.anim.shake);
        forget = findViewById(R.id.forget_Layout);
        send=findViewById(R.id.submiting);
        Intent i=getIntent();
        Bundle b=i.getExtras();
        flag=b.getString("flag");
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
        String getEmailId = emailId.getText().toString();
        // Pattern for email id validation
        Pattern p = Pattern.compile(Emailchecker.regEx);

        // Match the pattern
        Matcher m = p.matcher(getEmailId);

        // First check if email id is not null else show error toast
        if (getEmailId.equals("") || getEmailId.length() == 0){
            send.setVisibility(View.INVISIBLE);
            send.setIndeterminate(false);
            submit.setVisibility(View.VISIBLE);
            submit.setEnabled(true);
            forget.startAnimation(shake);
            Toast.makeText(this,"Enter the Username",Toast.LENGTH_SHORT).show();
        }
        else if (!m.find()) {
            send.setVisibility(View.INVISIBLE);
            send.setIndeterminate(false);
            submit.setVisibility(View.VISIBLE);
            submit.setEnabled(true);
            forget.startAnimation(shake);
            Toast.makeText(this, "Invalid EmailID", Toast.LENGTH_SHORT).show();
        }
        // Else submit email id and fetch passwod or do your stuff
        else{
            BackgroundTask b=new BackgroundTask(this);
             b.execute(emailId.getText().toString(),flag);

        }
    }
    class BackgroundTask extends AsyncTask<String,Void,String> {
        Context ctx;
        BackgroundTask(Context ctx){
            this.ctx=ctx;
        }
        String add_info_url;
        @Override
        protected void onPreExecute() {
            add_info_url ="http://159.65.144.10/miniproject/email.php";

        }

        @Override
        protected String doInBackground(String... args) {
                  try {
                    URL url = new URL(add_info_url + "?email=" + args[0] +"&flag="+args[1]);
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


                } catch (Exception e) {
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
            if(res.toString().equals("Check OTP on MAIL  in 2 min! check on spam.")) {
                Toast.makeText(ctx, "Check OTP on MAIL  in 2 min! check on spam.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(TCForgotActivity.this, OtpTCActivity.class);
                Bundle b = new Bundle();
                b.putString("Email",emailId.getText().toString());
                b.putString("flag",flag);
                i.putExtras(b);
                startActivity(i);
                finish();
            }
            else{
                //donwe
                send.setVisibility(View.INVISIBLE);
                send.setIndeterminate(false);
                submit.setVisibility(View.VISIBLE);
                submit.setEnabled(true);
                forget.startAnimation(shake);
                Toast.makeText(ctx,"Not A Valid ID",Toast.LENGTH_SHORT).show();

            }
        }
    }
}
