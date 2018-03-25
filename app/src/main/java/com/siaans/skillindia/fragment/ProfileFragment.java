package com.siaans.skillindia.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.vision.text.Text;
import com.siaans.skillindia.R;
import com.siaans.skillindia.other.CircleTransform;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

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
    Spinner gendar;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        Bundle b = getArguments();
       String str = b.getString("json");
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
            String[] State=getResources().getStringArray(R.array.gender);
            gendar = root.findViewById(R.id.gendar);
            Log.d("", "info: "+jo.getString("gendar")+jo.getString("gendar").equals("M"));
            for(int i=0;i<State.length;i++) {
                if (jo.getString("gendar").equals("F") && State[i].equals("Female")) {
                    gendar.setSelection(i);
                    break;
                } else if (jo.getString("gendar").equals("M")&& State[i].equals("Male")) {
                    gendar.setSelection(i);
                    break;
                } else {
                    gendar.setSelection(i);
                }
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("data",str);

    }

}
