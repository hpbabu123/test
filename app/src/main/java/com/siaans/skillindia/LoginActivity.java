package com.siaans.skillindia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.siaans.skillindia.activity.TraineeNavActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    public SharedPreferences.Editor loginPrefsEditor;
    public  SharedPreferences loginPreferences;
    private Boolean saveLogin;

    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;

    public LoginActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the view
        setContentView(R.layout.activity_login);
        //intizaling the variables

        emailid = findViewById(R.id.login_emailid);
        password = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.btnLogin);
        forgotPassword = findViewById(R.id.forgot_password);
        signUp = findViewById(R.id.btnLinkToRegisterScreen);
        show_hide_password = findViewById(R.id.show_hide_password);
        loginLayout = findViewById(R.id.login_layout);
        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);



        if (saveLogin == true) {
            emailid.setText(loginPreferences.getString("username", ""));
            password.setText(loginPreferences.getString("password", ""));


        }

        // login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailid.getText().toString();
                String pass1 = password.getText().toString();
                //new Thread (new Task()).start();
               // new myAsyncTask().execute()+
                // Get email id and password
                String getEmailId = emailid.getText().toString();
                String getPassword = password.getText().toString();
                // Check patter for email id
                Pattern p = Pattern.compile(Emailchecker.regEx);
                Matcher m = p.matcher(getEmailId);
                // Check for both field is empty or not
                if (getEmailId.equals("") || getEmailId.length() == 0 || getPassword.equals("") || getPassword.length() == 0) {
                    loginLayout.startAnimation(shakeAnimation);
                    Toast.makeText(LoginActivity.this, "Enter Both Credentials", Toast.LENGTH_SHORT).show();
                }
                // Check if email id is valid or not
                else if (!m.find()) {
                    Toast.makeText(LoginActivity.this, "Invalid EMail", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(LoginActivity.this, "Login", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(LoginActivity.this, TraineeNavActivity.class);
//                    startActivity(intent);
//                    finish();
                    BackgroundTask backgroundTask = new BackgroundTask();
                    backgroundTask.execute(getEmailId,getPassword);
                    SharedPreferences.Editor h=  loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", email);
                    loginPrefsEditor.putString("password", pass1);
                    loginPrefsEditor.commit();

                }
            }

        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, TCForgotActivity.class);
                Bundle b=new Bundle();
                b.putString("flag","0");
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Reg_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        show_hide_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button,
                                         boolean isChecked) {
                // If it is checkec then show password else hide
                // password
                if (isChecked) {
                    show_hide_password.setText(R.string.hide_pwd);// change
                    // checkbox
                    // text
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    password.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());// show password
                } else {
                    show_hide_password.setText(R.string.show_pwd);// change
                    // checkbox
                    // text
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());// hide password
                }

            }

        });
    }
    class BackgroundTask extends AsyncTask<String,Void,String>{

        String add_info_url;
        @Override
        protected void onPreExecute() {
            add_info_url ="http://159.65.144.10/miniproject/login.php";

        }

        @Override
        protected String doInBackground(String... args) {
            add_info_url = add_info_url+"?EMAILID='"+args[0]+"'&PASSWORD='"+args[1]+"'";
            Log.d("sdf", "doInBackground: "+add_info_url);
            try {
                URL url = new URL(add_info_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader buff=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String r="";
                String l="";
                while((l=buff.readLine())!=null){
                    r+=l;
                }
                buff.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return r;

            }catch (Exception e){
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
            if(r.equals("Wrong Username or Password")){
                Toast.makeText(LoginActivity.this,"Invalid username & password",Toast.LENGTH_LONG).show();

            }
            else{
                Intent intent = new Intent(LoginActivity.this, TraineeNavActivity.class);
                    startActivity(intent);
                    finish();

            }

        }
    }
}