package edu.stanford.slac.powernetlab.Activities;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.stanford.slac.powernetlab.Fragments.AboutFragment;
import edu.stanford.slac.powernetlab.Fragments.AcFragment;
import edu.stanford.slac.powernetlab.Fragments.DishWasherFragment;
import edu.stanford.slac.powernetlab.Fragments.DryerFragment;
import edu.stanford.slac.powernetlab.Fragments.LandingFragment;
import edu.stanford.slac.powernetlab.Fragments.LightsFragment;
import edu.stanford.slac.powernetlab.Fragments.MicrowaveFragment;
import edu.stanford.slac.powernetlab.Fragments.StoveExhaustFragment;
import edu.stanford.slac.powernetlab.Fragments.PowerWallFragment;
import edu.stanford.slac.powernetlab.Fragments.RefrigeratorFragment;
import edu.stanford.slac.powernetlab.Fragments.SolarPanelsFragment;
import edu.stanford.slac.powernetlab.Fragments.StoveFragment;
import edu.stanford.slac.powernetlab.Fragments.WasherFragment;
import edu.stanford.slac.powernetlab.Fragments.WaterHeaterFragment;
import edu.stanford.slac.powernetlab.Model.model;
import edu.stanford.slac.powernetlab.R;
import edu.stanford.slac.powernetlab.Rest.ApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle mDrawerToggle;

    private final static String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "http://pwrnet-158117.appspot.com/api/v1/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Retrieve the AppCompact Toolbar
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//          Find Drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        mDrawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(mDrawerToggle);

        connectApitoGetData();


//        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rv_refrigerator);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));


////        set the padding to match the status bar height
//        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
////         create our manager instance after the content view is set
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
////         enable status bar tint
//        tintManager.setStatusBarTintEnabled(true);
////         enable navigation bar tint
//        tintManager.setNavigationBarTintEnabled(true);
////         set the transparent color of the status bar, 20% darker
//        tintManager.setTintColor(Color.parseColor("#20000000"));

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flContent, new LandingFragment());
        fragmentTransaction.commit();
    }

    private void connectApitoGetData() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiEndpointInterface endpointInterface = retrofit.create(ApiEndpointInterface.class);

        Call<model> call = endpointInterface.getData("5/");
        call.enqueue(new Callback<model>() {
            @Override
            public void onResponse(Call<model> call, Response<model> response) {
                int code = response.code();
                model model = response.body();
//                Log.d("HTTP CODE: ", String.valueOf(code));
//                Log.d("HTTP BODY: ", model.toString());
            }

            @Override
            public void onFailure(Call<model> call, Throwable t) {

            }
        });
        Log.d("API-Data", call.getClass().toString());


    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        selectDrawerItem(item);
                        return true;
                    }
                }
        );

    }


    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);

    }

    public void selectDrawerItem(MenuItem item) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        android.support.v4.app.Fragment fragment = null;
        Class fragmentClass = null;
        switch (item.getItemId()) {
            case R.id.nav_fragment_one:
                fragmentClass = RefrigeratorFragment.class;
                break;
            case R.id.nav_fragment_three:
                fragmentClass = DishWasherFragment.class;
                break;
            case R.id.nav_fragment_seven:
                fragmentClass = StoveFragment.class;
                break;
            case R.id.nav_fragment_eight:
                fragmentClass = StoveExhaustFragment.class;
                break;
            case R.id.nav_fragment_four:
                fragmentClass = DryerFragment.class;
                break;
            case R.id.nav_fragment_five:
                fragmentClass = WasherFragment.class;
                break;
            case R.id.nav_fragment_two:
                fragmentClass = PowerWallFragment.class;
                break;
            case R.id.nav_fragment_six:
                fragmentClass = AcFragment.class;
                break;
            case R.id.nav_fragment_nine:
                fragmentClass = WaterHeaterFragment.class;
                break;
            case R.id.nav_fragment_ten:
                fragmentClass = SolarPanelsFragment.class;
                break;
            case R.id.nav_fragment_eleven:
                fragmentClass = LightsFragment.class;
                break;
            case R.id.nav_fragment_twelve:
                fragmentClass = AboutFragment.class;
                break;
            case R.id.nav_microwave:
                fragmentClass = MicrowaveFragment.class;
                break;
        }
        try {
            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();


        //Highlight the seleceted item has been done by NavigationView
        item.setChecked(true);
        //Set Action Bar title
        setTitle(item.getTitle());
        //Close the navigation drawer
        mDrawer.closeDrawers();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE 1: Make sure to override the method with only a single `Bundle` argument
    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.
    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();

    }


    //        method to find height of the status bar
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


}
