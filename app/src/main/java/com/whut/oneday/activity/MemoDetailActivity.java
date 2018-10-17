package com.whut.oneday.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.sendtion.xrichtext.RichTextView;
import com.whut.oneday.BaseActivity;
import com.whut.oneday.R;
import com.whut.oneday.entity.Memo;
import com.whut.oneday.tools.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MemoDetailActivity extends BaseActivity {

    @InjectView(R.id.memo_details_title)
    TextView memoDetailsTitle;
    @InjectView(R.id.memo_details_toolbar)
    Toolbar memoDetailsToolbar;
    @InjectView(R.id.memo_details_name)
    TextView memoDetailsName;
    @InjectView(R.id.memo_details_time)
    TextView memoDetailsTime;
    @InjectView(R.id.memo_details_body)
    RichTextView memoDetailsBody;

    private Memo memo;

    private ProgressDialog loadingDialog;
    private Subscription subsLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_detail);
        ButterKnife.inject(this);

        //隐藏状态栏
        hideStatus();

        //设置supportActionbar
        memoDetailsToolbar.setTitle("");
        memoDetailsTitle.setText("备忘录详情");
        setSupportActionBar(memoDetailsToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //左上角图标可点击
            actionBar.setHomeButtonEnabled(true);
            //显示图标
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //设置页面数据
        initData();

        //显示页面数据
        if(memo!=null)
            showDataSync(memo.getBody());
        else
            showDataSync("无内容显示");
    }

    /**
     * 初始化页面数据
     */
    private void initData(){
        //获取diary
        Intent intent=getIntent();
        if(intent==null)
            return;
        memo=intent.getParcelableExtra("memo_detail");

        if(memo==null){
            return;
        }
        memoDetailsName.setText(memo.getTitle());
        memoDetailsTime.setText(new SimpleDateFormat("yyyy/MM/dd").format(memo.getCreatestamp()));

        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage("数据加载中...");
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_diary,menu);
        return true;
    }

    /**
     * 编辑按钮点击响应
     * @param item 被点击的按钮
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.detail_diary_edit:
                Intent intent=new Intent(MemoDetailActivity.this,EditMemoActivity.class);
                intent.putExtra("edit_memo",memo);
                startActivity(intent);
                finish();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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
                            memoDetailsBody.addImageViewAtIndex(memoDetailsBody.getLastIndex(), imagePath);
                        } else {
                            memoDetailsBody.addTextViewAtIndex(memoDetailsBody.getLastIndex(), text);
                        }
                    }
                });

    }

    /**
     * 显示数据
     * @param html
     */
    private void showEditData(Subscriber<? super String> subscriber, String html) {
        try {
            List<String> textList = StringUtils.cutStringByImgTag(html);
            for (int i = 0; i < textList.size(); i++) {
                String text = textList.get(i);
                subscriber.onNext(text);
            }
            subscriber.onCompleted();
        } catch (Exception e){
            e.printStackTrace();
            subscriber.onError(e);
        }
    }
}
