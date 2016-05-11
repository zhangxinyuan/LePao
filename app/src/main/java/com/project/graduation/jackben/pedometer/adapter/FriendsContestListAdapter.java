package com.project.graduation.jackben.pedometer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.graduation.jackben.pedometer.R;
import com.project.graduation.jackben.pedometer.beans.FriendsContest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * User: XinYuan(1054344254@qq.com)
 * Date: 2016-05-06
 * Time: 18:58
 */
public class FriendsContestListAdapter extends RecyclerView.Adapter<FriendsContestListAdapter.FCListViewHolder> {

    private Context context;
    private List<FriendsContest> userList = new ArrayList<>();
    private int layoutId;

    public FriendsContestListAdapter(Context context, List<FriendsContest> userList, int layoutId) {
        this.context = context;
        this.userList = userList;
        this.layoutId = layoutId;
    }

    @Override
    public FCListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, layoutId, null);
        return new FCListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FCListViewHolder holder, int position) {
        FriendsContest contestDataBean = userList.get(position);
        holder.setData(contestDataBean,position);
    }

    @Override
    public int getItemCount() {
        return userList == null ? 0 : userList.size();
    }

    public class FCListViewHolder extends RecyclerView.ViewHolder {
        private TextView contest_paihang, contest_step, contest_name;
        private ImageView contest_icon;
        private ProgressBar contest_progressBar;

        public FCListViewHolder(View itemView) {
            super(itemView);
            contest_paihang = (TextView) itemView.findViewById(R.id.contest_paihang);
            contest_icon = (ImageView) itemView.findViewById(R.id.contest_icon);
            contest_name = (TextView) itemView.findViewById(R.id.contest_name);
            contest_progressBar = (ProgressBar) itemView.findViewById(R.id.contest_progressBar);
            contest_step = (TextView) itemView.findViewById(R.id.contest_step);
        }

        public void setData(FriendsContest bean, int position) {
            if (position == 0) {
                contest_paihang.setBackgroundResource(R.mipmap.chat_rank_medal_1);
            } else if (position == 1) {
                contest_paihang.setBackgroundResource(R.mipmap.chat_rank_medal_2);
            } else if (position == 2) {
                contest_paihang.setBackgroundResource(R.mipmap.chat_rank_medal_3);
            } else {
                contest_paihang.setText(position + 1+"");
            }
            contest_name.setText(bean.getUserName());
            Picasso.with(context).load(bean.getUserIcon().getFileUrl(context)).into(contest_icon);
            contest_step.setText(bean.getStepCount()+"步");
            contest_progressBar.setProgress(bean.getStepCount());
        }
    }
}
