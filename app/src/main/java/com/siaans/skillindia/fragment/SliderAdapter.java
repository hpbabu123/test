package com.siaans.skillindia.fragment;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.siaans.skillindia.R;

/**
 * Created by Admin on 19-Mar-18.
 */

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] slide_images = {
            R.drawable.pmvyk1,
            R.drawable.objective1,
            R.drawable.scope,
            R.drawable.apply
    };
    public String[] slide_headings ={
            "WHAT IS PMKVY ?",
            "OBJECTIVES",
            "SCOPE",
            "HOW TO APPLY"
    };
    public String[] slide_desc= {
            " Approved for another four years (2016-2020) to benefit 10 million youth Allocated Budget 12,000 Crores Pradhan Mantri Kaushal Vikas Yojana (PMKVY) is the flagship scheme of the Ministry of Skill Development and Entrepreneurship (MSDE). The objective of this Skill Certification Scheme is to enable a large number of Indian youth to take up industry-relevant skill training that will help them in securing a better livelihood. Individuals with prior learning experience or skills will also be assessed and certified under Recognition of Prior Learning (RPL).\n",
            "1.Create opportunities for all to acquire skills, especially for youth, women and disadvantaged groups (SC, ST, and OBC)\n\n2.Promote commitment by all stakeholders to own skill development initiatives.\n\n3.Develop a high-quality skilled workforce that will help emerging employment market needs\n\n4.Enable effective coordination between different ministries, the Centre and the States and public and private providers.  ",
            "1.Encourage Standardization in the certification process\n\n2.Provide Monetary Awards to the candidates who complete the course successfully\n\n3.Training for self – employment development\n\n4.Adult learning, retiring employees and lifelong learning\n\n5.institutional based skill development including vocational schools/ ITCs/ ITIS/ polytechnics ",
            "1 : Register yourself and  Find a Training Center\n\n" +
                    "2 : Learn A Skill\n\n" +
                    "3 : Get Enrolled\n\n" +
                    "4 : Be Assessed and Certified\n\n" +
                    "5 : Gain A Reward"

    };
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view ==(RelativeLayout) o;
    }
    @Override
    public Object instantiateItem(ViewGroup container,int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_desc[position]);

        container.addView(view);

        return view;
    }

    public void destroyItem(ViewGroup container,int position,Object object){
        container.removeView((RelativeLayout)object);
    }
}