package com.siaans.skillindia;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.siaans.skillindia.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

/**
 * Created by HP on 22-03-2018.
 */

@SuppressLint("ValidFragment")
public class Chngepass extends AppCompatDialogFragment {
    String pass;

    @SuppressLint("ValidFragment")
    public Chngepass(String pass, String email, String type,Context ctx){
        this.pass=pass;
    }
    private EditText editTextoldpass;
    private CheckBox show_hide_password;
    private exampledialogue list;
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.activity_chngepass,null);
        editTextoldpass=view.findViewById(R.id.edit_oldpass);
        show_hide_password = view.findViewById(R.id.show_hide_password);

        builder.setView(view)
                .setTitle("Change Password")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })

                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(pass.equals(editTextoldpass.getText().toString())) {
                                    list.old(true);

                        }else{

                        }
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
                    editTextoldpass.setInputType(InputType.TYPE_CLASS_TEXT);
                    editTextoldpass.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());// show password
                } else {
                    show_hide_password.setText(R.string.show_pwd);// change
                    // checkbox
                    // text
                    editTextoldpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    editTextoldpass.setTransformationMethod(PasswordTransformationMethod.getInstance());// hide password
                }

            }

        });

        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            list = (exampledialogue) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement");
        }
    }


    public  interface  exampledialogue{
        void old(Boolean check);
}

   }