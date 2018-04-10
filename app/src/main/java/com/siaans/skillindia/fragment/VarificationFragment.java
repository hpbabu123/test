package com.siaans.skillindia.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siaans.skillindia.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VarificationFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout swipeLayout;


    public VarificationFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_varification, container, false);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(android.R.color.holo_green_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);
        return view;
    }

    @Override
    public void onRefresh() {

    }
}
