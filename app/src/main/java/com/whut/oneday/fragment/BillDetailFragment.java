package com.whut.oneday.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whut.oneday.R;
import com.whut.oneday.adapter.BillDescAdapter;
import com.whut.oneday.entity.Bill;
import com.whut.oneday.tools.Content;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillDetailFragment extends Fragment {
    @InjectView(R.id.bill_detail_list)
    RecyclerView billDetailList;

    List<Bill> list;
    BillDescAdapter mAdapter;

    public BillDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill_detail, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //初始化list数据
        initData();

        //设置RecyclerView管理器
        billDetailList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //初始化适配器
        mAdapter = new BillDescAdapter(list);
        //设置添加或删除item时的动画，这里使用默认动画
        billDetailList.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        billDetailList.setAdapter(mAdapter);
    }

    //初始化数据函数
    private void initData() {
        Bill bill;
        if (list == null)
            list = new ArrayList<>();
        list.clear();
        Integer year=BillFragment.getYear();
        Integer month=BillFragment.getMonth();
        list= Content.billMap.get(year*100+month);
        //本地获取不到信息,先初始化
        if(list.size()==0){
            for (int i = 0; i < 7; i++) {
                bill = new Bill(1000000,1000000,"全部", false,1, 10.00,"");
                list.add(bill);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
