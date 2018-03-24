package com.siaans.skillindia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotActivity extends AppCompatActivity{

    private static EditText emailId;
    private static TextView submit;
    private static Animation shake;
    private static LinearLayout forget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the view
        setContentView(R.layout.activity_forgot);
        //intizaling the variables
        emailId = findViewById(R.id.registered_emailid);
        submit = findViewById(R.id.forgot_button);
        shake = AnimationUtils.loadAnimation(this,R.anim.shake);
        forget = findViewById(R.id.forget_Layout);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitButtonTask();
            }
        });
    }


    private void submitButtonTask() {
        String getEmailId = emailId.getText().toString();
        // Pattern for email id validation
        Pattern p = Pattern.compile(Emailchecker.regEx);

        // Match the pattern
        Matcher m = p.matcher(getEmailId);

        // First check if email id is not null else show error toast
        if (getEmailId.equals("") || getEmailId.length() == 0){
            forget.startAnimation(shake);
            Toast.makeText(this,"Enter the EmailId",Toast.LENGTH_SHORT).show();
        }
        // Check if email id is valid or not
        else if (!m.find()){
            Toast.makeText(this,"Invalid MailID",Toast.LENGTH_SHORT).show();
        }
        // Else submit email id and fetch passwod or do your stuff
        else{
            Toast.makeText(this,"Check your Mail For new password",Toast.LENGTH_SHORT).show();
            Intent i= new Intent(ForgotActivity.this , LoginActivity.class);
            finish();
        }

    }
}