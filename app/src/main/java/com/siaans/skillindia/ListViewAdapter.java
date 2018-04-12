package com.siaans.skillindia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by HarshPanjwani on 12-04-2018.
 */

public class ListViewAdapter extends ArrayAdapter<TCenter> {
    private List<TCenter> heroList;

    //the context object
    private Context mCtx;

    public ListViewAdapter(List<TCenter> heroList, Context mCtx) {
        super(mCtx, R.layout.item, heroList);
        this.heroList = heroList;
        this.mCtx = mCtx;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.item, null, true);

        //getting text views
        TextView textViewTCName = listViewItem.findViewById(R.id.TC_Name);
        TextView textViewTPname = listViewItem.findViewById(R.id.TPname);
        TextView textViewEMAIL = listViewItem.findViewById(R.id.Email_id);
        TextView textViewSF = listViewItem.findViewById(R.id.Seats_filled);
        TextView textViewTCSPOC = listViewItem.findViewById(R.id.TC_SPOC_Name);
        TextView textViewMob = listViewItem.findViewById(R.id.Mobile_no);
        TextView textViewBAt = listViewItem.findViewById(R.id.Batch_Name);
        TextView textViewAdd = listViewItem.findViewById(R.id.Address);
        TextView textViewPin = listViewItem.findViewById(R.id.pin);

        //Getting the hero for the specified position
        TCenter hero = heroList.get(position);

        //setting hero values to textviews
        textViewTPname.setText(hero.tp());
        textViewTCName.setText(hero.tcname());
        textViewEMAIL.setText(hero.emailid());
         textViewSF.setText(hero.seats());
         textViewTCSPOC.setText(hero.tcspocname());
         textViewMob.setText(hero.mobno());
         textViewBAt.setText(hero.batchname());
        textViewAdd.setText(hero.address());
      textViewPin.setText(hero.pincode());

        //returning the listitem
        return listViewItem;
    }
}

