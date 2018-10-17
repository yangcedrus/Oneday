package com.whut.oneday.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.whut.oneday.R;
import com.whut.oneday.adapter.BillDescAdapter;
import com.whut.oneday.entity.Bill;
import com.whut.oneday.tools.Content;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static java.lang.Math.abs;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillTableFragment extends Fragment {
    @InjectView(R.id.bill_pie_chart)
    PieChart billPieChart;
    @InjectView(R.id.bill_overview_list)
    RecyclerView billOverviewList;

    List<Bill> list;
    BillDescAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill_table, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PieData mPieData = getPieData(4, 100);
        showChart(billPieChart, mPieData);

        int count = mPieData.getEntryCount();
        //计算饼状图旋转角度
        billPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String name = "";
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
                        name = entry.getLabel();
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
                if (abs(from - to) > 180)
                    from = from - 360;
                if (from != to) {
                    billPieChart.spin(1000, from, to, Easing.EasingOption.EaseInCubic);
                    changeData(name);
                }
            }

            @Override
            public void onNothingSelected() {
                changeData("全部");
            }
        });

        //初始化list数据
        initData();

        //设置RecyclerView管理器
        billOverviewList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //初始化适配器
        mAdapter = new BillDescAdapter(list);
        //设置添加或删除item时的动画，这里使用默认动画
        billOverviewList.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        billOverviewList.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    //初始化数据函数
    private void initData() {
        Bill bill;
        if (list == null)
            list = new ArrayList<>();
        list.clear();
        Integer year = BillFragment.getYear();
        Integer month = BillFragment.getMonth();
        list = Content.billMap.get(year * 100 + month);
        //本地获取不到信息,先初始化
        if (list.size() == 0) {
            for (int i = 0; i < 7; i++) {
                bill = new Bill(1000000, 1000000, "全部", false, 1, 10.00, "");
                list.add(bill);
            }
        }
    }

    private void changeData(String change) {
        Bill bill;
        list.clear();
        for (int i = 0; i < 7; i++) {
            bill = new Bill(1000000, 1000000, "全部", false, 1, 10.00, "");
            list.add(bill);
        }
        mAdapter.notifyDataSetChanged();
    }

    //显示饼状图
    private void showChart(PieChart pieChart, PieData pieData) {
        //设置无数据时显示文字
        pieChart.setNoDataText("没有数据可显示");

        //设置右下方不显示文字
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);

        pieChart.setHighlightPerTapEnabled(true);//设置旋转的时候点中的tab是否高亮(默认为true)

        pieChart.setEntryLabelTextSize(16);

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
        pieChart.setUsePercentValues(false);  //不显示成百分比
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

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
        float px = 5 * (metrics.densityDpi / 200f);
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
