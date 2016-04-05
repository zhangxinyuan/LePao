package com.project.graduation.jackben.pedometer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import cn.bmob.v3.Bmob;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends AppCompatActivity {

    private View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        //初始化bmob服务器
        Bmob.initialize(this, "b11275335a9208ba615a41c536efe906");
        mContentView = findViewById(R.id.fullscreen_content);
        mContentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                finish();
                return false;

            }
        });

    }


}
