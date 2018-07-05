package com.example.yjw.assignment2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity implements OnChartValueSelectedListener{

    private PieChart mChart;
    private String[] title = {"Fridge Usage", "Air-conditioner Usage","Washing machine Usage"};
    private double[] usageRate = {10,10,10};
    private String date;
    private String resid;
    private JsonArray usages;
    private int times = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_piechart);
        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        resid = intent.getStringExtra("resid");
        
        new AsyncTask<String,Void,Void>(){

            @Override
            protected Void doInBackground(String... params) {
                String str = RestClient.getDailyUsageByResidAndDate(params[0],params[1]);
                if (!str.equals("[]")) {
                    usages = new JsonParser().parse(str).getAsJsonArray();
                    usageRate = new double[]{usages.get(0).getAsJsonObject().get("fridge").getAsDouble()
                            , usages.get(0).getAsJsonObject().get("airconditioner").getAsDouble()
                            , usages.get(0).getAsJsonObject().get("wash Machine").getAsDouble()};
                    mChart.setCenterText(date + "\n\nDaily Usasge");
                } else {
                    mChart.setCenterText(date + "\n\nNo Daily Usasge");
                    //usages.add(null);
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void param) {

                setData(title.length, 100);
            }
        }.execute(new String[] {resid, date});


        
        setTitle("Daily Electricity Usage");

//        Intent intent = getIntent();
//        units = intent.getStringArrayExtra("units");
//        usageRate = intent.getIntArrayExtra("usageRate");


        for (double i: usageRate) {
            times += i;
        }

        mChart = (PieChart) findViewById(R.id.pie_chart);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        //mChart.setCenterText(date + "\n\nDaily Usasge");
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setHoleRadius(50f);
        mChart.setTransparentCircleRadius(61f);
        mChart.setDrawCenterText(true);
        mChart.setHighlightPerTapEnabled(true);

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e == null)
                    return;
                Log.i("VAL SELECTED",
                        "Value: " + e.getY() + ", index: " + h.getX()
                                + ", DataSet index: " + h.getDataSetIndex());
            }

            @Override
            public void onNothingSelected() {}
        });



        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        mChart.setEntryLabelColor(Color.BLACK);
        mChart.setEntryLabelTextSize(12f);
    }


    private void setData(int count, float range) {


        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        for (int i = 0; i < title.length; i++) {
            entries.add(new PieEntry((float) usageRate[i]/times, title[i]));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        mChart.setData(data);
        mChart.highlightValues(null);
        mChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }
}
