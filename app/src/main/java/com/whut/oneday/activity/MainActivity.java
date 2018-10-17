package com.whut.oneday.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.whut.oneday.BaseActivity;
import com.whut.oneday.R;
import com.whut.oneday.adapter.FragmentViewPagerAdapter;
import com.whut.oneday.fragment.BillFragment;
import com.whut.oneday.fragment.DiaryFragment;
import com.whut.oneday.fragment.MemoFragment;
import com.whut.oneday.fragment.TodayFragment;
import com.whut.oneday.tools.BottomNavigationViewHelper;
import com.whut.oneday.tools.Content;
import com.whut.oneday.tools.NoScrollViewPager;
import com.whut.oneday.weatherUtils.WeatherMsg;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @InjectView(R.id.main_title)
    TextView mainTitle;
    @InjectView(R.id.main_toolbar)
    Toolbar mainToolbar;
    @InjectView(R.id.main_viewpager)
    NoScrollViewPager mainViewpager;
    @InjectView(R.id.main_Navigation)
    BottomNavigationView mainNavigation;
    @InjectView(R.id.main_drawer)
    DrawerLayout drawerLayout;
    @InjectView(R.id.navigation_view)
    NavigationView drawerNavigation;

    private CircleImageView userIcon;
    private TextView userName, billDays, bills;
    private FragmentViewPagerAdapter viewPagerAdapter;
    private MenuItem menuItem;
    private ActionBarDrawerToggle mDrawerToggle;
    private String currentTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mainTitle.setText(R.string.main_bottom_today);

        //状态栏透明
        hideStatus();

        //导航栏动画消除
        BottomNavigationViewHelper.disableShiftMode(mainNavigation);

        //左上角图标
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //设置左上角图标是否可点击
            actionBar.setHomeButtonEnabled(true);
            //左上角加上一个返回图标
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //页面数据初始化
        initData();

        //导航栏添加监听
        mainNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        //ViewPager禁止滑动,添加页面监听
        mainViewpager.setNoScroll(true);
        mainViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    mainNavigation.getMenu().getItem(0).setChecked(false);
                }
                menuItem = mainNavigation.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //设置当前页面外缓存页面数(3个缓存+1个当前显示)
        mainViewpager.setOffscreenPageLimit(3);

        viewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager());
        mainViewpager.setAdapter(viewPagerAdapter);
        List<Fragment> list = new ArrayList<>();
        list.add(new TodayFragment());
        list.add(new BillFragment());
        list.add(new MemoFragment());
        list.add(new DiaryFragment());
        viewPagerAdapter.setList(list);
    }

    /**
     * 初始化DrawerLayout
     */
    private void initData() {
        //加载头像侧拉栏头像以及名称，记账的记录信息
        View view = getLayoutInflater().inflate(R.layout.main_drawer_header, null);
        userIcon = view.findViewById(R.id.main_drawer_icon);
        userName = view.findViewById(R.id.main_drawer_name);
        billDays = view.findViewById(R.id.main_header_tv_days);
        bills = view.findViewById(R.id.main_header_tv_bills);

        billDays.setText("1");
        bills.setText("1");

        if (Content.localUser.getIconpath() != null) {
            RequestOptions myOptions = new RequestOptions().fitCenter();
            Glide.with(this)
                    .load(Content.localUser.getIconpath())
                    .apply(myOptions)
                    .into(userIcon);
        }
        if (Content.localUser.getUsername() != null) {
            userName.setText(Content.localUser.getUsername());
        }

        //设置头像框监听事件
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // FIXME: 2018/10/9 本地存储实现后取消注释
//                if(Content.localUser==null)
//                    return;
                startActivity(new Intent(MainActivity.this, MyInfoActivity.class));
            }
        });
        //添加到navigation中(注：在xml中添加无法则header内容无法添加监听）
        drawerNavigation.addHeaderView(view);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mainToolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                currentTitle = mainTitle.getText().toString();
                mainTitle.setText("");
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (currentTitle == null) {
                    currentTitle = "";
                }
                mainTitle.setText(currentTitle);
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle);
        drawerNavigation.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //侧边栏选中事件
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String mString = null;
        switch (id) {
            case R.id.nav_billType:
                mString = "收支类别";
                break;
            case R.id.nav_remind:
                mString = "事件提醒";
                break;
            case R.id.nav_theme:
                mString = "主题风格";
                break;
            case R.id.nav_setting:
                mString = "设置";
                break;
            case R.id.nav_suggestion:
                mString = "意见反馈";
                break;
            case R.id.nav_about:
                mString = "关于";
                break;
        }
        showToast(mString, false);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_today:
                    //切换标题
                    mainTitle.setText(R.string.main_bottom_today);

                    //切换页面
                    mainViewpager.setCurrentItem(0);
                    break;
                case R.id.navigation_bill:
                    //切换标题
                    mainTitle.setText(R.string.main_bottom_bill);

                    //切换页面
                    mainViewpager.setCurrentItem(1);
                    break;
                case R.id.navigation_memo:
                    //切换标题
                    mainTitle.setText(R.string.main_bottom_memo);

                    //切换页面
                    mainViewpager.setCurrentItem(2);
                    break;
                case R.id.navigation_diary:
                    //切换标题
                    mainTitle.setText(R.string.main_bottom_diary);

                    //切换页面
                    mainViewpager.setCurrentItem(3);
                    break;
                default:
                    break;
            }
            return false;
        }
    };
}
