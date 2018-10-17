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

import com.whut.oneday.R;
import com.whut.oneday.activity.EditDiaryActivity;
import com.whut.oneday.adapter.DiaryRecyclerViewAdapter;
import com.whut.oneday.entity.Diary;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryFragment extends Fragment {
    @InjectView(R.id.diary_recyclerview)
    RecyclerView recyclerView;
    @InjectView(R.id.diary_add)
    FloatingActionButton diaryAdd;
    private LinearLayoutManager linearLayoutManager;
    private DiaryRecyclerViewAdapter adapter;
    private List<Diary> diary;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);
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

        initData();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new DiaryRecyclerViewAdapter(diary, getContext());
        recyclerView.setAdapter(adapter);

    }

    private void reFresh() {
        adapter.notifyDataSetChanged();
    }

    private void initData() {
        //初始化监听
        diaryAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditDiaryActivity.class);
                startActivity(intent);
            }
        });
        if (diary != null) {
            diary.clear();
        } else {
            diary = new ArrayList<>();
        }
        for (int i = 1; i < 10; i++) {
            Diary temp = new Diary();
            String s = i + "<img src=\"/storage/emulated/0/XRichText/1539680220390-\"/>it's nice to sleep.<img src=\"/storage/emulated/0/XRichText/1539680363764-\"/>Ahhhhhhhhhhhhhhhhhhh!";
            temp.setDiaryID(1);
            temp.setUserID(1);
            temp.setBody(s);
            temp.setMood("开心");
            temp.setWeather("晴");
            temp.setCreatestamp(new Timestamp(System.currentTimeMillis()));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            temp.setDate(format.format(temp.getCreatestamp()));
            diary.add(temp);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
