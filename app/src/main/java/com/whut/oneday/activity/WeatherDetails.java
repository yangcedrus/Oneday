package com.whut.oneday.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.whut.oneday.BaseActivity;
import com.whut.oneday.R;
import com.whut.oneday.adapter.FragmentViewPagerAdapter;
import com.whut.oneday.fragment.ForecastFragment;
import com.whut.oneday.tools.Content;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WeatherDetails extends BaseActivity {

    @InjectView(R.id.weather_details_viewPager)
    ViewPager weatherDetailsViewPager;
    @InjectView(R.id.weather_details_points)
    LinearLayout weatherDetailsPoints;
    @InjectView(R.id.weather_details_toolbar)
    Toolbar weatherDetailsToolbar;
    @InjectView(R.id.weather_details_background)
    ConstraintLayout weatherDetailsBackground;

    private int cityNum = 0;
    private FragmentViewPagerAdapter mAdapter;
    private int lastPosition;   //上次滑到的页面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);
        ButterKnife.inject(this);

        //隐藏状态栏
        hideStatus();

        //设置返回按钮
        weatherDetailsToolbar.setTitle("");
        setSupportActionBar(weatherDetailsToolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            //设置左上角图标是否可点击
            actionBar.setHomeButtonEnabled(true);
            //左上角加上一个返回图标
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // FIXME: 2018/10/17 修改背景图片
        RequestOptions myOption=new RequestOptions().fitCenter();
        Glide.with(this)
                .load("https://api.i-meto.com/bing?color=White")
                .apply(myOption)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        resource=getWhiteImage(resource);
                        resource.setAlpha(180);
                        weatherDetailsBackground.setBackground(resource);
                    }
                });

        //获取已存储城市数量
        cityNum = Content.localWeatherMsg.size();

        initView();
        initData();
    }

    //初始化视图
    private void initView() {
        //插入屏幕底部提示的圆点
        View pointView;
        for (int i = 0; i < cityNum; i++) {
            pointView = new View(this);
            pointView.setBackgroundResource(R.drawable.selector_point);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
            if (i != 0) {
                layoutParams.leftMargin = 10;
            }
            if (i == 0)
                pointView.setEnabled(true);
            else
                pointView.setEnabled(false);
            weatherDetailsPoints.addView(pointView, layoutParams);
        }

        weatherDetailsViewPager.setOffscreenPageLimit(4);   //设置缓存页面数量
        weatherDetailsViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                weatherDetailsPoints.getChildAt(lastPosition).setEnabled(false);
                weatherDetailsPoints.getChildAt(position).setEnabled(true);
                lastPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //初始化视图数据
    private void initData() {
        //设置城市信息，添加到viewpager中
        mAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager());
        weatherDetailsViewPager.setAdapter(mAdapter);
        List<Fragment> list = new ArrayList<>();
        for (int i = 0; i < cityNum; i++) {
            list.add(ForecastFragment.newInstance(Content.localWeatherMsg.get("天津市").getData(), "天津市"));
        }
        mAdapter.setList(list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather_details,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.weather_details_addCity:
                showToast("添加城市",false);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //获取加白遮罩图片
    public Drawable getWhiteImage(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;

        Bitmap bm= bd.getBitmap();
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.RGB_565);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Canvas canvas = new Canvas(bmp);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bm, 0, 0, paint);
        canvas.drawColor(Color.parseColor("#b0ffffff"));

        drawable = new BitmapDrawable(bmp);
        return drawable;

    }
}
