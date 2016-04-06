package com.project.graduation.jackben.pedometer.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.graduation.jackben.pedometer.R;

/**
 * User: XinYuan(1054344254@qq.com)
 * Date: 2015-10-30
 * Time: 12:10
 */
public class SportsHistoryFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sporthistory, container, false);
        return view;

    }
}
