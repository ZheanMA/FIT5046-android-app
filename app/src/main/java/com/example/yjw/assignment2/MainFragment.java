package com.example.yjw.assignment2;

import android.app.Fragment;
import android.database.Cursor;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by yjw on 2018/4/13.
 */

public class MainFragment extends Fragment{
    View vMain;

    private TextView firstName;
    private TextView postCode;
    private TextView temp;
    private static TextView fridgeUsage;
    private static TextView aircondUsage;
    private static TextView washMachUsage;
    private static TextView tvHour;
    private static TextView tvPeak;
    private static TextView tvInfo;
    private static int hour;
    protected static DBManager dbManager;
    private static Handler handler = null;
    private static String fUsage;
    private static String aUsage;
    private static String wUsage;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String pCode = "";

        vMain = inflater.inflate(R.layout.fragment_main, container, false);

        dbManager = new DBManager(getActivity());
        handler=new Handler();

        firstName = (TextView) vMain.findViewById(R.id.fName);
        postCode = (TextView) vMain.findViewById(R.id.pCode);
        temp = (TextView) vMain.findViewById(R.id.temp);
        fridgeUsage = (TextView) vMain.findViewById(R.id.tvFUsage);
        aircondUsage = (TextView) vMain.findViewById(R.id.tvAUsage);
        washMachUsage = (TextView) vMain.findViewById(R.id.tvWUsage);
        tvHour = (TextView) vMain.findViewById(R.id.tvHour);
        tvPeak = (TextView) vMain.findViewById(R.id.tvPeakHours);
        tvInfo = (TextView) vMain.findViewById(R.id.tvInfo);

        if (isAdded()) {
            String fName = getArguments().getString("firstName");
            pCode = getArguments().getString("postCode");
            String subTemp = getArguments().getString("temp");
            firstName.setText(fName + "!");
            postCode.setText(pCode + ") is: ");
        }
        refreshUsage();

        new AsyncTask<String, Void, String>(){
            @Override
            protected String doInBackground(String... params) {

                String t = RestClient.getTemp(params[0]);
                return t;
            }
            @Override
            protected void onPostExecute(String t) {

                temp.setText(t + " ℃");
            }
        }.execute(new String [] {pCode});
        return vMain;
    }

    public static void refreshUsage() {
        ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
        //Run each 60 minutes (1 hour), delay 1 minute for waiting new hourly record generated.
        schedule.scheduleAtFixedRate(new SetHourlyUsage(),1,60, TimeUnit.MINUTES);
        /**For Testing: Cut the Time Scale.*/
        //schedule.scheduleAtFixedRate(new SetHourlyUsage(),0,1, TimeUnit.SECONDS);
    }

    protected static class SetHourlyUsage extends TimerTask {
        @Override
        public void run() {
            try {
                dbManager.open();
                hour = EleUsageGenerator.getHour() - 1;
                if (hour < 0)
                    hour = 0;
                Cursor c = dbManager.getUser(hour + "");
                System.out.println("This round is " + hour);
                fUsage = c.getString(3);
                aUsage = c.getString(4);
                wUsage = c.getString(5);
                System.out.println("Fridge is " + fUsage);
                handler.post(runnableUI);

                //MainFragment.hourIncre();
                System.out.println("This round is " + hour);
            } catch (Exception e) {
                e.printStackTrace();
            }
            dbManager.close();
        }
    }

   static Runnable runnableUI = new Runnable() {

        @Override
        public void run() {
            tvHour.setText(hour + "");
            fridgeUsage.setText(fUsage);
            aircondUsage.setText(aUsage);
            washMachUsage.setText(wUsage);
            double hourlyUsage = Double.parseDouble(fUsage) + Double.parseDouble(aUsage) + Double.parseDouble(wUsage);
            DecimalFormat df = new DecimalFormat("####0.0");

            if (hour < 23 && hour > 8) {
                tvPeak.setText("Now is peak hour.");
                if (hourlyUsage >= 1.5) {
                    tvInfo.setText(df.format(hourlyUsage) + "kWh. Over Usage Thredhold :(");
                    tvInfo.setTextColor(0xFFCC0000);
                } else {
                    tvInfo.setText(df.format(hourlyUsage) + "kWh. Saving Electricity ;)");
                    tvInfo.setTextColor(0xFF99CC00);
                }
            } else {
                tvPeak.setText("Not Peak Hours");
                tvInfo.setText("");
            }

        }
    };

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public static int hourIncre() {
        return hour ++;
    }



}
