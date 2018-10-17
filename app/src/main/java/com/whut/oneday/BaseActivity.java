package com.whut.oneday;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    public static Boolean BASE_LONG = true;
    public static Boolean BASE_SHORT = false;

    /**
     * 显示Toast方法
     *
     * @param string 要显示的字符串
     * @param time   显示时间，true为LONG，false为SHORT
     */
    public void showToast(String string, boolean time) {
        Toast.makeText(this, string, (time) ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    public void hideStatus() {
        //透明状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
