package com.project.graduation.jackben.pedometer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.project.graduation.jackben.pedometer.beans.StepBean;
import com.project.graduation.jackben.pedometer.beans.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * User: XinYuan(1054344254@qq.com)
 * Date: 2015-11-20
 * Time: 18:06
 */
public class PedometerDbUtil {
    private static final String TAG = "PedometerDbUtil";
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
     * @param context
     * @return
     */
    public static PedometerDbUtil getInstance(Context context) {

        if (pedometerDbUtil == null) {
            synchronized (PedometerDbUtil.class) {
                if (pedometerDbUtil == null) {
                    pedometerDbUtil = new PedometerDbUtil(context);
                }
            }

        }
        return pedometerDbUtil;
    }

    /**
     * 插入一条用户个人数据
     *
     * @param userBean
     */
    public void insertUserInfo(UserBean userBean) {
        if (userBean != null) {
            ContentValues valus = new ContentValues();
            valus.put("userId", userBean.getUserId());
            valus.put("userName", userBean.getUserName());
            valus.put("userSex", userBean.getUserSex());
            valus.put("userPic", userBean.getUserPic());
            valus.put("userWeight", userBean.getUserWeight());
            valus.put("userHeight", userBean.getUserHeight());
            valus.put("userSensitivity", userBean.getUserSensitivity());
            valus.put("userStepLength", userBean.getUserStepLength());
            db.insert(USER_TABLE_NAME, null, valus);
        }
    }

    /**
     * 更新个人数据
     *
     * @param user
     */
    public void updateUserInfo(UserBean user) {
        if (user != null) {
            ContentValues values = new ContentValues();
            values.put("userId", user.getUserId());
            values.put("userName", user.getUserName());
            values.put("userSex", user.getUserSex());
            values.put("userPic", user.getUserPic());
            values.put("userWeight", user.getUserWeight());
            values.put("userHeight", user.getUserHeight());
            values.put("userSensitivity", user.getUserSensitivity());
            values.put("userStepLength", user.getUserStepLength());
            db.update("user", values, "userId = ?",
                    new String[]{user.getUserId()});
        }
    }

    public void updateUserName(String userId, String name) {
        if (userId != null) {
            ContentValues values = new ContentValues();
            values.put("userName", name);
            db.update("user", values, "userId = ?",
                    new String[]{userId});
        }
    }

    public void updateUserSex(String userId, String sex) {
        if (userId != null) {
            ContentValues values = new ContentValues();
            values.put("userSex", sex);
            db.update("user", values, "userId = ?",
                    new String[]{userId});
        }
    }

    public void updateUserWeight(String userId, String weight) {
        if (userId != null) {
            ContentValues values = new ContentValues();
            values.put("userWeight", weight);
            db.update("user", values, "userId = ?",
                    new String[]{userId});
        }
    }

    public void updateUserHeight(String userId, String height) {
        if (userId != null) {
            ContentValues values = new ContentValues();
            values.put("userWeight", height);
            db.update("user", values, "userId = ?",
                    new String[]{userId});
        }
    }

    public void updateUserStepLength(String userId, String length) {
        if (userId != null) {
            ContentValues values = new ContentValues();
            values.put("userStepLength", length);
            db.update("user", values, "userId = ?",
                    new String[]{userId});
        }
    }

    public void updateUserSensitivity(String userId, String sens) {
        if (userId != null) {
            ContentValues values = new ContentValues();
            values.put("userSensitivity", sens);
            db.update("user", values, "userId = ?",
                    new String[]{userId});
        }
    }

    public UserBean queryUserInfo() {
        UserBean user = null;
        Cursor cursor = db.query("user", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            user = new UserBean();
            user.setUserId(cursor.getString(cursor.getColumnIndex("userId")));
            user.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
            user.setUserSex(cursor.getString(cursor.getColumnIndex("userSex")));
            user.setUserPic(cursor.getString(cursor.getColumnIndex("userPic")));
            user.setUserWeight(cursor.getInt(cursor
                    .getColumnIndex("userWeight")));
            user.setUserHeight(cursor.getInt(cursor
                    .getColumnIndex("userHeight")));
            user.setUserSensitivity(cursor.getInt(cursor.getColumnIndex("userSensitivity")));
            user.setUserStepLength(cursor.getInt(cursor.getColumnIndex("userStepLength")));
        }
        return user;
    }

    /**
     * 给Step表中插入一条数据
     *
     * @param stepBean
     */
    public void insertStep(StepBean stepBean) {
        if (stepBean != null) {
            ContentValues stepValues = new ContentValues();
            stepValues.put("userId", stepBean.getUserId());
            stepValues.put("date", stepBean.getDate());
            stepValues.put("stepCount", stepBean.getStepCount());
            db.insert(STEP_TABLE_NAME, null, stepValues);
        }

    }

    /**
     * 更新step
     *
     * @param stepBean
     */
    public void updateStep(StepBean stepBean) {
        if (stepBean != null) {
            ContentValues stepValues = new ContentValues();
            stepValues.put("stepCount", stepBean.getStepCount());
            db.update(STEP_TABLE_NAME, stepValues, "userId = ? and date = ?", new String[]{
                    stepBean.getUserId(), stepBean.getDate()});
        }

    }

    /**
     * 按照日期查询步数
     *
     * @param userId
     * @param date
     * @return
     */
    public StepBean getStepByDate(String userId, String date) {
        StepBean step = null;
        Cursor cursor = db.query(STEP_TABLE_NAME, null, "userId = ? and date = ?", new String[]{userId, date}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                step = new StepBean();
                step.setUserId(userId);
                step.setDate(cursor.getString(cursor.getColumnIndex("date")));
                step.setStepConut(cursor.getInt(cursor.getColumnIndex("stepCount")));
                Log.i(TAG, "得到一个step");
            } while (cursor.moveToNext());

        }
        return step;

    }

    public List<StepBean> getStepByUserId(String userId) {
        List<StepBean> stepList = new ArrayList<>();

        Cursor cursor = db.query(STEP_TABLE_NAME, null, "userId =", new String[]{userId}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                StepBean step = new StepBean();
                step.setUserId(userId);
                step.setDate(cursor.getString(cursor.getColumnIndex("date")));
                step.setStepConut(cursor.getInt(cursor.getColumnIndex("stepCount")));
                Log.i(TAG, "得到一个step");
                stepList.add(step);
            } while (cursor.moveToNext());

        }
        return stepList;
    }

}
