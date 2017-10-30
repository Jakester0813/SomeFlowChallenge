package com.jakester.someflowchallenge.managers;

import android.content.Context;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.jakester.someflowchallenge.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jake on 10/29/2017.
 */

public class AnalyticsManager {

    private static AnalyticsManager mInstance;

    Context mContext;

    public static AnalyticsManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new AnalyticsManager(context);
        }
        return mInstance;
    }

    private AnalyticsManager(Context context){
        mContext = context;
    }

    public BarData getVerticalData(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 24f));
        entries.add(new BarEntry(1, 9.7f));
        entries.add(new BarEntry(2, 5.6f));
        entries.add(new BarEntry(3, 7.2f));
        entries.add(new BarEntry(4, 7.3f));
        entries.add(new BarEntry(5, 6.9f));
        entries.add(new BarEntry(6, 1.8f));



        BarDataSet dataset = new BarDataSet(entries, "Nintendo Switch sales globally (per 100,000 units sold)");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        return new BarData(dataset);

    }

    public BarData getHorizontalData(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 62f));
        entries.add(new BarEntry(1, 5f));
        entries.add(new BarEntry(2, 31f));

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(R.color.ps4);
        colors.add(R.color.nintendo_switch);
        colors.add(R.color.xbox_one);

        final ArrayList<String> horizontalLabels = new ArrayList<String>();
        horizontalLabels.add("PS4");
        horizontalLabels.add("Switch");
        horizontalLabels.add("XBox One");


        BarDataSet dataset = new BarDataSet(entries, "");
        dataset.setColors(colors);
        return new BarData(dataset);

    }

    public LineData generateLineData() {

        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();

        List<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(2f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));
        Collections.sort(entries, new EntryXComparator());

        LineDataSet ds1 = new LineDataSet(entries,"Dat Line");

        ds1.setLineWidth(2f);

        ds1.setDrawCircles(false);

        ds1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);

        // load DataSets from textfiles in assets folders
        sets.add(ds1);

        LineData d = new LineData(sets);
        return d;
    }

    public LineData generateTwoLineData() {

        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();

        List<Entry> entriesOne = new ArrayList<Entry>();
        entriesOne.add(new Entry(4f, 0));
        entriesOne.add(new Entry(8f, 1));
        entriesOne.add(new Entry(6f, 2));
        entriesOne.add(new Entry(2f, 3));
        entriesOne.add(new Entry(18f, 4));
        entriesOne.add(new Entry(9f, 5));
        Collections.sort(entriesOne, new EntryXComparator());

        List<Entry> entriesTwo = new ArrayList<Entry>();
        entriesTwo.add(new Entry(1f, 0));
        entriesTwo.add(new Entry(11f, 1));
        entriesTwo.add(new Entry(15f, 2));
        entriesTwo.add(new Entry(8f, 3));
        entriesTwo.add(new Entry(10f, 4));
        entriesTwo.add(new Entry(7f, 5));
        Collections.sort(entriesTwo, new EntryXComparator());

        LineDataSet ds1 = new LineDataSet(entriesOne,"Line Uno");
        LineDataSet ds2 = new LineDataSet(entriesTwo,"Line Dos");

        ds1.setLineWidth(2f);
        ds2.setLineWidth(2f);

        ds1.setDrawCircles(false);
        ds2.setDrawCircles(false);

        ds1.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);
        ds2.setColor(ColorTemplate.VORDIPLOM_COLORS[2]);

        // load DataSets from textfiles in assets folders
        sets.add(ds1);
        sets.add(ds2);

        LineData d = new LineData(sets);
        return d;
    }

    public PieData generatePieData() {

        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(70.0f, "Is Pacman"));
        entries.add(new PieEntry(30.0f, "Is Not Pacman"));

        PieDataSet set = new PieDataSet(entries, "Pac Man Data");
        set.setColors(new int[] { R.color.pacman, R.color.not_pacman}, mContext);
        return new PieData(set);
    }

}
