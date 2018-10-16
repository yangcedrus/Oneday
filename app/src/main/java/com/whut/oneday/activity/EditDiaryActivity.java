package com.whut.oneday.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.sendtion.xrichtext.RichTextEditor;
import com.whut.oneday.BaseActivity;
import com.whut.oneday.R;
import com.whut.oneday.entity.Diary;
import com.whut.oneday.tools.CommonUtil;
import com.whut.oneday.tools.GlideUtil;
import com.whut.oneday.tools.ImageUtils;
import com.whut.oneday.tools.SDCardUtil;
import com.whut.oneday.tools.StringUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditDiaryActivity extends BaseActivity {
    @InjectView(R.id.edit_diary_title)
    TextView editDiaryTitle;
    @InjectView(R.id.edit_diary_toolbar)
    Toolbar editDiaryToolbar;
    @InjectView(R.id.edit_diary_body)
    RichTextEditor editDiaryBody;
    @InjectView(R.id.edit_diary_date)
    TextView editDiaryDate;
    @InjectView(R.id.edit_diary_weather_text)
    TextView editDiaryWeatherText;
    @InjectView(R.id.edit_diary_weather_icon)
    ImageView editDiaryWeatherIcon;
    @InjectView(R.id.edit_diary_mood_icon)
    ImageView editDiaryMoodIcon;
    @InjectView(R.id.edit_diary_mood_text)
    TextView editDiaryMoodText;

    private boolean saveFlag = false;   //true为已经点击保存，false为未点击保存
    private static final int DIARY_REQUEST = 101;//定义请求码常量
    private int screenWidth,screenHeight;   //屏幕宽高
    private ProgressDialog insertDialog,loadingDialog;
    private Subscription subsInsert,subsLoading;
    private Diary diary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_diary);
        ButterKnife.inject(this);

        //隐藏状态栏
        hideStatus();

        //设置页面数据
        initData();
        if(diary!=null)
            showDataSync(diary.getBody());

        //设置supportActionbar
        editDiaryToolbar.setTitle("");
        String s;
        if (diary==null)
            s = "新建日记";
        else
            s = "编辑日记";
        editDiaryTitle.setText(s);
        setSupportActionBar(editDiaryToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //左上角图标可点击
            actionBar.setHomeButtonEnabled(true);
            //显示图标
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 初始化页面视图数据
     */
    private void initData(){
        // TODO: 2018/10/16 添加心情以及天气图片，此处处理
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
        String datestring=format.format(date);
        editDiaryDate.setText(datestring);

        //图片插入进程框
        insertDialog = new ProgressDialog(this);
        insertDialog.setMessage("正在插入图片...");
        insertDialog.setCanceledOnTouchOutside(false);

        //读取数据进程框
        loadingDialog=new ProgressDialog(this);
        loadingDialog.setMessage("读取中...");
        loadingDialog.setCanceledOnTouchOutside(false);

        //测量屏幕宽度
        screenWidth = CommonUtil.getScreenWidth(this);
        screenHeight = CommonUtil.getScreenHeight(this);

        //获取传递的diary信息
        Intent intent=getIntent();
        if (intent==null){
            return;
        }
        diary=(Diary)intent.getParcelableExtra("edit_diary");
    }

    /**
     * 虚拟返回按键响应
     */
    @Override
    public void onBackPressed() {
        if(!saveFlag)
            showDialog();
        super.onBackPressed();
    }

    /**
     * 显示右上角菜单
     *
     * @param menu 菜单
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_diary, menu);
        return true;
    }

    /**
     * 右上角菜单点击响应
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_diary_image:
                Matisse.from(this)
                        .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.GIF))//照片视频全部显示MimeType.allOf()
                        .countable(true)//true:选中后显示数字;false:选中后显示对号
                        .maxSelectable(3)//最大选择数量为9
                        //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))//图片显示表格的大小
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)//图像选择和预览活动所需的方向
                        .thumbnailScale(0.85f)//缩放比例
                        .theme(R.style.customTheme)//主题
                        .imageEngine(new GlideUtil())//图片加载方式，Glide4需要自定义实现
                        .capture(false) //是否提供拍照功能，兼容7.0系统需要下面的配置
                        //参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                        .captureStrategy(new CaptureStrategy(true, "com.sendtion.matisse.fileprovider"))//存储到哪里
                        .forResult(DIARY_REQUEST);//请求码
                break;
            case R.id.edit_diary_save:
                saveDiaryData();
                break;
            case android.R.id.home:
                if(!saveFlag)
                    showDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 处理图片选择器的返回
     *
     * @param requestCode 请求码
     * @param resultCode  结果代码
     * @param data        数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == 1) {
                    //处理调用图库
                } else if (requestCode == DIARY_REQUEST) {
                    //异步方式插入图片
                    insertImagesSync(data);
                }
            }
        }
    }

    /**
     * 异步方式插入图片
     * @param data 图片选择器返回的数据
     */
    private void insertImagesSync(final Intent data) {
        insertDialog.show();

        subsInsert= Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try{
                    editDiaryBody.measure(0, 0);
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    // 可以同时插入多张图片
                    for (Uri imageUri : mSelected) {
                        String imagePath = SDCardUtil.getFilePathFromUri(EditDiaryActivity.this,  imageUri);
                        //Log.e(TAG, "###path=" + imagePath);
                        Bitmap bitmap = ImageUtils.getSmallBitmap(imagePath, screenWidth, screenHeight);//压缩图片
                        //bitmap = BitmapFactory.decodeFile(imagePath);
                        imagePath = SDCardUtil.saveToSdCard(bitmap);
                        //Log.e(TAG, "###imagePath="+imagePath);
                        subscriber.onNext(imagePath);
                    }

                    // 测试插入网络图片 http://p695w3yko.bkt.clouddn.com/18-5-5/44849367.jpg
                    //subscriber.onNext("http://p695w3yko.bkt.clouddn.com/18-5-5/30271511.jpg");

                    subscriber.onCompleted();
                }catch (Exception e){
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())//生产事件在io
                .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        if (insertDialog != null && insertDialog.isShowing()) {
                            insertDialog.dismiss();
                        }
                        showToast("图片插入成功",false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (insertDialog != null && insertDialog.isShowing()) {
                            insertDialog.dismiss();
                        }
                        showToast("图片插入失败:"+e.getMessage(),false);
                    }

                    @Override
                    public void onNext(String s) {
                        editDiaryBody.insertImage(s, editDiaryBody.getMeasuredWidth());
                    }
                });
    }

    /**
     * 保存日记信息
     */
    private void saveDiaryData(){
        //如果是新建，则添加创建时间
        if(diary==null){
            diary=new Diary();
            diary.setCreatestamp(new Timestamp(System.currentTimeMillis()));
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            String dateString=format.format(diary.getCreatestamp());
            diary.setDate(dateString);
        }
        //否则，只更新以下信息
        diary.setBody(getEditData());
        diary.setMood(editDiaryMoodText.getText().toString());
        diary.setWeather(editDiaryWeatherText.getText().toString());

        // TODO: 2018/10/16 保存日记，数据库/服务器


        saveFlag=true;
    }

    /**
     * 负责处理编辑数据提交等事宜，请自行实现
     */
    // FIXME: 2018/10/16 理清网络图片与本地图片存储关系
    private String getEditData() {
        List<RichTextEditor.EditData> editList = editDiaryBody.buildEditData();
        StringBuffer content = new StringBuffer();
        for (RichTextEditor.EditData itemData : editList) {
            if (itemData.inputStr != null) {
                content.append(itemData.inputStr);
            } else if (itemData.imagePath != null) {
                content.append("<img src=\"").append(itemData.imagePath).append("\"/>");
            }
        }
        return content.toString();
    }

    /**
     * 异步方式显示数据
     * @param html
     */
    private void showDataSync(final String html){

        subsLoading = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                showEditData(subscriber, html);
            }
        })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())//生产事件在io
                .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        if (loadingDialog != null){
                            loadingDialog.dismiss();
                        }
                        //在图片全部插入完毕后，再插入一个EditText，防止最后一张图片后无法插入文字
                        editDiaryBody.addEditTextAtIndex(editDiaryBody.getLastIndex(), "");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (loadingDialog != null){
                            loadingDialog.dismiss();
                        }
                        showToast("解析错误：图片不存在或已损坏",false);
                    }

                    @Override
                    public void onNext(String text) {
                        if (text.contains("<img") && text.contains("src=")) {
                            //imagePath可能是本地路径，也可能是网络地址
                            String imagePath = StringUtils.getImgSrc(text);
                            //插入空的EditText，以便在图片前后插入文字
                            editDiaryBody.addEditTextAtIndex(editDiaryBody.getLastIndex(), "");
                            editDiaryBody.addImageViewAtIndex(editDiaryBody.getLastIndex(), imagePath);
                        } else {
                            editDiaryBody.addEditTextAtIndex(editDiaryBody.getLastIndex(), text);
                        }
                    }
                });
    }

    /**
     * 显示数据
     */
    protected void showEditData(Subscriber<? super String> subscriber, String html) {
        try{
            List<String> textList = StringUtils.cutStringByImgTag(html);
            for (int i = 0; i < textList.size(); i++) {
                String text = textList.get(i);
                subscriber.onNext(text);
            }
            subscriber.onCompleted();
        }catch (Exception e){
            e.printStackTrace();
            subscriber.onError(e);
        }
    }

    /**
     * 设置退出时对话框
     */
    private void showDialog(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("退出")//设置对话框的标题
                .setMessage("您还没有保存，是否要保存内容")//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("点击了取消",false);
                        dialog.dismiss();
                        finish();
                    }
                })
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("点击了保存",false);
                        dialog.dismiss();
                        saveDiaryData();
                        finish();
                    }
                }).create();
        dialog.show();
    }

}
