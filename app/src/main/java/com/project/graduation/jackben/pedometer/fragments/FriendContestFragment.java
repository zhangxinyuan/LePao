package com.project.graduation.jackben.pedometer.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.graduation.jackben.pedometer.R;
import com.project.graduation.jackben.pedometer.adapter.FriendsContestListAdapter;
import com.project.graduation.jackben.pedometer.beans.FriendsContest;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * User: XinYuan(1054344254@qq.com)
 * Date: 2015-10-30
 * Time: 12:05
 */
public class FriendContestFragment extends Fragment {

    private static FriendContestFragment mFriendContestFragment = null;
    private RecyclerView recyclerView;
    private  List<FriendsContest> userList;
    public static FriendContestFragment getInstance() {
        if (mFriendContestFragment == null) {
            synchronized (FriendContestFragment.class) {
                if (mFriendContestFragment == null) {
                    mFriendContestFragment = new FriendContestFragment();
                }
            }
        }
        return mFriendContestFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        BmobQuery<FriendsContest> query = new BmobQuery<>();
        query.order("-stepCount");
        query.findObjects(getActivity(), new FindListener<FriendsContest>() {
            @Override
            public void onSuccess(List<FriendsContest> list) {
                FriendsContestListAdapter adapter = new FriendsContestListAdapter(getActivity(), list, R.layout.item_contest);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(int i, String s) {

                Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}
