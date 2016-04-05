package com.project.graduation.jackben.pedometer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.project.graduation.jackben.pedometer.fragments.CurrentLocationFragment;
import com.project.graduation.jackben.pedometer.fragments.FriendContestFragment;
import com.project.graduation.jackben.pedometer.fragments.HomeFragment;
import com.project.graduation.jackben.pedometer.fragments.SettingFragment;
import com.project.graduation.jackben.pedometer.fragments.SportsHistoryFragment;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class HomeActivity extends AppCompatActivity implements OnClickListener {

    private ResideMenu mResideMenu;
    private ResideMenuItem item, item2, item1, item3, item4;
    private Fragment homeFragment;
    private Fragment currentLocationFragment;
    private Fragment friendContestFragment;
    private Fragment sportsHistoryFragment;
    private Fragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initToolBar();
        initFragments();
        initResideMenu();
        if (savedInstanceState == null) {
            if (homeFragment != null) {
                ReplaceFragment(homeFragment);
            } else {
                ReplaceFragment(new HomeFragment());
            }
        }

    }

    /**
     * 初始化标题栏
     */
    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.slidemenu_btn_more);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mResideMenu.isOpened()) {
                    mResideMenu.closeMenu();
                } else {
                    mResideMenu.openMenu(mResideMenu.DIRECTION_LEFT);
                }
            }
        });
    }

    /**
     * 初始化侧滑菜单
     */
    private void initResideMenu() {
        mResideMenu = new ResideMenu(this);
        mResideMenu.setBackground(R.drawable.residemenu_bg);
        mResideMenu.attachToActivity(this);
        mResideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        mResideMenu.setScaleValue(0.6f);
        // create menu items;
        item = new ResideMenuItem(this, R.drawable.slidemenu_home, "我的计步");
        item1 = new ResideMenuItem(this, R.drawable.slidemenu_map, "地图定位");
        item2 = new ResideMenuItem(this, R.drawable.slidemenu_friends, "好友竞赛");
        item3 = new ResideMenuItem(this, R.drawable.slidemenu_data, "历史数据");
        item4 = new ResideMenuItem(this, R.drawable.slidemenu_setting, "设置中心");
        item.setOnClickListener(this);
        item1.setOnClickListener(this);
        item2.setOnClickListener(this);
        item3.setOnClickListener(this);
        item4.setOnClickListener(this);
        mResideMenu.addMenuItem(item, mResideMenu.DIRECTION_LEFT);
        mResideMenu.addMenuItem(item1, mResideMenu.DIRECTION_LEFT);
        mResideMenu.addMenuItem(item2, mResideMenu.DIRECTION_LEFT);
        mResideMenu.addMenuItem(item3, mResideMenu.DIRECTION_LEFT);
        mResideMenu.addMenuItem(item4, mResideMenu.DIRECTION_LEFT);

    }

    private void initFragments() {
        homeFragment = new HomeFragment();
        currentLocationFragment = new CurrentLocationFragment();
        friendContestFragment = new FriendContestFragment();
        sportsHistoryFragment = new SportsHistoryFragment();
        settingFragment = new SettingFragment();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mResideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(HomeActivity.this, UserActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (v == item) {
            ReplaceFragment(homeFragment);

        } else if (v == item1) {
            ReplaceFragment(currentLocationFragment);

        } else if (v == item2) {
            ReplaceFragment(friendContestFragment);

        } else if (v == item3) {

            ReplaceFragment(sportsHistoryFragment);
        } else if (v == item4) {

            ReplaceFragment(settingFragment);
        }
        mResideMenu.closeMenu();
    }

    private void ReplaceFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, fragment, "homefragment").setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

}
