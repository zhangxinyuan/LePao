package com.project.graduation.jackben.pedometer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.graduation.jackben.pedometer.R;

/**
 * User: XinYuan(1054344254@qq.com)
 * Date: 2015-10-30
 * Time: 12:05
 */
public class FriendContestFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        return view;
    }
}