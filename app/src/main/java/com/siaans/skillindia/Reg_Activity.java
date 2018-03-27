package com.siaans.skillindia;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.siaans.skillindia.activity.NavigationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Reg_Activity extends AppCompatActivity {
    String dob_date;
    Button dob;
    int year_x,month_y,date_z;
    static final int DILOG_ID = 0;
    Button bt_register;
    TextInputLayout til_name, til_last, til_password, til_confirmPass, til_mobile, til_email,adhaar,address,pincodelayout;
    ImageView iv_profile;
    String name,gend, last, password, email, mobile,confirm,profile,adhharc,addres,pincode;
//    RequestQueue requestQueue;
    boolean IMAGE_STATUS = false;
    Bitmap profilePicture;
    private static Animation shakeAnimation;
    TextInputLayout dateofbirth;
    ProgressBar send;
    RadioGroup gender;
    LinearLayout genderLayout;
    private static RelativeLayout forget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_);

        showDialogOnButtonClick();

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
                send.setVisibility(View.VISIBLE);
                send.setIndeterminate(true);
                bt_register.setVisibility(View.INVISIBLE);
                bt_register.setEnabled(false);
                name = til_name.getEditText().getText().toString();
                last = til_last.getEditText().getText().toString();
                password = til_password.getEditText().getText().toString();
                email = til_email.getEditText().getText().toString();
                mobile = til_mobile.getEditText().getText().toString();
                confirm = til_confirmPass.getEditText().getText().toString();
                adhharc = adhaar.getEditText().getText().toString();
                addres = address.getEditText().getText().toString();
                pincode = pincodelayout.getEditText().getText().toString();
                if (    //perform validation by calling all the validate functions inside the IF condition
                        validateUsername(last) &&
                                validateName(name) &&
                                validatePassword(password) &&
                                validateConfirm(confirm) &&
                                validateMobile(mobile) &&
                                validateEmail(email) &&
                                validateProfile() && validateadhaar(adhharc) && validategender(gender)&& validatepincode(pincode)

                        ) {
                    //Validation Success

                    JSONObject b = new JSONObject();
                    try {        // Adding All values to Params.
                        b.put("name", name + last);
                        b.put("password", password);
                        b.put("email", email);
                        b.put("mobile", mobile);
                       // b.put("profile", profile);
                        b.put("adhaar", adhharc);
                        b.put("DOB", dob_date);
                        b.put("Gender",gend );
                        b.put("Address",addres);
                        b.put("pincode",pincode);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("s", "onClick: cdxs");

                    }
                    String message = b.toString();
                    Log.d("s", "onClick: " + message);
                    Reg_Activity.BackgroundTask bkk = new Reg_Activity.BackgroundTask(Reg_Activity.this);
                    bkk.execute(message);
//
//
//                        intent.putExtras(b);
//                        startActivity(intent);

                } else {
                    send.setVisibility(View.INVISIBLE);
                    send.setIndeterminate(false);
                    bt_register.setVisibility(View.VISIBLE);
                    bt_register.setEnabled(true);
                    forget.startAnimation(shakeAnimation);
                }
            }
        });


    }

    public void showDialogOnButtonClick(){
        dob = (Button)findViewById(R.id.dob);
        dob.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DILOG_ID);
                    }
                }
        );
    }
    @Override
    protected Dialog onCreateDialog(int id){
        if (id == DILOG_ID)
            return new DatePickerDialog(this ,dpickerListener,year_x,month_y,date_z);
        return null;
    }
    private DatePickerDialog.OnDateSetListener dpickerListener
        = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x = year;
            month_y= month;
            date_z = dayOfMonth;
            dob_date = year_x+"-"+(month_y+1)+"-"+date_z;
            dateofbirth.getEditText().setText(dob_date);
        }
    };





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
        dateofbirth=(TextInputLayout)findViewById(R.id.doblayout);
        adhaar = (TextInputLayout)findViewById(R.id.adharlayout);
        send=(ProgressBar) findViewById(R.id.submiting);
        gender=(RadioGroup)findViewById(R.id.gen);
        genderLayout=(LinearLayout)findViewById(R.id.genderLayout);
        address=(TextInputLayout) findViewById(R.id.addresslayout);
        pincodelayout=(TextInputLayout) findViewById(R.id.pincodelayout);
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
    private boolean validategender(RadioGroup gender){
        if(gender.getCheckedRadioButtonId() == -1){

            Toast.makeText(this,"Select one of the gender!!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            int selectedId = gender.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            RadioButton b = (RadioButton) findViewById(selectedId);
            gend= String.valueOf(b.getText());
            Log.d("d", "validategender: "+gend.toString());
            return true;
        }
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

    private boolean validatepincode(String string) {
        if (string.equals("")) {
            pincodelayout.setError("Enter Your pincode");
            return false;
        }
        if (string.length() != 6) {
            pincodelayout.setError("Enter A Valid pincode");
            return false;
        }
        pincodelayout.setErrorEnabled(false);
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
    private boolean validateadhaar(String adhhar){
        if (adhhar.equals("")) {
            adhaar.setError("Enter Your Adhaar card number");
            return false;
        }
        if (adhhar.length() != 12) {
            adhaar.setError("Enter A Valid adhaar card number");
            return false;
        }
        adhaar.setErrorEnabled(false);
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
            add_info_url ="http://159.65.144.10/miniproject/insertion.php";

        }

        @Override
        protected String doInBackground(String... args) {
            try{
                URL url=new URL(add_info_url);
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                conn.setReadTimeout( 10000 /*milliseconds*/ );
                conn.setConnectTimeout( 15000 /* milliseconds */ );
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(args[0].getBytes().length);
                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
                //open
                conn.connect();
                //setup send
                OutputStream os = new BufferedOutputStream(conn.getOutputStream());
                os.write(args[0].getBytes());
                //clean up
                os.flush();
//                OutputStream os=httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
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
                        in.close();

                        return response.toString();
                }



            }catch (Exception e){
                e.printStackTrace();
            }
            return "No Interconnection";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String r) {
            Log.d("kdcn", "onPostExecute: "+r);
                if(r.contains("Successful Register!!")){
                    Toast.makeText(ctx,"Successfully Register!!Verify yourself in nearby TC",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(ctx, NavigationActivity.class);
                    startActivity(i);
                    finish();
                }else if(r.contains("Adhaar")){
                    send.setVisibility(View.INVISIBLE);
                    send.setIndeterminate(false);
                    bt_register.setVisibility(View.VISIBLE);
                    bt_register.setEnabled(true);
                    Toast.makeText(ctx,"Already Register With this Adhaar Card",Toast.LENGTH_SHORT).show();
                }else if(r.contains("Mobile")){
                    send.setVisibility(View.INVISIBLE);
                    send.setIndeterminate(false);
                    bt_register.setVisibility(View.VISIBLE);
                    bt_register.setEnabled(true);
                    Toast.makeText(ctx,"Already Register With this Mobile Number",Toast.LENGTH_SHORT).show();
                }else if(r.contains("EmailID")){
                    send.setVisibility(View.INVISIBLE);
                    send.setIndeterminate(false);
                    bt_register.setVisibility(View.VISIBLE);
                    bt_register.setEnabled(true);
                    Toast.makeText(ctx,"Already Register With this EmailID",Toast.LENGTH_SHORT).show();
                }
                else if(r.contains("No Interconnection")){
                    send.setVisibility(View.INVISIBLE);
                    send.setIndeterminate(false);
                    bt_register.setVisibility(View.VISIBLE);
                    bt_register.setEnabled(true);
                    Toast.makeText(ctx,"No Interconnection",Toast.LENGTH_SHORT).show();
                }
                else{
                    send.setVisibility(View.INVISIBLE);
                    send.setIndeterminate(false);
                    bt_register.setVisibility(View.VISIBLE);
                    bt_register.setEnabled(true);
                    Toast.makeText(ctx,"Try Again Internal Server Error",Toast.LENGTH_SHORT).show();
                }
        }
    }
}
