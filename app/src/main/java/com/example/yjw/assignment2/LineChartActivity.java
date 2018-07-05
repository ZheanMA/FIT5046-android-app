package com.example.yjw.assignment2;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

/**
 * Created by yjw on 2018/4/29.
 */


public class LineChartActivity extends AppCompatActivity {
    private ArrayList<Integer> position;
    private ArrayList<Double> temp;
    private ArrayList<Double> dailyTemp;
    private ArrayList<Double> hourly;
    private ArrayList<Double> daily;
    private static String resid;
    private static String today;
    private static String yesterday;
    private static String befYesterday;
    private static String type;
    private ArrayList<Double> perUsage;
    private static GraphView graph;
    private Spinner spType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linechart);
        Intent intent = getIntent();
        resid = intent.getStringExtra("resid");

        graph = (GraphView) findViewById(R.id.lineChart);
        spType = (Spinner) findViewById(R.id.spType);

        temp = new ArrayList<Double>();
        hourly = new ArrayList<Double>();
        daily = new ArrayList<Double>();
        dailyTemp = new ArrayList<Double>();

        today = Time.getCurrentFormatedDate();
        yesterday = Time.getYesterdayFormatedDate();
        befYesterday = Time.getDayBeforeYesterdayFormatedDate();

        new AsyncTask<String,Void,JsonArray>() {

            @Override
            protected JsonArray doInBackground(String... params) {
                String str = RestClient.getHourlyDailyUsages(params[0],params[1],params[2]);
                JsonArray usages = new JsonParser().parse(str).getAsJsonArray();

                return usages;
            }
            @Override
            protected void onPostExecute(JsonArray usages) {
                for (int i = 0; i < usages.size(); i ++) {
                    temp.add(Double.parseDouble(RestClient.deQuoMark(usages.get(i).getAsJsonObject().get("temperature").getAsString())));
                    hourly.add(Double.parseDouble(RestClient.deQuoMark(usages.get(i).getAsJsonObject().get("totalUsage").getAsString())));
                }
            }
        }.execute(new String[]{resid + "",yesterday, "hourly"});

        new AsyncTask<String,Void,ArrayList<JsonArray>>() {

            @Override
            protected ArrayList<JsonArray> doInBackground(String... params) {
                String bstr = RestClient.getHourlyDailyUsages(params[0],params[1],params[4]);
                String ystr = RestClient.getHourlyDailyUsages(params[0],params[2],params[4]);
                String tstr = RestClient.getHourlyDailyUsages(params[0],params[3],params[4]);
                ArrayList<JsonArray> dailyUsage = new ArrayList<JsonArray>();
                JsonArray busages = new JsonParser().parse(bstr).getAsJsonArray();
                dailyUsage.add(busages);
                JsonArray yusages = new JsonParser().parse(ystr).getAsJsonArray();
                dailyUsage.add(yusages);
                JsonArray tusages = new JsonParser().parse(tstr).getAsJsonArray();
                dailyUsage.add(tusages);

                return dailyUsage;
            }
            @Override
            protected void onPostExecute(ArrayList<JsonArray> dailyUsage) {
                for (int i = 0; i < dailyUsage.size(); i ++) {
                    dailyTemp.add(Double.parseDouble(RestClient.deQuoMark(dailyUsage.get(i).get(0).getAsJsonObject().get("avgTemp").getAsString())));
                    daily.add(Double.parseDouble(RestClient.deQuoMark(dailyUsage.get(i).get(0).getAsJsonObject().get("totalUsage").getAsString())));
                }
            }
        }.execute(new String[]{resid + "",befYesterday,yesterday,today,"daily"});


        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = spType.getSelectedItem().toString();

              if (type.equals("Hourly")) {
                  LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                  LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>();

                  for (int j = 0; j < hourly.size(); j ++) {
                      series.appendData(new DataPoint(j,hourly.get(j)),true,24);
                      series2.appendData(new DataPoint(j,temp.get(j)),true,24);
                  }
                  graph.setTitle("Hourly Usage Chart");
                  graph.addSeries(series);
                  // set second scale
                  graph.getSecondScale().addSeries(series2);
                  // the y bounds are always manual for second scale
                  graph.getSecondScale().setMinY(0);
                  StaticLabelsFormatter label = new StaticLabelsFormatter(graph);
                  label.setHorizontalLabels(new String[] {"0","1","2","3","4","5","6","7","8","9","10","11","12","13"
                          ,"14","15","16","17","18","19","20","21","22","23"});
                  graph.getGridLabelRenderer().setLabelFormatter(label);
                  graph.getSecondScale().setMaxY(30);
                  series2.setColor(Color.RED);
              } else if (type.equals("Daily")) {
                  LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                  LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>();

                  for (int j = 0; j < daily.size(); j ++) {
                      series.appendData(new DataPoint(j,daily.get(j)),true,4);
                      series2.appendData(new DataPoint(j,dailyTemp.get(j)),true,4);
                  }
                  graph.setTitle("Daily Usage Chart");
                  graph.addSeries(series);
                  // set second scale
                  graph.getSecondScale().addSeries(series2);
                  // the y bounds are always manual for second scale
                  graph.getSecondScale().setMinY(0);
                  StaticLabelsFormatter label = new StaticLabelsFormatter(graph);
                  label.setHorizontalLabels(new String[] {befYesterday,yesterday,today});
                  graph.getGridLabelRenderer().setLabelFormatter(label);
                  graph.getSecondScale().setMaxY(20);
                  series2.setColor(Color.RED);
              }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }
}
