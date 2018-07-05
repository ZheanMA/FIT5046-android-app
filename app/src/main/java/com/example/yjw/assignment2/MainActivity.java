package com.example.yjw.assignment2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    //private static String weatherKey = "1ed6eca3f52d92a2f80ea6e4416d7a01";
    //private static String weatherInvoke = "http://api.openweathermap.org/data/2.5/weather?zip=";// +&units=metric&appid=
   // private JSONArray currentWeather;

    private TextView resultTextView;
    private JsonObject resident;
    private Intent intent;
    private Bundle bundle;
    private GoogleMap mMap;
    private String location;
    private static String residentStr;
    private static String postcode;
    private static String temp;
    private static ArrayList<String> recordArray;
    protected static DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DBManager(this);
        this.recordArray = new ArrayList<String>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //get resident information from login activity
        Intent intent = getIntent();
        final Resident resident = intent.getParcelableExtra("user");
        residentStr = resident.toString();

        //create bundle and store useful information to send to fragments
        this.bundle = new Bundle();
        this.bundle.putString("resid",resident.getResId() + "");
        this.bundle.putString("firstName",resident.getFirstName());
        postcode = resident.getPostCode();
        this.bundle.putString("postCode",resident.getPostCode());
        this.bundle.putString("address",resident.getAddress());



        EleUsageGenerator.resetScheduleAtMidnight();
        EleUsageGenerator.generateEleHourly();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("smartER");
        Fragment iniFragment = new MainFragment();
        iniFragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, iniFragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment nextFragment = null;

        switch (id) {
            case R.id.nav_reports:
                nextFragment = new ReportsFragment();
                nextFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
                break;

            case R.id.nav_home:
                nextFragment = new MainFragment();
                nextFragment.setArguments(bundle);
                FragmentManager fragmentManager1 = getFragmentManager();
                fragmentManager1.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
                break;

            case R.id.nav_maps:
                nextFragment = new MapsFragment();
                nextFragment.setArguments(bundle);
                FragmentManager fragmentManager2 = getFragmentManager();
                fragmentManager2.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static String deQuoMark(String str){
        str = str.replaceAll("\"","");
        return str;
    }

    /**
     * Google Map API test
     * */
    //test start
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public static void deleteUsage(String id) {
        dbManager.deleteUsage(id);

        System.out.println("24 records has been deleted");
    }

    public static String readData(){
        Cursor c = dbManager.getAllUsages();
        String s="";
        if (c.moveToFirst()) {
            do {
                s="{\"resid\":" + c.getString(0) + "," + "\"dates\":" + "\"" + c.getString(1) + "\""
                        + "," + "\"hours\":" + c.getString(2) + "," + "\"fridgeusage\":" + c.getString(3)
                        + "," + "\"aircondusate\":" + c.getString(4) + "," + "\"washmachusage\":" + c.getString(5)
                        + "," + "\"temperature\":" + c.getString(6) + "}" ;
                recordArray.add(s);
                System.out.println("This round is " + s);
            } while (c.moveToNext());
        }

        return s;
    }




    static class SetEleUsage implements Runnable {
        @Override
        public void run() {
            EleUsageGenerator.generatorController(EleUsageGenerator.getHour());
            try {
                dbManager.open();
            }catch(SQLException e) {
                e.printStackTrace();
            }
            new AsyncTask<String, Void, String>(){
                @Override
                protected String doInBackground(String... params) {
                    String t = RestClient.getTemp(params[0]);
                    return t;
                }
                @Override
                protected void onPostExecute(String t){
                    temp = t;
                }
            }.execute(postcode);
            dbManager.insertUsage(residentStr,Time.getCurrentFormatedDate() + "T00:00:00+10:00",EleUsageGenerator.getHour() + "",EleUsageGenerator.getFridgeUsage(),EleUsageGenerator.getAircondusage(),EleUsageGenerator.getWashmachusage(),temp);
            if (EleUsageGenerator.getHour() == 0) {
                readData();
                    new AsyncTask<Void, Void, Void>() {

                        @Override
                        protected Void doInBackground(Void... params) {
                            RestClient.createUsage(recordArray);
                            recordArray.clear();
                            return null;
                        }
                    }.execute();
                deleteUsage(residentStr);
            }
            dbManager.close();
            EleUsageGenerator.hourIncrease();
        }
    }

    static class SetMidNight implements Runnable {
        @Override
        public void run() {
            EleUsageGenerator.setHour(0);
            EleUsageGenerator.setWashMachFlag(0);
            EleUsageGenerator.setWashMachCounter(0);

            System.out.println("Midnight is reset as " + EleUsageGenerator.getHour());
        }
    }

}
