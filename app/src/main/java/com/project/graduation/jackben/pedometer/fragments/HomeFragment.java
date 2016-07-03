package com.project.graduation.jackben.pedometer.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.project.graduation.jackben.pedometer.R;
import com.project.graduation.jackben.pedometer.beans.StepBean;
import com.project.graduation.jackben.pedometer.beans.UserBean;
import com.project.graduation.jackben.pedometer.db.PedometerDbUtil;
import com.project.graduation.jackben.pedometer.service.StepCountService;
import com.project.graduation.jackben.pedometer.utils.StepCountDetector;
import com.project.graduation.jackben.pedometer.view.ShowPercentView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 计步界面
 * User: XinYuan(1054344254@qq.com)
 * Date: 2015-10-30
 * Time: 12:35
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "HomeFragment";
    private static HomeFragment mHomeFragment = null;
    private View view;
    private FloatingActionButton fab;
    private ShowPercentView mCircleView;
    private TextView today_step_count;
    private TextView today_target;
    private TextView today_distance;
    private TextView today_calorie;
    private LineChart chart;
    private MThread mThread;
    private int current_step_count;
    private PedometerDbUtil dbUtils;
    private UserBean userBean;
    private String date;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mCircleView.setPercent(current_step_count);
                    today_step_count.setText(String.valueOf(current_step_count));
                    today_distance.setText(String.valueOf(current_step_count * userBean.getUserStepLength()));
                    today_calorie.setText(String.valueOf((current_step_count * userBean.getUserStepLength() * userBean.getUserWeight() * 0.01 * 0.01) / 200000));
                    break;
            }
        }
    };

    public static HomeFragment getInstance() {
        if (mHomeFragment == null) {
            synchronized (HomeFragment.class) {
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                }
            }
        }
        return mHomeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        initDB();
        initChart();
        startStep();
        startThread();
        return view;
    }

    private void initDB() {
        date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        dbUtils = PedometerDbUtil.getInstance(getActivity().getApplicationContext());
        userBean = dbUtils.queryUserInfo();
        StepBean stepBean = dbUtils.getStepByDate(userBean.getUserId(), date);
        if (stepBean != null) {
            StepCountDetector.CURRENT_SETP = stepBean.getStepCount() == null ? 0 : stepBean.getStepCount();
        }
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
                current_step_count = StepCountDetector.CURRENT_SETP;
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
                //分享截屏
                shareScreen();
                break;
        }

    }

    /***
     * 截屏分享
     */
    private void shareScreen() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.US);
        String filePath = "/sdcard/" + sdf.format(new Date()) + ".png";
        getScreen(filePath);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        File image = new File(filePath);
        if (image != null && image.exists() && image.isFile()) {
            shareIntent.setType("image/png");
            Uri uri = Uri.fromFile(image);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        }
        startActivity(Intent.createChooser(shareIntent, "分享到"));


    }

    /**
     * 截屏
     * @param filePath
     */
    private void getScreen(String filePath) {
        View view = getActivity().getWindow().getDecorView().getRootView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        if (bitmap != null) {
            try {
                FileOutputStream out = new FileOutputStream(filePath);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "出错了！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "出错了！", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
        saveStepData();

        super.onPause();
    }

    private void saveStepData() {
        //退出时保存当前步数
        StepBean stepBean = new StepBean();
        stepBean.setUserId(userBean.getUserId());
        stepBean.setDate(date);
        stepBean.setStepConut(current_step_count);
        StepBean stepByDate = dbUtils.getStepByDate(userBean.getUserId(), date);
        if (stepByDate == null) {
            dbUtils.insertStep(stepBean);
        }
        dbUtils.updateStep(stepBean);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
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
        yAxisLeft.setAxisMinValue(0f);
        yAxisLeft.setDrawLabels(true);
        //设置Y轴（右边）的属性
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawLabels(false);
        chart.setData(data);
        chart.animateX(560);
    }

    private LineData getData(int count, float range) {
        List<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add(i + "");
        }
        List<Entry> yVals = new ArrayList<Entry>();
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
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1);
        LineData data = new LineData(xVals, dataSets);
        return data;
        // TODO Auto-generated method stub

    }
}
