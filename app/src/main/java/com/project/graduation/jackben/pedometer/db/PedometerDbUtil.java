package com.project.graduation.jackben.pedometer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.project.graduation.jackben.pedometer.beans.UserBean;

/**
 * User: XinYuan(1054344254@qq.com)
 * Date: 2015-11-20
 * Time: 18:06
 */
public class PedometerDbUtil {
    private SQLiteDatabase db;
    private static PedometerDbUtil pedometerDbUtil;
    private static final String USER_TABLE_NAME = "user";
    private static final String STEP_TABLE_NAME = "step";

    private PedometerDbUtil(Context context) {
        PedometerOpenHelper pedometerOpenHelper = new PedometerOpenHelper(context);
        db = pedometerOpenHelper.getWritableDatabase();
    }

    /**
     * 单例模式获取PedometerDbUtil对象
     *
     * @param contefxt
     * @return
     */
    public synchronized static PedometerDbUtil getInstance(Context context) {

        if (pedometerDbUtil == null) {
            pedometerDbUtil = new PedometerDbUtil(context);
        }
        return pedometerDbUtil;
    }

    /**
     * 插入一条用户数据
     *
     * @param userBean
     */
    private void insertUser(UserBean userBean) {
        if (userBean != null) {
            ContentValues valus = new ContentValues();
            valus.put("userId", userBean.getUserId());
            valus.put("userName", userBean.getUserName());
            valus.put("userSex", userBean.getUserSex());
            valus.put("userPic", userBean.getUserPic());
            valus.put("userWeight", userBean.getUserWeight());
            valus.put("userHeight", userBean.getUserHeight());
            valus.put("userSensitivity", userBean.getUserSensitivity());
            db.insert(USER_TABLE_NAME, null, valus);
        }
    }


}
