package com.whut.oneday.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.whut.oneday.R;
import com.whut.oneday.activity.EditMemoActivity;
import com.whut.oneday.adapter.MemoRecyclerViewAdapter;
import com.whut.oneday.entity.Memo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemoFragment extends Fragment {
    @InjectView(R.id.memo_recyclerview)
    RecyclerView recyclerView;
    @InjectView(R.id.memo_search)
    SearchView memoSearch;
    @InjectView(R.id.memo_add)
    FloatingActionButton memoAdd;
    private LinearLayoutManager linearLayoutManager;
    private MemoRecyclerViewAdapter adapter;
    private List<Memo> memo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_memo, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initdata();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MemoRecyclerViewAdapter(memo, getContext());
        recyclerView.setAdapter(adapter);
    }

    private void reFresh() {
        adapter.notifyDataSetChanged();
    }

    private void initdata() {
        //初始化监听
        memoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditMemoActivity.class);
                startActivity(intent);
            }
        });

        if (memo != null) {
            memo.clear();
        } else {
            memo = new ArrayList<>();
        }
        for (int i = 1; i < 10; i++) {
            // FIXME: 2018/10/17 获取备忘录数据
            Memo temp = new Memo();
            temp.setMemoID(1);
            temp.setUserID(1);
            temp.setTitle("备忘录" + i);
            temp.setBody("晚上记得要吃饭。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
            temp.setCreatestamp(new Timestamp(System.currentTimeMillis()));
            temp.setRemindstamp(new Timestamp(System.currentTimeMillis()));
            temp.setDeletestamp(new Timestamp(System.currentTimeMillis()));
            temp.setStatus(0);
            memo.add(temp);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
