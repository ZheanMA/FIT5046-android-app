package com.example.yjw.assignment2;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Date;

/**
 * Created by yjw on 2018/4/14.
 */

public class ReportsFragment extends Fragment{
    View vDisplayUnit;
    private Button btPie;
    private Button btLine;
    private int year = 2018;
    private int month = 3;
    private int day = 1;
    private String datePicked;
    private String resid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vDisplayUnit = inflater.inflate(R.layout.fragment_reports, container, false);
        btPie = (Button) vDisplayUnit.findViewById(R.id.bnPieChart);
        btLine = (Button) vDisplayUnit.findViewById(R.id.bnLineChart);

        if (isAdded()) {
            this.resid = getArguments().getString("resid");
        }
        btPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), dateListener, year, month, day).show();
            }
        });

        btLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(vDisplayUnit.getContext(), LineChartActivity.class);
                intent.putExtra("resid", resid);

                startActivity(intent);
            }
        });
        return vDisplayUnit;
    }

    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int newYear, int newMonth, int newDate) {
            year = newYear;
            month = newMonth;
            day = newDate;
            String displayDate = day + "/" + Integer.toString(month + 1) + "/" + year;
            //Date temp = Time.toDate(displayDate, "dd/MM/yyyy");
            String strDay = "";
            String strMonth = "";
            if (day < 10)
                strDay = "0" + day;
            else
                strDay = day + "";
            if (month + 1 < 10)
                strMonth = "0" + Integer.toString(month + 1);
            else
                strMonth = month + "";

            datePicked = year + "-" + strMonth + "-" + strDay;// + "T00:00:00+10:00";

            Intent intent = new Intent(vDisplayUnit.getContext(), PieChartActivity.class);
            intent.putExtra("date", datePicked);
            intent.putExtra("resid",resid);

            startActivity(intent);
        }
    };

}
