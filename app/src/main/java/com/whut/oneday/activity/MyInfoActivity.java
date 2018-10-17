package com.whut.oneday.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.whut.oneday.BaseActivity;
import com.whut.oneday.R;
import com.whut.oneday.tools.Content;
import com.whut.oneday.tools.GlideUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyInfoActivity extends BaseActivity {
    @InjectView(R.id.my_info_title)
    TextView myInfoTitle;
    @InjectView(R.id.my_info_toolbar)
    Toolbar myInfoToolbar;
    @InjectView(R.id.my_info_icon)
    CircleImageView myInfoIcon;
    @InjectView(R.id.my_info_layout1)
    RelativeLayout myInfoLayout1;
    @InjectView(R.id.my_info_id)
    TextView myInfoId;
    @InjectView(R.id.my_info_layout2)
    RelativeLayout myInfoLayout2;
    @InjectView(R.id.my_info_name)
    TextView myInfoName;
    @InjectView(R.id.my_info_layout3)
    RelativeLayout myInfoLayout3;
    @InjectView(R.id.my_info_layout4)
    RelativeLayout myInfoLayout4;
    @InjectView(R.id.my_info_qq)
    TextView myInfoQq;
    @InjectView(R.id.my_info_layout5)
    RelativeLayout myInfoLayout5;
    @InjectView(R.id.my_info_wechat)
    TextView myInfoWechat;
    @InjectView(R.id.my_info_layout6)
    RelativeLayout myInfoLayout6;
    @InjectView(R.id.log_out)
    Button logOut;
    @InjectView(R.id.my_info_phone)
    TextView myInfoPhone;
    @InjectView(R.id.my_info_layout7)
    RelativeLayout myInfoLayout7;

    private final int LOCAL_USER_ICON_REQUEST = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.inject(this);

        //隐藏状态栏
        hideStatus();

        //初始化视图
        initView();

        //添加返回按钮
        myInfoToolbar.setTitle("");
        myInfoTitle.setText(R.string.my_info);
        setSupportActionBar(myInfoToolbar);

        //左上角图标
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //设置左上角图标是否可点击
            actionBar.setHomeButtonEnabled(true);
            //左上角加上一个返回图标
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        myInfoLayout1.setOnClickListener(new MyListener());
        myInfoLayout2.setOnClickListener(new MyListener());
        myInfoLayout3.setOnClickListener(new MyListener());
        myInfoLayout4.setOnClickListener(new MyListener());
        myInfoLayout5.setOnClickListener(new MyListener());
        myInfoLayout6.setOnClickListener(new MyListener());
    }

    /**
     * 初始化活动页面
     */
    private void initView() {
        if (Content.localUser == null) {
            return;
        }
        if (Content.localUser.getIconpath() != null) {
            RequestOptions myOptions = new RequestOptions().fitCenter();
            Glide.with(this)
                    .load(Content.localUser.getIconpath())
                    .apply(myOptions)
                    .into(myInfoIcon);
        }
        if (Content.localUser.getUserID() != null) {
            myInfoId.setText(String.valueOf(Content.localUser.getUserID()));
        }
        if (Content.localUser.getUsername() != null) {
            myInfoName.setText(Content.localUser.getUsername());
        }
        if (Content.localUser.getPhone() != null) {
            myInfoPhone.setText(Content.localUser.getPhone());
        } else {
            myInfoPhone.setText("未设置");
        }
        if (Content.localUser.getQq() != null) {
            myInfoQq.setText(Content.localUser.getQq());
        } else {
            myInfoQq.setText("未设置");
        }
        if (Content.localUser.getWechat() != null) {
            myInfoWechat.setText(Content.localUser.getWechat());
        } else {
            myInfoWechat.setText("未设置");
        }
    }

    /**
     * 返回按钮响应
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    /**
     * 创建监听类
     */
    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                // TODO: 2018/10/9 根据点击进行跳转
                case R.id.my_info_layout1:
                    //第三方加载
                    Matisse.from(MyInfoActivity.this)
                            .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.GIF))//照片视频全部显示MimeType.allOf()
                            .countable(true)//true:选中后显示数字;false:选中后显示对号
                            .maxSelectable(3)//最大选择数量为9
                            //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                            .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))//图片显示表格的大小
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)//图像选择和预览活动所需的方向
                            .thumbnailScale(0.85f)//缩放比例
                            .theme(R.style.customTheme)//主题
                            .imageEngine(new GlideUtil())//图片加载方式，Glide4需要自定义实现
                            .capture(true) //是否提供拍照功能，兼容7.0系统需要下面的配置
                            //参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                            .captureStrategy(new CaptureStrategy(true, "com.sendtion.matisse.fileprovider"))//存储到哪里
                            .forResult(LOCAL_USER_ICON_REQUEST);//请求码
                    break;
                case R.id.my_info_layout2:
                    break;
                case R.id.my_info_layout3:
                    break;
                case R.id.my_info_layout4:
                    break;
                case R.id.my_info_layout5:
                    break;
                case R.id.my_info_layout6:
                    break;
                case R.id.log_out:
                    break;

            }
        }
    }
}
