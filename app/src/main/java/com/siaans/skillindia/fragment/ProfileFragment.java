package com.siaans.skillindia.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.siaans.skillindia.ChangeActivity;
import com.siaans.skillindia.R;
import com.siaans.skillindia.other.CircleTransform;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    View root;
    ImageView img;
    TextView name;
    TextView addharcard;
    TextView dateofbirth;
    TextView email;
    TextView mobile;
    TextView address;
    TextView pincode;
    TextView gender;
    TextView varification;
    Button chngpass;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_profile, container, false);

        Bundle b = getArguments();
       final String str = b.getString("json");
       final String type=b.getString("type");

       chngpass= root.findViewById(R.id.chngpass);
       chngpass.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               try {
                   JSONObject jo= new JSONObject(str);
                   Intent i=new Intent(getActivity(), ChangeActivity.class);
                   Bundle b=new Bundle();
                   b.putString("Email",jo.getString("emailid"));
                   b.putString("flag","0");
                   b.putString("oldpass",jo.getString("password"));
                   i.putExtras(b);
                   startActivity(i);

               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
       });
        Log.d("", "onCreateView: "+str);
       info(str);
        return root;
    }


    void info(String str){
        try {
            JSONObject jo= new JSONObject(str);
            img = root.findViewById(R.id.profileImg);
            Context c= getActivity();
            Glide.with(this).load("http://159.65.144.10/miniproject/profileImg/TID_"+jo.getString("tid")+".JPG")
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(c))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img);
            name = root.findViewById(R.id.name);
            name.setText(jo.getString("name"));

            addharcard = root.findViewById(R.id.adhaar);
            addharcard.setText(jo.getString("adhaar"));

            dateofbirth = root.findViewById(R.id.dob);
            dateofbirth.setText(jo.getString("DOB"));

            email = root.findViewById(R.id.emailId);
            email.setText(jo.getString("emailid"));

            mobile = root.findViewById(R.id.mob);
            mobile.setText(jo.getString("mob"));

            address = root.findViewById(R.id.address);
            address.setText(jo.getString("address"));

            pincode = root.findViewById(R.id.pincode);
            pincode.setText(jo.getString("pincode"));

            gender = root.findViewById(R.id.gender);

            if(jo.getString("gendar").equals("M")){
                gender.setText("Male");
            }else if(jo.getString("gendar").equals("F")){
                gender.setText("Female");
            }else{
                gender.setText("Other");
            }


            varification = root.findViewById(R.id.varification);
            varification.setText(jo.getString("verification"));



        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("data",str);

    }


}
