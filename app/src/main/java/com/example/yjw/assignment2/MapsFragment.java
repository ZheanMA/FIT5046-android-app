package com.example.yjw.assignment2;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;

/**
 * Created by yjw on 2018/4/15.
 */

public class MapsFragment extends Fragment implements OnMapReadyCallback{
    private MapView mMap;
    private GoogleMap gMap;
    private View vMap;
    private String resid;
    private String yesterday;
    private String userAddress;
    private String userPostCode;
    private String dailyUsage;
    private String[] location;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        vMap = inflater.inflate(R.layout.fragment_maps, container, false);
        yesterday = Time.getYesterdayFormatedDate();
        setUpMapIfNeeded();
        if (isAdded()) {
            this.resid = getArguments().getString("resid");
            this.userAddress = getArguments().getString("address");
            this.userPostCode = getArguments().getString("postCode");
        }

        new AsyncTask<String, Void, JsonArray>() {

            @Override
            protected JsonArray doInBackground(String... params) {
                String ystr = RestClient.getHourlyDailyUsages(params[0],params[1],params[2]);
                JsonArray yusages = new JsonParser().parse(ystr).getAsJsonArray();
                return yusages;
            }
            @Override
            protected  void onPostExecute(JsonArray yusages) {
                dailyUsage = yusages.get(0).getAsJsonObject().get("totalUsage").getAsString();
            }
        }.execute(new String[] {resid, yesterday,"daily"});

        return vMap;
    }

    private void setUpMapIfNeeded() {
        if (gMap == null) {
            MapFragment mapFrag = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFrag.getMapAsync(this);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        new AsyncTask<String, Void, String[]>(){
            @Override
            protected String[] doInBackground(String... params) {
                String[] loc = {};
                loc = RestClient.getPositionByAddress(userAddress + " " + userPostCode);
                return loc;
            }
            @Override
            protected void onPostExecute(String[] loc){
                location = loc;
                LatLng house = new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1]));
                gMap.addMarker(new MarkerOptions().position(house).title("Daily Usage " + dailyUsage));
                gMap.moveCamera(CameraUpdateFactory.newLatLng(house));
            }
        }.execute(new String[] {userAddress});
    }
}
