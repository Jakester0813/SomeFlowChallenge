package com.jakester.someflowchallenge.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.github.mikephil.charting.utils.FileUtils;
import com.jakester.someflowchallenge.R;
import com.jakester.someflowchallenge.managers.AnalyticsManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnalyticsActivity extends AppCompatActivity{

    Toolbar toolbar;

    private BarChart mHorizontalChart, mVerticalChart;

    private LineChart mOneLineChart, mTwoLineChart;

    private PieChart mPieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Analytics");
        setSupportActionBar(toolbar);

        // create vertical Chart
        mVerticalChart = (BarChart) findViewById(R.id.bar_chart_vertical);
        setupVerticalChart();

        //Create Horizontal chart
        mHorizontalChart = (HorizontalBarChart) findViewById(R.id.bar_chart_horizontal);
        setupHorizontalChart();

        //One line chart
        mOneLineChart = (LineChart) findViewById(R.id.line_chart);
        setupOneLineChart();


        //Two line chart
        mTwoLineChart = (LineChart) findViewById(R.id.line_chart_duo);
        setupTwoLineChart();

        //Pie Chart
        mPieChart = (PieChart) findViewById(R.id.pie_chart);
        setupPieChart();
    }

    public void setupVerticalChart(){
        final ArrayList<String> verticalLabels = new ArrayList<String>();
        verticalLabels.add("March");
        verticalLabels.add("April");
        verticalLabels.add("May");
        verticalLabels.add("June");
        verticalLabels.add("July");
        verticalLabels.add("August");
        verticalLabels.add("September");
        mVerticalChart.getDescription().setEnabled(false);

        mVerticalChart.setDrawGridBackground(false);
        mVerticalChart.setDrawBarShadow(false);

        BarChart chartVert = new BarChart(this);


        BarData verticalData = AnalyticsManager.getInstance(this).getVerticalData();
        chartVert.setData(verticalData);

        mVerticalChart.setData(verticalData);

        YAxis leftAxis = mVerticalChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        mVerticalChart.getAxisRight().setEnabled(false);

        XAxis xAxis = mVerticalChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return verticalLabels.get((int)value);
            }
        });
    }

    public void setupHorizontalChart(){
        mHorizontalChart.getDescription().setEnabled(false);

        mHorizontalChart.setDrawGridBackground(false);
        mHorizontalChart.setDrawBarShadow(false);

        BarChart chartHorizontal= new BarChart(this);

        BarData horizontalData = AnalyticsManager.getInstance(this).getHorizontalData();
        chartHorizontal.setData(horizontalData);

        mHorizontalChart.setData(horizontalData);

        YAxis horizontalLeftAxis = mHorizontalChart.getAxisLeft();
        horizontalLeftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        mHorizontalChart.getAxisRight().setEnabled(false);

        XAxis horizontalXAxis = mHorizontalChart.getXAxis();
        horizontalXAxis.setEnabled(true);
    }

    public void setupOneLineChart(){
        mOneLineChart.getDescription().setEnabled(false);

        mOneLineChart.setDrawGridBackground(false);

        mOneLineChart.setData(AnalyticsManager.getInstance(this).generateLineData());

        mOneLineChart.getAxisRight().setEnabled(false);

        XAxis oneXAxis = mOneLineChart.getXAxis();
        oneXAxis.setEnabled(false);
    }

    public void setupTwoLineChart(){
        mTwoLineChart.getDescription().setEnabled(false);

        mTwoLineChart.setDrawGridBackground(false);

        mTwoLineChart.setData(AnalyticsManager.getInstance(this).generateTwoLineData());

        mTwoLineChart.getAxisRight().setEnabled(false);

        XAxis twoXAxis = mTwoLineChart.getXAxis();
        twoXAxis.setEnabled(false);
    }

    public void setupPieChart(){
        mPieChart.getDescription().setEnabled(false);

        // radius of the center hole in percent of maximum radius
        mPieChart.setHoleRadius(0f);
        mPieChart.setTransparentCircleRadius(0f);

        mPieChart.setData(AnalyticsManager.getInstance(this).generatePieData());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.chat_menu) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_analytics, menu);
        return true;
    }
}
