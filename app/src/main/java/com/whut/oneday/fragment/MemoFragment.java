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
        private LinearLayoutManager linearLayoutManager;
        private MemoRecyclerViewAdapter adapter;
        private List<Memo> memo;
        //option


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_memo, container, false);
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
        adapter = new MemoRecyclerViewAdapter(memo);
        recyclerView.setAdapter(adapter);
    }

    private void reFresh() {
        adapter.notifyDataSetChanged();
    }

    private void initdata(){
        if(memo!=null){
            memo.clear();
        }else{
            memo=new ArrayList<>();
        }
        for(int i=1;i<10;i++){
            Memo temp=new Memo();
            temp.setTitle("备忘录");
            temp.setCreatestamp(new Timestamp(System.currentTimeMillis()));
            memo.add(temp);
        }
    }
}
