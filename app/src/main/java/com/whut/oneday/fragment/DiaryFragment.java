package com.whut.oneday.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whut.oneday.R;
import com.whut.oneday.adapter.DiaryRecyclerViewAdapter;
import com.whut.oneday.adapter.MemoRecyclerViewAdapter;
import com.whut.oneday.entity.Diary;
import com.whut.oneday.entity.Memo;

import java.sql.Timestamp;
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
    private LinearLayoutManager linearLayoutManager;
    private DiaryRecyclerViewAdapter adapter;
    private List<Diary> diary;
    //option
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_diary, container, false);
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
        adapter = new DiaryRecyclerViewAdapter(diary);
        recyclerView.setAdapter(adapter);
    }

    private void reFresh() {
        adapter.notifyDataSetChanged();
    }

    private void initdata(){
        if(diary!=null){
            diary.clear();
        }else{
            diary=new ArrayList<>();
        }
        for(int i=1;i<10;i++){
            Diary temp=new Diary();
            temp.setCreatestamp(new Timestamp(System.currentTimeMillis()));
            diary.add(temp);
        }
    }
}
