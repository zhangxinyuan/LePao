package com.project.graduation.jackben.pedometer.fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.project.graduation.jackben.pedometer.LoginActivity;
import com.project.graduation.jackben.pedometer.R;
import com.project.graduation.jackben.pedometer.UserActivity;

/**
 * User: XinYuan(1054344254@qq.com)
 * Date: 2015-10-30
 * Time: 12:13
 */
public class SettingFragment extends Fragment implements View.OnClickListener {
    private View view;
    private static SettingFragment mSettingFragment = null;
    private RelativeLayout setting_user, setting_step, setting_msg, seeting_share, setting_sofw_update, setting_suggest, seeting_about;
    private Button exit_login;

    public static SettingFragment getInstance() {
        if (mSettingFragment == null) {
            synchronized (SettingFragment.class) {
                if (mSettingFragment == null) {
                    mSettingFragment = new SettingFragment();
                }
            }
        }
        return mSettingFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        initView();
        return view;

    }

    private void initView() {
        exit_login = (Button) view.findViewById(R.id.exit_login);
        setting_user = (RelativeLayout) view.findViewById(R.id.setting_user);
        setting_step = (RelativeLayout) view.findViewById(R.id.setting_step);
        setting_msg = (RelativeLayout) view.findViewById(R.id.setting_msg);
        seeting_share = (RelativeLayout) view.findViewById(R.id.seeting_share);
        setting_sofw_update = (RelativeLayout) view.findViewById(R.id.setting_sofw_update);
        setting_suggest = (RelativeLayout) view.findViewById(R.id.setting_suggest);
        seeting_about = (RelativeLayout) view.findViewById(R.id.seeting_about);
        exit_login.setOnClickListener(this);
        setting_user.setOnClickListener(this);
        setting_step.setOnClickListener(this);
        setting_msg.setOnClickListener(this);
        seeting_share.setOnClickListener(this);
        setting_sofw_update.setOnClickListener(this);
        setting_suggest.setOnClickListener(this);
        seeting_about.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit_login:
                if (LoginActivity.mTencent != null)
                    LoginActivity.mTencent.logout(getActivity().getApplicationContext());
                break;
            case R.id.setting_user:
                startActivity(new Intent(getActivity(), UserActivity.class));
                break;
            case R.id.setting_step:
                final String[] choice = new String[]{"低", "中", "高"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("设置灵敏度");
                dialog.setSingleChoiceItems(choice, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity().getApplicationContext(), "设置灵敏度为" + choice[which], Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.setting_msg:
                break;
            case R.id.seeting_share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "我正在使用乐跑跑步，快来加入吧！");
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "分享乐跑到"));
                break;
            case R.id.setting_sofw_update:
                Toast.makeText(getActivity(), "已是最新版本", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_suggest:
                break;
            case R.id.seeting_about:
                break;
        }
    }
}
