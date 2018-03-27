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
import android.widget.TextView;

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
public class TCProfileFragment extends Fragment {
    View root;
    Button chngpass;
    ImageView img;
    TextView tcname;
    TextView tp;
    TextView tcspocname;
    TextView email;
    TextView mobile;
    TextView address;
    TextView pincode;
    TextView batchid;
    TextView trainee;


    public TCProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_tcprofile, container, false);
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
                    b.putString("flag","1");
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
            Log.d("", "info: "+str);
            JSONObject jo= new JSONObject(str);
            img = root.findViewById(R.id.tcprofileImg);
            Context c= getActivity();
            Glide.with(this).load("http://159.65.144.10/miniproject/profileImg/CID_"+jo.getString("tcid")+".JPG")
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(c))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img);
            tcname = root.findViewById(R.id.tcname);
            tcname.setText(jo.getString("tcname"));

            tp = root.findViewById(R.id.tp);
            tp.setText(jo.getString("tp"));

            tcspocname = root.findViewById(R.id.tcspocname);
            tcspocname.setText(jo.getString("tcspocname"));

            email = root.findViewById(R.id.emailId);
            email.setText(jo.getString("emailid"));

            mobile = root.findViewById(R.id.mob);
            mobile.setText(jo.getString("mobno"));

            address = root.findViewById(R.id.address);
            address.setText(jo.getString("address"));

            pincode = root.findViewById(R.id.pincode);
            pincode.setText(jo.getString("pincode"));

            batchid = root.findViewById(R.id.batchid);
            batchid.setText(jo.getString("batch"));

            trainee = root.findViewById(R.id.trainees);
            trainee.setText(jo.getString("trainee"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("data",str);

    }



}
