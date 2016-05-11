package com.project.graduation.jackben.pedometer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.graduation.jackben.pedometer.beans.UserBean;
import com.project.graduation.jackben.pedometer.db.PedometerDbUtil;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton fab;
    private CardView pickname_card_view, sex_card_view, height_card_view, weight_card_view;
    private TextView pickname, sex, height, weight;
    private PedometerDbUtil dbUtil;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        initClick();

    }

    private void initClick() {
        pickname_card_view.setOnClickListener(this);
        sex_card_view.setOnClickListener(this);
        height_card_view.setOnClickListener(this);
        weight_card_view.setOnClickListener(this);
        fab.setOnClickListener(this);

    }

    private void initView() {
        pickname_card_view = (CardView) findViewById(R.id.pickname_card_view);
        sex_card_view = (CardView) findViewById(R.id.pickname_card_view);
        height_card_view = (CardView) findViewById(R.id.height_card_view);
        weight_card_view = (CardView) findViewById(R.id.weight_card_view);
        pickname = (TextView) findViewById(R.id.pickname);
        sex = (TextView) findViewById(R.id.sex);
        height = (TextView) findViewById(R.id.height);
        weight = (TextView) findViewById(R.id.weight);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        dbUtil = PedometerDbUtil.getInstance(this);
        UserBean userBean = dbUtil.queryUserInfo();
        if (userBean != null) {
             userId = userBean.getUserId();
            pickname.setText(userBean.getUserName());
            sex.setText(userBean.getUserSex());
            height.setText(userBean.getUserHeight()+"");
            weight.setText(userBean.getUserWeight()+"");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pickname_card_view:
                showChangeDiglog("昵称", pickname,1);
                break;
            case R.id.sex_card_view:
                showChangeDiglog("性别",sex,2);
                break;
            case R.id.height_card_view:
                showChangeDiglog("身高",height,3);
                break;
            case R.id.weight_card_view:
                showChangeDiglog("体重",weight,4);
                break;
            case R.id.fab://修改用户头像
                break;
        }

    }

    private void showChangeDiglog(String title, final TextView textView, final int type) {
        final EditText editText = new EditText(this);

        new AlertDialog.Builder(this).setTitle(title).setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input = editText.getText().toString();
                if (input != null) {
                    textView.setText(input);
                    switch (type){
                        case 1:
                            dbUtil.updateUserName(userId,input);
                            break;
                        case 2:
                            dbUtil.updateUserSex(userId,input);
                            break;
                        case 3:
                            dbUtil.updateUserHeight(userId,input);
                            break;
                        case 4:
                            dbUtil.updateUserWeight(userId,input);
                            break;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "昵称不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        }).setNegativeButton("取消", null).show();

    }
}
