package com.example.administrator.test.View;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.test.Presenter.Activity.MainActivity;
import com.example.administrator.test.R;

/**
 * Created by Administrator on 2017-06-08.
 */

public class Main_View {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle toggle;
    public NavigationView navigationView;
    public ImageView btn_navi_settings, img_navi_profile;
    MainActivity activity;

    public Main_View(MainActivity activity){
        this.activity = activity;

        init();

    }

    // 0. init
    public void init(){
        findAddress();
        setListener();
        setFirst();
//        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        toggle.syncState();
    }

    // 1. findViewById
    public void findAddress(){
        drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
        View headerlayout = navigationView.getHeaderView(0); // 헤더 뷰 얻는 법!!!!!!! 매우 오랜 시간이 걸렸음. 잘 숙지할 것!!
        btn_navi_settings = (ImageView) headerlayout.findViewById(R.id.btn_navigation_settings);
        img_navi_profile = (ImageView) headerlayout.findViewById(R.id.imageView);
    }

    // 2. 리스너 등록
    public void setListener(){
        drawerLayout.setDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(activity);
        btn_navi_settings.setOnClickListener(activity);
        img_navi_profile.setOnClickListener(activity);
    }

    // 3. 초기값 설정
    public void setFirst(){
        img_navi_profile.setEnabled(false);
    }


}
