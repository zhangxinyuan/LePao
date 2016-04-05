package com.project.graduation.jackben.pedometer.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.project.graduation.jackben.pedometer.R;
import com.project.graduation.jackben.pedometer.service.StepCountService;
import com.project.graduation.jackben.pedometer.utils.StepCountDetector;
import com.project.graduation.jackben.pedometer.view.ShowPercentView;

import java.util.ArrayList;

/**
 * 计步界面
 * User: XinYuan(1054344254@qq.com)
 * Date: 2015-10-30
 * Time: 12:35
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private View view;
    private FloatingActionButton fab;
    private ShowPercentView mCircleView;
    private TextView today_step_count;
    private TextView today_target;
    private TextView today_distance;
    private TextView today_calorie;
    private LineChart chart;
    private MThread mThread;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    int current_step_count = StepCountDetector.CURRENT_SETP;
                    mCircleView.setPercent(current_step_count);
                    today_step_count.setText("" + current_step_count);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        initChart();
        startStep();
        startThread();
        return view;
    }

    private void startThread() {
        if (mThread == null) {
            mThread = new MThread();
            mThread.start();
        }

    }

    /**
     * 初始化控件
     */
    private void initView() {
        mCircleView = (ShowPercentView) view.findViewById(R.id.myShowPercentView);
        today_step_count = (TextView) view.findViewById(R.id.today_step_count);
        today_target = (TextView) view.findViewById(R.id.today_target);
        today_distance = (TextView) view.findViewById(R.id.today_distance);
        today_calorie = (TextView) view.findViewById(R.id.today_calorie);
        chart = (LineChart) view.findViewById(R.id.home_line_chart);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    /**
     * 开启计步服务
     */
    private void startStep() {
        Intent intent = new Intent(getActivity(), StepCountService.class);
        getActivity().startService(intent);
    }


    /**
     * 开启线程去更新UI
     */
    private class MThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (StepCountService.FLAG) {
                    Message message = new Message();
                    message.what = 0;
                    mHandler.sendMessage(message);
                }


            }

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                //snackber可以替换toast
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
        }

    }


    /**
     * 初始化图表的属性
     */
    private void initChart() {
        // 定义字体为粗体
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Bold.ttf");
        LineData data = getData(48, 100);
        // 表的描述，会出现在表的右下角
        chart.setDescription("");
        chart.setNoDataTextDescription("没有数据");
        // 是否可触摸
        chart.setTouchEnabled(false);
        // 是否显示"网格"背景
        chart.setDrawGridBackground(false);
        //chart.setBackgroundColor(Color.rgb(137, 230, 81));
        // 是否可以拖动
        chart.setDragEnabled(false);
        // 是否可以手势缩放
        chart.setScaleEnabled(false);
        // Ture：缩放时X轴与Y轴一起变化。False：缩放时分别变化
        chart.setPinchZoom(false);
        // 设置字体样式
        chart.setDescriptionTypeface(type);

        // 设置X轴的属性
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(type);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        // 设置Y轴（左边）的属性
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setTypeface(type);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setDrawAxisLine(false);
        yAxisLeft.setLabelCount(2, false);
        yAxisLeft.setStartAtZero(false);
        yAxisLeft.setDrawLabels(true);
        //设置Y轴（右边）的属性
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawLabels(false);
        chart.setData(data);
        chart.animateX(560);
    }

    private LineData getData(int count, float range) {
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add(i + "");
        }
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            float mult = range + 1;
            float val = (float) (Math.random() * mult + 3);
            yVals.add(new Entry(val, i));
        }

        LineDataSet set1 = new LineDataSet(yVals, "活动状态");
        // 为线设置属性
        set1.setLineWidth(0.75f); // 线宽
        set1.setCircleSize(3f);// 显示的圆形大小
        set1.setColor(Color.GREEN);// 显示颜色
        set1.setDrawCircles(false);//不显示数值处的圆圈
        set1.setDrawValues(false);//不显示数值
        set1.setDrawCubic(true);//以函数形式输出
        set1.setDrawFilled(true);//填充
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1);
        LineData data = new LineData(xVals, dataSets);
        return data;
        // TODO Auto-generated method stub

    }
}
