package com.siaans.skillindia.fragment;


import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView.*;
import android.widget.TextView;
import android.widget.Toast;

import com.siaans.skillindia.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseFragment extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    public CourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_course, null);

        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);

        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        Log.i("groups", listDataHeader.toString());
        Log.i("details", listDataChild.toString());

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
               }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return false;
            }
        });
        return rootView;

    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Agriculture Sector Skill Council");
        listDataHeader.add("Construction");
        listDataHeader.add("Automotive");
        listDataHeader.add("Beauty and Wellness");
        listDataHeader.add("Capital Goods");
        listDataHeader.add("Domestic Workers");
        listDataHeader.add("Earthmoving & Infrastructure Building");
        listDataHeader.add("Electronics & Hardware");
        listDataHeader.add("Food Processing");
        listDataHeader.add("Furniture and Fitting");
        listDataHeader.add("Gems and Jewellery");
        listDataHeader.add("Green Jobs");
        listDataHeader.add("Handicrafts");
        listDataHeader.add("Healthcare");
        listDataHeader.add("Iron and Steel");
        // Adding child data

        List<String> Agriculture = new ArrayList<String>();
        Agriculture.add("Animal Health Worker");
        Agriculture.add("Aquaculture Worker");
        Agriculture.add("Dairy Farmer Entrepreneur");
        Agriculture.add("Gardener");
        Agriculture.add("Green House Operator");
        Agriculture.add("Micro Irrigation Technician");
        Agriculture.add("Organic grower");
        Agriculture.add("Quality Seed Grower");
        Agriculture.add("Small poultry farmer");
        Agriculture.add("Tractor Operator");

        List<String> Construction = new ArrayList<String>();
        Construction.add("Assistant Electrician");
        Construction.add("Assistant Bar-Bender Steel Fixer");
        Construction.add("Assistant False Ceilling & Drywall Installer");
        Construction.add("Assistant Mason");
        Construction.add("Assistant Scaffolder System");
        Construction.add("Assistant Shuttering Carpenter");
        Construction.add("Assistant Painter Decorator");


        List<String> Automotive = new ArrayList<String>();
        Automotive.add("Automotive Service Technician Two & Three Wheelers");
        Automotive.add("Auto E-Rickshaw Driver Service Technician");
        Automotive.add("Welding & Quality Technician");
        Automotive.add("Dealership Telecaller Sales Executive");
        Automotive.add("Showroom Hostess Customer Relationship Executive");

        List<String> Beauty = new ArrayList<String>();
        Beauty.add("Assistant Beauty Therapist");
        Beauty.add("Assistant Hair Stylist");
        Beauty.add("Assistant Nail Technician");
        Beauty.add("Assistant Spa Therapist");
        Beauty.add("Beauty Therapist");

        List<String> Capital = new ArrayList<String>();
        Capital.add("CNC Operator Turning");
        Capital.add("Assistant MMAW-SMAW Welder");
        Capital.add("Assistant Oxy Fuel-Gas Cutter");
        Capital.add("Draughtsman – Mechanical");
        Capital.add("Fitter – Electrical and Electronic Assembly");

        List<String> Domestic = new ArrayList<String>();
        Domestic.add("Child Caretaker");
        Domestic.add("Elderly Caretaker (Non-Clinical)");
        Domestic.add("General Housekeeper");
        Domestic.add("Housekeeper cum cook");

        List<String> Earthmoving = new ArrayList<String>();
        Earthmoving.add("Backhoe Loader Operator");
        Earthmoving.add("Batching Plant Operator");
        Earthmoving.add("Compactor Operator");
        Earthmoving.add("Compressor Operator");
        Earthmoving.add("Concrete Pump Operator");

        List<String> Electronics = new ArrayList<String>();
        Electronics.add("CCTV Installation Technician");
        Electronics.add("DTH Set Top Box Installation & Service Technician");
        Electronics.add("Field Technician – Computing and Peripherals");
        Electronics.add("Field Technician – Networking and Storage");
        Electronics.add("Field Technician – Other Home Appliances");

        List<String> Food = new ArrayList<String>();
        Food.add("Baking Technician");
        Food.add("Craft Baker");
        Food.add("Jam Jelly & Ketchup Processing Technician");
        Food.add("Pickle Making Technician");
        Food.add("Plant Biscuit Production Specialist");

        List<String> Furniture = new ArrayList<String>();
        Furniture.add("Assistant Carpenter Wooden Furniture");
        Furniture.add("Carpenter - Wooden Furniture");
        Furniture.add("Assistant Fitter Modular Furniture");
        Furniture.add("Fitter Modular Furniture");

        List<String> Gems = new ArrayList<String>();
        Gems.add("Cast and diamonds-set jewellery - CAD Operator");
        Gems.add("Cast and diamonds-set jewellery - Hand Sketch Designer (Basic)");
        Gems.add("Cast & Diamonds Set Jewellery - Wax Setter");
        Gems.add("Cast & diamonds-set jewellery Merchandiser Design");
        Gems.add("Cast & diamonds-set jewellery Metal Setter(Basic)");

        List<String> Green = new ArrayList<String>();
        Green.add("Solar PV Installer - Civil");
        Green.add("Solar PV Installer - Electrical");
        Green.add("Solar PV Installer (Suryamitra)");
        Green.add("Wastewater Treatment Plant Helper");
        Green.add("Wastewater Treatment Plant Technician");

        List<String> Handicrafts = new ArrayList<String>();
        Handicrafts.add("Agarbatti Packer");
        Handicrafts.add("Bamboo Basket Maker");
        Handicrafts.add("Bamboo Mat Weaver");
        Handicrafts.add("Bamboo Utility Handicraft Assembler");
        Handicrafts.add("Hammering Artisan");

        List<String> Healthcare = new ArrayList<String>();
        Healthcare.add("Diabetes Educator");
        Healthcare.add("Diet Assistant");
        Healthcare.add("Emergency Medical Technician-Basic");
        Healthcare.add("Frontline Health Worker");
        Healthcare.add("General Duty Assistant");

        List<String> Iron = new ArrayList<String>();
        Iron.add("Bearing Maintenance");
        Iron.add("EOT Overhead Crane Operator");
        Iron.add("Fitter Electrical Assembly");
        Iron.add("Fitter Electronic Assembly");
        Iron.add("Fitter Instrumentation");




        listDataChild.put(listDataHeader.get(0), Agriculture); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Construction);
        listDataChild.put(listDataHeader.get(2), Automotive);
        listDataChild.put(listDataHeader.get(3), Beauty);
        listDataChild.put(listDataHeader.get(4), Capital);
        listDataChild.put(listDataHeader.get(5), Domestic);
        listDataChild.put(listDataHeader.get(6), Earthmoving);
        listDataChild.put(listDataHeader.get(7), Electronics);
        listDataChild.put(listDataHeader.get(8), Food);
        listDataChild.put(listDataHeader.get(9), Furniture);
        listDataChild.put(listDataHeader.get(10), Gems);
        listDataChild.put(listDataHeader.get(11), Green);
        listDataChild.put(listDataHeader.get(12), Handicrafts);
        listDataChild.put(listDataHeader.get(13), Healthcare);
        listDataChild.put(listDataHeader.get(14), Iron);

    }


    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Activity _context;
        private List<String> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, List<String>> _listDataChild;

        public ExpandableListAdapter(Activity context, List<String> listDataHeader,
                                     HashMap<String, List<String>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final String childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item, null);
            }

            TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);

            txtListChild.setText(childText);
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group, null);
            }

            TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

}