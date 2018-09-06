package com.whut.oneday.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.whut.oneday.BaseActivity;
import com.whut.oneday.LoginActivity;
import com.whut.oneday.R;
import com.whut.oneday.fragment.SignUpFragment1;
import com.whut.oneday.fragment.SignUpFragment2;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignUpActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.sign_up_content)
    FrameLayout signUpContent;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment1;
    Fragment fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.inject(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        hideStatus();

        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();

        fragment1=new SignUpFragment1();
        fragment2=new SignUpFragment2();

        fragmentTransaction.add(R.id.sign_up_content,fragment1,"fragment1").show(fragment1)
                .add(R.id.sign_up_content,fragment2,"fragment2").hide(fragment2).commit();
    }

    //填写电话页面下一步
    public void nextFragment(){
        Fragment from=fragmentManager.findFragmentByTag("fragment1");
        Fragment to=fragmentManager.findFragmentByTag("fragment2");

        fragmentTransaction=fragmentManager.beginTransaction();
        if (!to.isAdded()) {//判断是否被添加到了Activity里面去了
            fragmentTransaction.hide(from).add(R.id.sign_up_content, to).commit();
        } else {
            fragmentTransaction.hide(from).show(to).commit();
        }
    }

    //上一步
    private void prevFragment(){
        Fragment from=fragmentManager.findFragmentByTag("fragment2");
        Fragment to=fragmentManager.findFragmentByTag("fragment1");

        fragmentTransaction=fragmentManager.beginTransaction();
        if (!to.isAdded()) {//判断是否被添加到了Activity里面去了
            fragmentTransaction.hide(from).add(R.id.sign_up_content, to).commit();
        } else {
            fragmentTransaction.hide(from).show(to).commit();
        }
    }

    public void signUpComplete(){
        Intent intent=new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);

        //关闭页面
        finish();
    }

    public Fragment getVisibleFragment(){
        List<Fragment> fragments = fragmentManager.getFragments();
        for(Fragment fragment : fragments){
            if(fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    //返回按钮响应
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            if(getVisibleFragment()==fragment1)
                finish();
            else
                prevFragment();
        }
        return super.onOptionsItemSelected(item);
    }
}
