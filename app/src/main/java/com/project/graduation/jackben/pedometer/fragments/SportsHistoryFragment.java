package com.project.graduation.jackben.pedometer.fragments;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.project.graduation.jackben.pedometer.R;

import java.util.ArrayList;

/**
 * User: XinYuan(1054344254@qq.com)
 * Date: 2015-10-30
 * Time: 12:10
 */
public class SportsHistoryFragment extends Fragment {
    private View view;
    private BarChart historyBarChart;
    private Typeface mTf;
    private static SportsHistoryFragment mSportsHistoryFragment = null;

    public static SportsHistoryFragment getInstance() {
        if (mSportsHistoryFragment == null) {
            synchronized (SportsHistoryFragment.class) {
                if (mSportsHistoryFragment == null) {
                    mSportsHistoryFragment = new SportsHistoryFragment();
                }
            }
        }
        return mSportsHistoryFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sporthistory, container, false);
        historyBarChart = (BarChart) view.findViewById(R.id.history_bar_chart);
        initChart();
        return view;

    }

    private void initChart() {
        historyBarChart.setDrawBarShadow(false);
        historyBarChart.setDrawValueAboveBar(true);
        historyBarChart.setDescription("");
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        historyBarChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        historyBarChart.setPinchZoom(false);
        // 是否可以拖动
        historyBarChart.setDragEnabled(false);
        // 是否可以手势缩放
        historyBarChart.setScaleEnabled(false);
        historyBarChart.setDrawGridBackground(false);

        // mChart.setDrawYLabels(false);
        mTf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        XAxis xAxis = historyBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setSpaceBetweenLabels(2);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = historyBarChart.getAxisLeft();
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLabels(false);


        YAxis rightAxis = historyBarChart.getAxisRight();
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawLabels(false);

        Legend l = historyBarChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

        // set data

        setData(7, 50);

        // do not forget to refresh the chart
//            holder.chart.invalidate();
        historyBarChart.animateY(700, Easing.EasingOption.EaseInCubic);

    }

    private void setData(int count, float range) {
        String[] mMonths = new String[]{
                "周一", "周二", "周三", "周四", "周五", "周六", "周日"
        };

        //X轴数据
        ArrayList<String> xVals = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            xVals.add(mMonths[i]);
        }

        //Y轴数据
        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);
            yVals1.add(new BarEntry(val, i));
        }

        BarDataSet set1;

//        if (historyBarChart.getData() != null &&
//                historyBarChart.getData().getDataSetCount() > 0) {
//            set1 = (BarDataSet)historyBarChart.getData().getDataSetByIndex(0);
//            set1.set(yVals1);
//            historyBarChart.getData().setXVals(xVals);
//            historyBarChart.getData().notifyDataChanged();
//            historyBarChart.notifyDataSetChanged();
//        } else {
        set1 = new BarDataSet(yVals1, "DataSet");
        set1.setBarSpacePercent(35f);
        set1.setColors(ColorTemplate.JOYFUL_COLORS);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        data.setValueTypeface(mTf);

        historyBarChart.setData(data);
//        }
    }


}
