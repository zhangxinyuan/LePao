package com.project.graduation.jackben.pedometer.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;

import com.project.graduation.jackben.pedometer.utils.StepCountDetector;

/**
 * 运行在后台的计步服务
 */
public class StepCountService extends Service {
    public static boolean FLAG = false;
    private StepCountDetector stepCountDetector;// 计步器
    private SensorManager sensorManager;
    private Sensor defaultSensor;

    public StepCountService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        new Thread() {
            @Override
            public void run() {
                super.run();
                initDetector();

            }


        }.start();

    }

    private void initDetector() {
        FLAG = true;
        stepCountDetector = new StepCountDetector(getApplicationContext());
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);// 得到传感器管理者
        defaultSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);// 得到加速传感器
        sensorManager.registerListener(stepCountDetector, defaultSensor, SensorManager.SENSOR_DELAY_NORMAL);// 对得到的传感器进行注册并实行监听，如果传感器可用，则返回TRUE
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        FLAG = false;
        if (stepCountDetector != null) {
            sensorManager.unregisterListener(stepCountDetector);// 移除注册
        }
    }
}
