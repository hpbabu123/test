package com.siaans.skillindia;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.siaans.skillindia.activity.NavigationActivity;

public class Trainee_Login_Activity extends AppCompatActivity {

    private Button logbutton;
    private Button regbutton;
    private Button skip;
    private  Button log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee__login_);

        logbutton=findViewById(R.id.btnLogin);
        regbutton=findViewById(R.id.btnLinkToRegisterScreen);
        skip=findViewById(R.id.skip);
        log=findViewById(R.id.TC);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trainee_Login_Activity.this,TCloginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trainee_Login_Activity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trainee_Login_Activity.this,Reg_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Trainee_Login_Activity.this, NavigationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

