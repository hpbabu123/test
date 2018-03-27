package com.siaans.skillindia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private static EditText npass,cnfrmpass;
    private static CheckBox show_pass;
    private static TextView submit;
    private static Animation shake;
    private static LinearLayout forget;
    String res,flag;
    ProgressBar send;String email,pass1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass);
        //intizaling the variables
        send=findViewById(R.id.submiting);
        npass = findViewById(R.id.edit_npass);
        cnfrmpass = findViewById(R.id.edit_cnfrmpass);
        submit = findViewById(R.id.npass_submit);
        send=findViewById(R.id.submiting);
        shake = AnimationUtils.loadAnimation(this,R.anim.shake);
        forget = findViewById(R.id.newpass_Layout);
        show_pass=findViewById(R.id.show_hide_password);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitButtonTask();
            }
        });
        show_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button,
                                         boolean isChecked) {
                // If it is checkec then show password else hide
                // password
                if (isChecked) {
                    show_pass.setText(R.string.hide_pwd);// change
                    // checkbox
                    // text
                    npass.setInputType(InputType.TYPE_CLASS_TEXT);
                    npass.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());// show password
                } else {
                    show_pass.setText(R.string.show_pwd);// change
                    // checkbox
                    // text
                    npass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    npass.setTransformationMethod(PasswordTransformationMethod.getInstance());// hide password
                }

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
        Boolean pass=(validatePassword(npass.getText().toString())&&validateConfirm(cnfrmpass.getText().toString()));
        flag=bun.getString("flag");
        if(pass) {
            NewPassActivity.BackgroundTask b = new NewPassActivity.BackgroundTask(this);
            b.execute(bun.getString("Email"), bun.getString("flag"));
        }
    }
    private boolean validatePassword(String string) {
        if (string.equals("")) {
            send.setVisibility(View.INVISIBLE);
            send.setIndeterminate(false);
            submit.setVisibility(View.VISIBLE);
            submit.setEnabled(true);
            Toast.makeText(this,"Enter Your Password",Toast.LENGTH_SHORT).show();
            return false;
        } else if (string.length() > 32) {
            send.setVisibility(View.INVISIBLE);
            send.setIndeterminate(false);
            submit.setVisibility(View.VISIBLE);
            submit.setEnabled(true);
            Toast.makeText(this,"Maximum 32 Characters",Toast.LENGTH_SHORT).show();
            return false;
        } else if (string.length() < 8) {
            send.setVisibility(View.INVISIBLE);
            send.setIndeterminate(false);
            submit.setVisibility(View.VISIBLE);
            submit.setEnabled(true);
            Toast.makeText(this,"Minimum 8 Characters",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validateConfirm(String string) {
        if (string.equals("")) {
            send.setVisibility(View.INVISIBLE);
            send.setIndeterminate(false);
            submit.setVisibility(View.VISIBLE);
            submit.setEnabled(true);

            Toast.makeText(this,"Re-Enter Your Confirm Password",Toast.LENGTH_SHORT).show();
            cnfrmpass.setError("Re-Enter Your Password");
            return false;
        } else if (!string.equals(npass.getText().toString())) {
            send.setVisibility(View.INVISIBLE);
            send.setIndeterminate(false);
            submit.setVisibility(View.VISIBLE);
            submit.setEnabled(true);

            Toast.makeText(this,"Confrim Password Do Not Match!",Toast.LENGTH_SHORT).show();
            return false;
        }
        cnfrmpass.setError(null);
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
                    Intent i = new Intent(NewPassActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }

            }else{
                send.setVisibility(View.INVISIBLE);
                send.setIndeterminate(false);
                submit.setVisibility(View.VISIBLE);
                submit.setEnabled(true);
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
