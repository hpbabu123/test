package com.siaans.skillindia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Reg_Activity extends AppCompatActivity {


    Button bt_register;
    TextInputLayout til_name, til_last, til_password, til_confirmPass, til_mobile, til_email;
    ImageView iv_profile;
    String name, last, password, email, mobile,confirm,profile;
//    RequestQueue requestQueue;
    boolean IMAGE_STATUS = false;
    Bitmap profilePicture;
    private static Animation shakeAnimation;

    private static RelativeLayout forget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_);


        setTitle("Create An Account");
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
        forget = findViewById(R.id.reg_layout);
        initialize();//Function to initialize widgets

        //creating request queue
//        requestQueue = Volley.newRequestQueue(Reg_Activity.this);

        //Adding onClickListener to the ImageView to select the profile Picture
        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1000);
                //result will be available in onActivityResult which is overridden
            }
        });
        bt_register=findViewById(R.id.Reg);
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = til_name.getEditText().getText().toString();
                last = til_last.getEditText().getText().toString();
                password = til_password.getEditText().getText().toString();
                email = til_email.getEditText().getText().toString();
                mobile = til_mobile.getEditText().getText().toString();
                confirm = til_confirmPass.getEditText().getText().toString();
                if (    //perform validation by calling all the validate functions inside the IF condition
                        validateUsername(last) &&
                                validateName(name) &&
                                validatePassword(password) &&
                                validateConfirm(confirm) &&
                                validateMobile(mobile) &&
                                validateEmail(email) &&
                                validateProfile()
                        ) {
                    //Validation Success
                    try {
                        Intent intent = new Intent(Reg_Activity.this, Registerpage1Activity.class);
                        Bundle b = new Bundle();
                        b.putString("name", name);
                        b.putString("username", last);
                        b.putString("password", password);
                        b.putString("email", email);
                        b.putString("mobile", mobile);
                        b.putString("profile", profile);
                        intent.putExtras(b);
                        startActivity(intent);
                    }catch (Exception e){
                        Log.d("de'", "onClick: d");
                    }
                   }
                   else{
                    forget.startAnimation(shakeAnimation);
                }
            }
        });


    }

    private void convertBitmapToString(Bitmap profilePicture) {
        /*
            Base64 encoding requires a byte array, the bitmap image cannot be converted directly into a byte array.
            so first convert the bitmap image into a ByteArrayOutputStream and then convert this stream into a byte array.
        */
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        profilePicture.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] array = byteArrayOutputStream.toByteArray();
        profile = Base64.encodeToString(array, Base64.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK && data != null) {
            //Image Successfully Selected
            try {
                //parsing the Intent data and displaying it in the imageview
                Uri imageUri = data.getData();//Geting uri of the data
                InputStream imageStream = getContentResolver().openInputStream(imageUri);//creating an imputstrea
                profilePicture = BitmapFactory.decodeStream(imageStream);//decoding the input stream to bitmap
                iv_profile.setImageBitmap(profilePicture);
                IMAGE_STATUS = true;//setting the flag
                convertBitmapToString(profilePicture);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    private void initialize() {
        //Initializing the widgets in the layout
        til_name = (TextInputLayout) findViewById(R.id.First_namelayout);
        til_last = (TextInputLayout) findViewById(R.id.lastlayout);
        til_password = (TextInputLayout) findViewById(R.id.confirmpasswordlayout);
        til_confirmPass = (TextInputLayout) findViewById(R.id.confirmpasswordlayout);
        til_mobile = (TextInputLayout) findViewById(R.id.mobilelayout);
        til_email = (TextInputLayout) findViewById(R.id.emaillayout);
        bt_register = (Button) findViewById(R.id.Reg);
        iv_profile = (ImageView) findViewById(R.id.im_profile);
    }
    private boolean validateUsername(String string) {
        if (string.equals("")) {
            til_last.setError("Enter A Username");
            return false;
        } else if (string.length() > 50) {
            til_last.setError("Maximum 50 Characters");
            return false;
        }
        til_last
                .setErrorEnabled(false);
        return true;
    }

    private boolean validateName(String string) {
        if (string.equals("")) {
            til_name.setError("Enter Your Name");
            return false;
        } else if (string.length() > 50) {
            til_name.setError("Maximum 50 Characters");
            return false;
        }
        til_name.setErrorEnabled(false);
        return true;
    }

    private boolean validatePassword(String string) {
        if (string.equals("")) {
            til_password.setError("Enter Your Password");
            return false;
        } else if (string.length() > 32) {
            til_password.setError("Maximum 32 Characters");
            return false;
        } else if (string.length() < 8) {
            til_password.setError("Minimum 8 Characters");
            return false;
        }
        til_password.setErrorEnabled(false);
        return true;
    }

    private boolean validateConfirm(String string) {
        if (string.equals("")) {
            til_confirmPass.setError("Re-Enter Your Password");
            return false;
        } else if (!string.equals(til_password.getEditText().getText().toString())) {
            til_confirmPass.setError("Passwords Do Not Match");
            til_password.setError("Passwords Do Not Match");
            return false;
        }
        til_confirmPass.setErrorEnabled(false);
        return true;
    }

    private boolean validateMobile(String string) {
        if (string.equals("")) {
            til_mobile.setError("Enter Your Mobile Number");
            return false;
        }
        if (string.length() != 10) {
            til_mobile.setError("Enter A Valid Mobile Number");
            return false;
        }
        til_mobile.setErrorEnabled(false);
        return true;
    }

    private boolean validateEmail(String string) {
        if (string.equals("")) {
            til_email.setError("Enter Your Email Address");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(string).matches()) {
            til_email.setError("Enter A Valid Email Address");
            return false;
        }
        til_email.setErrorEnabled(false);
        return true;
    }

    private boolean validateProfile() {
        if (!IMAGE_STATUS)
            Toast.makeText(this, "Select A Profile Picture", Toast.LENGTH_SHORT).show();
        return IMAGE_STATUS;
    }
}
