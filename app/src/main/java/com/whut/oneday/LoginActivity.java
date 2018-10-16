package com.whut.oneday;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.whut.oneday.activity.DiaryDetailActivity;
import com.whut.oneday.activity.EditDiaryActivity;
import com.whut.oneday.activity.MainActivity;
import com.whut.oneday.activity.SignUpActivity;
import com.whut.oneday.entity.User;
import com.whut.oneday.tools.Content;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends BaseActivity {
    TextView loginPopSignUp;
    TextView loginPopPhoneLogin;
    TextView loginPopTryIn;
    TextView loginPopCancel;

    @InjectView(R.id.login_button)
    Button loginButton;
    @InjectView(R.id.login_other_login)
    TextView loginOtherLogin;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        hideStatus();

        //登录界面登录按钮点击响应
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
//                intent.putExtra("diary_detail", Content.tempDiary);
                startActivity(intent);
                finish();
            }
        });

        //登录页面更多登录方式点击响应
        loginOtherLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lightoff();
                showPopWindow();
            }
        });
    }

    private void showPopWindow() {
        View popupView = View.inflate(this, R.layout.login_pop_menu, null);
        loginPopSignUp=popupView.findViewById(R.id.login_pop_sign_up);
        loginPopPhoneLogin=popupView.findViewById(R.id.login_pop_phone_login);
        loginPopTryIn=popupView.findViewById(R.id.login_pop_try_in);
        loginPopCancel=popupView.findViewById(R.id.login_pop_cancel);

        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lighton();
            }
        });

        //设置背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);

        //点击其他地方消失
        popupWindow.setOutsideTouchable(true);

        //设置动画
        popupWindow.setAnimationStyle(R.style.login_pop_anim_style);

        //菜单各个title响应
        loginPopSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("点击了第一个title",BASE_SHORT);
                Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                popupWindow.dismiss();
                lighton();
            }
        });
        loginPopPhoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("点击了第二个title",BASE_SHORT);
                popupWindow.dismiss();
                lighton();
            }
        });
        loginPopTryIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("点击了第三个title",BASE_SHORT);
                popupWindow.dismiss();
                lighton();
            }
        });
        loginPopCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                lighton();
            }
        });

        // 在点击之后设置popupwindow的销毁
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
            lighton();
        }

        // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
        popupWindow.showAtLocation(LoginActivity.this.findViewById(R.id.login_other_login), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 设置手机屏幕亮度变暗
     */
    private void lightoff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
    }

    /**
     * 设置手机屏幕亮度显示正常
     */
    private void lighton() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
    }

    private void saveUser(){
        // TODO: 2018/10/9 登录后存储user信息到Content中，然后保存到sharedpreference中
    }
}
