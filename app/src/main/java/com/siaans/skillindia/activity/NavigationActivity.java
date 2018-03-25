package com.siaans.skillindia.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.siaans.skillindia.ExDialog;
import com.siaans.skillindia.LoginActivity;
import com.siaans.skillindia.R;
import com.siaans.skillindia.Reg_Activity;
import com.siaans.skillindia.Registerpage1Activity;
import com.siaans.skillindia.TCloginActivity;
import com.siaans.skillindia.fragment.CourseFragment;
import com.siaans.skillindia.fragment.HomeFragment;
import com.siaans.skillindia.fragment.NewsFragment;

public class NavigationActivity extends AppCompatActivity {
    public SharedPreferences.Editor loginPrefsEditor;
    public  SharedPreferences loginPreferences;
    private Boolean saveLogin;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    public static int navItemIndex = 0;

    private static final String TAG_HOME = "home";
    private static final String TAG_NEWS = "news";
    private static final String TAG_APPLY = "apply";
    private static final String TAG_LOGIN = "login";
    private static final String TAG_TC = "tc";
    private static final String TAG_COURSE = "course";
    private static final String TAG_FIND = "find";
    public static String CURRENT_TAG = TAG_HOME;




    private String[] activityTitles;

    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);



        if (saveLogin == true) {
            String type = loginPreferences.getString("lgn", String.valueOf(true));
            if(type.equals("Trainee")) {
                Intent n = new Intent(NavigationActivity.this, TraineeNavActivity.class);
                startActivity(n);
                finish();
            }else{

                Intent n = new Intent(NavigationActivity.this, TCNavActivity.class);
                startActivity(n);
                finish();
            }

        }
        setContentView(R.layout.activity_navigation);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mHandler = new Handler();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);


        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogue();
            }
        });

        loadNavHeader();
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

    }

    public void openDialogue(){
        ExDialog exDialog=new ExDialog();
        exDialog.show(getSupportFragmentManager(),"example dialog");
    }
    private void loadNavHeader() {
        // name, website


        // loading header background image


        // showing dot next to notifications label
        navigationView.getMenu().getItem(1).setActionView(R.layout.menu_dot);
    }
    private void loadHomeFragment() {
        selectNavMenu();
        setToolbarTitle();

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            toggleFab();
            return;
        }
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                if(fragment!=null){
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
                }
            }
        };
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
        toggleFab();
        drawer.closeDrawers();
        invalidateOptionsMenu();
    }
    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                NewsFragment newsFragment = new NewsFragment();
                return newsFragment;
            case 2:
                Intent intent =new Intent(NavigationActivity.this,Reg_Activity.class);
                startActivity(intent);
                navItemIndex=0;
                CURRENT_TAG=TAG_HOME;
                loadHomeFragment();
                return null;
            case 3:
                Intent intent1 =new Intent(NavigationActivity.this,LoginActivity.class);
                startActivity(intent1);
                navItemIndex=0;
                CURRENT_TAG=TAG_HOME;
                loadHomeFragment();
                return null;
            case 4:
                Intent intent2 =new Intent(NavigationActivity.this, TCloginActivity.class);
                startActivity(intent2);
                navItemIndex=0;
                CURRENT_TAG=TAG_HOME;
                loadHomeFragment();
                return null;
            case 5:
                CourseFragment courseFragment = new CourseFragment();
                return courseFragment;

            case 6:
                Intent intent3 =new Intent(NavigationActivity.this, Registerpage1Activity.class);
                startActivity(intent3);
                navItemIndex=0;
                CURRENT_TAG=TAG_HOME;
                loadHomeFragment();
                return null;

            default:
                return new HomeFragment();
        }

    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_news:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_NEWS;
                        break;
                    case R.id.nav_apply_for_trainee:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_APPLY;
                        break;
                    case R.id.login:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_LOGIN;
                        break;
                    case R.id.TC:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_TC;
                        break;
                    case R.id.courselist:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_COURSE;
                        break;
                    case R.id.findTC:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_FIND;
                        break;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }


    private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }
}
