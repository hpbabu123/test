package com.siaans.skillindia.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siaans.skillindia.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }
    private ViewPager mSlideViewPager;
    private LinearLayout mdotsLayout;

    private TextView[] mDots;
    private SliderAdapter slideAdapter;

    private Button mNextBtn;
    private Button mBackBtn;

    private int mCurrentPage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, null);

        mSlideViewPager = (ViewPager) rootView.findViewById(R.id.slideViewPager);
        mdotsLayout = (LinearLayout) rootView.findViewById(R.id.dotsLayout);

        mBackBtn = (Button) rootView.findViewById(R.id.prevBtn);
        mNextBtn = (Button) rootView.findViewById(R.id.nextBtn);

        slideAdapter = new SliderAdapter(getActivity());
        mSlideViewPager.setAdapter(slideAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        mNextBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mSlideViewPager.setCurrentItem(mCurrentPage + 1);
            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mSlideViewPager.setCurrentItem(mCurrentPage -  1);
            }
        });
        return rootView;
    }

    public void addDotsIndicator(int postition){
        mDots =new TextView[4];
        mdotsLayout.removeAllViews();

        for (int i=0;i < mDots.length;i++){

            mDots[i] =  new TextView(getActivity());
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(50);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransperentWhite));

            mdotsLayout.addView(mDots[i]);
        }
        if (mDots.length > 0){
            mDots[postition].setTextColor(getResources().getColor(R.color.black));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);

            mCurrentPage = i;

            if (i == 0){
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.INVISIBLE);

                mNextBtn.setText("Next");
                mBackBtn.setText("");
            }else if (i == mDots.length - 1){
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.INVISIBLE);

                mNextBtn.setText("Finish");
                mBackBtn.setText("Back");

            }else {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.INVISIBLE);

                mNextBtn.setText("Next");
                mBackBtn.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
