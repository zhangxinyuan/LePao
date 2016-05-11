package com.project.graduation.jackben.pedometer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.project.graduation.jackben.pedometer.beans.UserBean;
import com.project.graduation.jackben.pedometer.db.PedometerDbUtil;

import cn.bmob.v3.Bmob;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {

    private View mContentView;
    private UserBean userBean = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        //初始化bmob服务器
        Bmob.initialize(this, "b11275335a9208ba615a41c536efe906");
        mContentView = findViewById(R.id.fullscreen_content);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.welcome_anim);
        animation.setFillEnabled(true);
        animation.setFillAfter(true);
        mContentView.setAnimation(animation);
        animation.setAnimationListener(this);
        PedometerDbUtil dbUtil = PedometerDbUtil.getInstance(this);
        userBean = dbUtil.queryUserInfo();//取用户信息

    }


    @Override
    public void onAnimationStart(Animation animation) {


    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (userBean == null) {//信息为空，说明没登录
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        }
        this.finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
