package com.project.graduation.jackben.pedometer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * PedometerOpenHelper
 * User: XinYuan(1054344254@qq.com)
 * Date: 2015-11-20
 * Time: 16:52
 */
public class PedometerOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "lepao.db";
    private static final String USER_TABLE_NAME = "user";
    private static final String STEP_TABLE_NAME = "step";

    private static final String STEP_TABLE_CREATE = "CREATE TABLE " + STEP_TABLE_NAME + "("
            + "_id integer primary key autoincrement," + "userId TEXT," + "stepCount integer," + "date TEXT" + ")";
    private static final String USER_TABLE_CREATE = "CREATE TABLE " + USER_TABLE_NAME + "(" +"_id integer primary key autoincrement," + "userId TEXT," + "userName TEXT,"
            + "userSex TEXT," + "userPic BLOB," + "userWeight integer," + "userHeight integer," + "userSensitivity integer," + "userStepLength integer" + ")";

    PedometerOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(STEP_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
