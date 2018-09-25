package com.whut.oneday.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.whut.oneday.BaseActivity;
import com.whut.oneday.R;
import com.whut.oneday.adapter.MainViewPagerAdapter;
import com.whut.oneday.fragment.BillFragment;
import com.whut.oneday.fragment.DiaryFragment;
import com.whut.oneday.fragment.MemoFragment;
import com.whut.oneday.fragment.TodayFragment;
import com.whut.oneday.tools.BottomNavigationViewHelper;
import com.whut.oneday.tools.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.main_title)
    TextView mainTitle;
    @InjectView(R.id.main_toolbar)
    Toolbar mainToolbar;
    @InjectView(R.id.main_viewpager)
    NoScrollViewPager mainViewpager;
    @InjectView(R.id.main_Navigation)
    BottomNavigationView mainNavigation;

    private MainViewPagerAdapter viewPagerAdapter;
    private MenuItem menuItem;

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

        //导航栏添加监听
        mainNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        //ViewPager禁止滑动
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

        viewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewpager.setAdapter(viewPagerAdapter);
        List<Fragment> list = new ArrayList<>();
        list.add(new TodayFragment());
        list.add(new BillFragment());
        list.add(new MemoFragment());
        list.add(new DiaryFragment());
        viewPagerAdapter.setList(list);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
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
                default:break;
            }
            return false;
        }
    };
}
