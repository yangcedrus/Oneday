package com.whut.oneday.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whut.oneday.R;
import com.whut.oneday.adapter.FragmentViewPagerAdapter;
import com.whut.oneday.entity.Bill;
import com.whut.oneday.tools.Content;
import com.whut.oneday.tools.MonthPickerDialog;
import com.whut.oneday.tools.NoScrollViewPager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.whut.oneday.tools.Content.billMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillFragment extends Fragment {
    @InjectView(R.id.bill_year)
    TextView billYear;
    @InjectView(R.id.bill_month)
    TextView billMonth;
    @InjectView(R.id.bill_et_income)
    TextView billIncome;
    @InjectView(R.id.bill_et_expenditure)
    TextView billExpenditure;
    @InjectView(R.id.bill_viewPager)
    NoScrollViewPager billViewPager;
    @InjectView(R.id.bill_tab_detail)
    TextView billTabDetail;
    @InjectView(R.id.bill_tab_table)
    TextView billTabTable;
    @InjectView(R.id.bill_add_one)
    TextView billAddOne;

    private FragmentViewPagerAdapter viewPagerAdapter;
    private MonthPickerDialog dialog;
    private List<Bill> list;
    private static Integer selectYear, selectMonth;

    public BillFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
        initViews();
        //数据存入Map
        billMap.put(selectYear * 100 + selectMonth, list);

        //ViewPager操作
        billViewPager.setNoScroll(true);

        viewPagerAdapter = new FragmentViewPagerAdapter(getChildFragmentManager());
        billViewPager.setAdapter(viewPagerAdapter);
        List<Fragment> list = new ArrayList<>();
        list.add(new BillDetailFragment());
        list.add(new BillTableFragment());
        viewPagerAdapter.setList(list);

        billTabDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //修改文字颜色
                billTabDetail.setTextColor(getResources().getColor(R.color.yellow));
                billTabTable.setTextColor(getResources().getColor(R.color.black));
                billViewPager.setCurrentItem(0);
            }
        });

        billTabTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //修改文字颜色
                billTabDetail.setTextColor(getResources().getColor(R.color.black));
                billTabTable.setTextColor(getResources().getColor(R.color.yellow));
                billViewPager.setCurrentItem(1);
            }
        });

        //日期选择
        initDialog();
        billYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        billMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void initDialog() {
        dialog = new MonthPickerDialog(getContext());
        dialog.setOnMonthPickListener(new MonthPickerDialog.OnMonthPickListener() {
            @Override
            public void pickMonth(int year, int month) {
                selectYear = year;
                selectMonth = month + 1;

                billYear.setText(String.format("%d年", selectYear));
                billMonth.setText(String.format("%d月", selectMonth));
                dialog.hide();

                // TODO: 2018/9/29 获取新选择的月份数据，保存到缓存中
                list = getBillList(selectYear, selectMonth);
                if (Content.billMap.get(selectYear * 100 + selectMonth) == null) {
                    Content.billMap.put(selectYear * 100 + selectMonth, list);
                } else {
                    Content.billMap.remove(selectYear * 100 + selectMonth);
                    Content.billMap.put(selectYear * 100 + selectMonth, list);
                }
            }
        });
    }

    //初始化数据
    private void initData() {
        Bill bill;
        if (list == null)
            list = new ArrayList<>();
        list.clear();
        for (int i = 0; i < 7; i++) {
            bill = new Bill(1000000, 1000000, "全部", false, 1, 10.00, "");
            list.add(bill);
        }
    }

    private void initViews() {
        //设置年月
        Calendar calendar = Calendar.getInstance();
        selectYear = calendar.get(Calendar.YEAR);
        selectMonth = calendar.get(Calendar.MONTH) + 1;
        String year = Integer.toString(selectYear);
        String month = Integer.toString(selectMonth);
        billYear.setText(year + "年");
        billMonth.setText(month + "月");

        //设置收支
        Double in = 0.00, out = 0.00;
        for (Bill desc : list) {
            if (desc.getIOE()) {
                in += desc.getMoney();
            } else {
                out += desc.getMoney();
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");
        billIncome.setText(df.format(in));
        billExpenditure.setText(df.format(out));
        
        //设置“记一笔”按钮响应
        billAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2018/10/9 跳转记一笔页面 
            }
        });
    }

    //发送请求获取选中年月的信息
    private List<Bill> getBillList(Integer Year, Integer Month) {
        // TODO: 2018/9/29 网络请求
        return null;
    }

    //获取选中的年份
    public static Integer getYear() {
        Calendar date = Calendar.getInstance();
        date.set(selectYear, selectMonth, 1);
        return date.get(Calendar.YEAR);
    }

    //获取选中的月份
    public static Integer getMonth() {
        Calendar date = Calendar.getInstance();
        date.set(selectYear, selectMonth, 1);
        return date.get(Calendar.MONTH);
    }

}
