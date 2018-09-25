package com.whut.oneday.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.whut.oneday.BaseActivity;
import com.whut.oneday.R;
import com.whut.oneday.adapter.BillOverViewAdapter;
import com.whut.oneday.entity.BillDesc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static java.lang.Math.abs;

public class BillOverviewActivity extends BaseActivity {

    @InjectView(R.id.bill_overview_toolbar)
    Toolbar toolbar;
    @InjectView(R.id.bill_overview_year)
    TextView Year;
    @InjectView(R.id.bill_overview_month)
    TextView Month;
    @InjectView(R.id.bill_overview_et_income)
    TextView Income;
    @InjectView(R.id.bill_overview_et_expenditure)
    TextView Expenditure;
    @InjectView(R.id.bill_overview_list)
    RecyclerView billOverviewList;
    @InjectView(R.id.bill_pie_chart)
    PieChart billPieChart;

    BillOverViewAdapter mAdapter;
    List<BillDesc> list;


    //退出程序标识
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_overview);
        ButterKnife.inject(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        hideStatus();

        PieData mPieData = getPieData(4, 100);
        showChart(billPieChart, mPieData);

        int count = mPieData.getEntryCount();
        //计算饼状图旋转角度
        billPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String name="";
                float x = e.getX();
                float y = e.getY();
                Object o = e.getData();
                List<PieEntry> entries = billPieChart.getData().getDataSet().getEntriesForXValue(0);

                float sum = 0;
                for (PieEntry entry : entries) {
                    sum += entry.getY();
                }
                float angle = 0;
                for (PieEntry entry : entries) {
                    if (entry != e) {
                        angle += entry.getY();
                    } else {
                        name=entry.getLabel();
                        angle += entry.getY() / 2;
                        break;
                    }
                }
                angle = (angle / sum) * 360;
                float from = billPieChart.getRotationAngle();
                if (angle > 180) {
                    angle = 360 - angle;
                } else {
                    angle = -angle;
                }
                float to = angle + 90;
                if(abs(from-to)>180)
                    from=from-360;
                if (from != to){
                    billPieChart.spin(1000, from, to, Easing.EasingOption.EaseInCubic);
                    changeData(name);
                }
            }

            @Override
            public void onNothingSelected() {
                changeData("全部");
            }
        });

        //初始化页面数据
        initWindow();
        //初始化list数据
        initData();

        //设置RecyclerView管理器
        billOverviewList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //初始化适配器
        mAdapter = new BillOverViewAdapter(list);
        //设置添加或删除item时的动画，这里使用默认动画
        billOverviewList.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        billOverviewList.setAdapter(mAdapter);
    }

    //初始化也谜案数据
    private void initWindow() {
        String s = "2018年";
        Year.setText(s);
        s = "9月";
        Month.setText(s);
        s = "0.00";
        Income.setText(s);
        Expenditure.setText(s);
    }

    //初始化数据函数
    private void initData() {
        BillDesc bill;
        if(list==null)
            list = new ArrayList<>();
        list.clear();
        for (int i = 0; i < 7; i++) {
            bill = new BillDesc("全部", "餐饮", -10.00, new Date(), "");
            list.add(bill);
        }
    }

    private void changeData(String change) {
        BillDesc bill;
        list.clear();
        for (int i = 0; i < 7; i++) {
            bill = new BillDesc(change, "餐饮", -10.00, new Date(), "");
            list.add(bill);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            showToast("再次点击返回退出程序", BASE_LONG);
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
            Process.killProcess(Process.myPid());
        }
    }

    //显示饼状图
    private void showChart(PieChart pieChart, PieData pieData) {
        //设置无数据时显示文字
        pieChart.setNoDataText("没有数据可显示");

        //设置右下方显示文字
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);

        pieChart.setHighlightPerTapEnabled(true);//设置旋转的时候点中的tab是否高亮(默认为true)

        pieChart.setEntryLabelTextSize(20);

        pieChart.setHoleRadius(50f);  //空洞半径
        pieChart.setTransparentCircleRadius(55f); // 半透明圈
        //pieChart.setHoleRadius(0)  //实心圆

        // mChart.setDrawYValues(true);
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(false); //不可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(false);  //显示成百分比
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);

        pieChart.setCenterText("支出情况");  //饼状图中间的文字

        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

        //不显示比例图
        Legend mLegend = pieChart.getLegend();
        mLegend.setEnabled(false);

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);

        pieChart.invalidate();
    }

    private PieData getPieData(int count, float range) {
        List<PieEntry> entries = new ArrayList<>();  //yVals用来表示封装每个饼块的实际数据

        // 饼图数据
        float quarterly1 = 1;
        float quarterly2 = 1;
        float quarterly3 = 2;
        float quarterly4 = 3;

        entries.add(new PieEntry(quarterly1, "饮食"));
        entries.add(new PieEntry(quarterly2, "出行"));
        entries.add(new PieEntry(quarterly3, "住宿"));
        entries.add(new PieEntry(quarterly4, "其他"));

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(entries, ""/*显示在比例图上*/);
        pieDataSet.setSliceSpace(2f); //设置个饼状图之间的距离

        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色
        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(57, 135, 200));

        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度
        pieDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return "";
            }
        });

        return new PieData(pieDataSet);
    }
}
