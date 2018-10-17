package com.whut.oneday.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.sendtion.xrichtext.RichTextEditor;
import com.whut.oneday.BaseActivity;
import com.whut.oneday.R;
import com.whut.oneday.entity.Diary;
import com.whut.oneday.entity.Memo;
import com.whut.oneday.tools.CommonUtil;
import com.whut.oneday.tools.StringUtils;

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

public class EditMemoActivity extends BaseActivity {

    @InjectView(R.id.edit_memo_title)
    TextView editMemoTitle;
    @InjectView(R.id.edit_memo_toolbar)
    Toolbar editMemoToolbar;
    @InjectView(R.id.edit_memo_name)
    EditText editMemoName;
    @InjectView(R.id.edit_memo_time)
    TextView editMemoTime;
    @InjectView(R.id.edit_memo_body)
    RichTextEditor editMemoBody;

    private boolean saveFlag = false;   //true为已经点击保存，false为未点击保存
    private ProgressDialog loadingDialog;
    private Subscription subsLoading;
    private Memo memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_memo);
        ButterKnife.inject(this);

        //隐藏状态栏
        hideStatus();

        //设置页面数据
        initData();
        if(memo!=null)
            showDataSync(memo.getBody());

        //设置supportActionbar
        editMemoToolbar.setTitle("");
        String s;
        if (memo==null)
            s = "新建备忘录";
        else
            s = "编辑备忘录";
        editMemoTitle.setText(s);
        setSupportActionBar(editMemoToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //左上角图标可点击
            actionBar.setHomeButtonEnabled(true);
            //显示图标
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initData(){
        //获取传递的diary信息
        Intent intent=getIntent();
        if (intent==null){
            return;
        }
        memo=(Memo)intent.getParcelableExtra("edit_memo");

        if(memo==null){
            Date date=new Date();
            SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
            String datestring=format.format(date);
            editMemoTime.setText(datestring);
        }else{
            editMemoTime.setText(new SimpleDateFormat("yyyy/MM/dd").format(memo.getCreatestamp()));
            editMemoName.setText(memo.getTitle());
        }

        //读取数据进程框
        loadingDialog=new ProgressDialog(this);
        loadingDialog.setMessage("读取中...");
        loadingDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 虚拟返回按键响应
     */
    @Override
    public void onBackPressed() {
        if(!saveFlag)
            showDialog();
        else
            finish();
        super.onBackPressed();
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
            case android.R.id.home:
                if(!saveFlag)
                    showDialog();
                else {
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 保存日记信息
     */
    private void saveDiaryData(){
        //如果是新建，则添加创建时间
        if(memo==null){
            memo=new Memo();
            memo.setCreatestamp(new Timestamp(System.currentTimeMillis()));
            memo.setStatus(0);
        }
        //否则，只更新以下信息
        if(editMemoTitle.getText().toString().length()==0){
            memo.setTitle(getEditData().substring(0,8));    //截取正文前8字符作为标题
        }else
            memo.setTitle(editMemoTitle.getText().toString());
        memo.setBody(getEditData());
        // FIXME: 2018/10/17 提醒时间
        memo.setRemindstamp(new Timestamp(System.currentTimeMillis()));

        // TODO: 2018/10/16 保存备忘录，数据库/服务器

        saveFlag=true;
    }

    /**
     * 负责处理编辑数据提交等事宜，请自行实现
     */
    private String getEditData() {
        List<RichTextEditor.EditData> editList = editMemoBody.buildEditData();
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
                        editMemoBody.addEditTextAtIndex(editMemoBody.getLastIndex(), "");
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
                            editMemoBody.addEditTextAtIndex(editMemoBody.getLastIndex(), "");
                            editMemoBody.addImageViewAtIndex(editMemoBody.getLastIndex(), imagePath);
                        } else {
                            editMemoBody.addEditTextAtIndex(editMemoBody.getLastIndex(), text);
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
