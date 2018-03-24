package com.siaans.skillindia;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.siaans.skillindia.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

/**
 * Created by HP on 22-03-2018.
 */

public class ExDialog extends AppCompatDialogFragment {
    private EditText editTextEmailid;
    private EditText editTextSubject;
    private EditText editTextText;
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.layout_dialog,null);
        editTextEmailid=view.findViewById(R.id.edit_email_id);
        editTextSubject=view.findViewById(R.id.edit_subject);
        editTextText=view.findViewById(R.id.edit_message);

        builder.setView(view)
                .setTitle("Feedback")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })

                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BackgroundTask b=new BackgroundTask();
                        b.execute(editTextEmailid.getText().toString(),editTextSubject.getText().toString(),editTextText.getText().toString());

                    }
                });
        return builder.create();

    }
    class BackgroundTask extends AsyncTask<String,Void,String> {

        String add_info_url;

        @Override
        protected void onPreExecute() {
            add_info_url = "http://159.65.144.10/miniproject/feedback.php";

        }

        @Override
        protected String doInBackground(String... args) {
            try {
                URL url = new URL(add_info_url + "?email=" + args[0] + "&subject=" + args[1]+"&flag="+args[2]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
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

        }
    }

}