package com.whut.oneday.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whut.oneday.R;
import com.whut.oneday.entity.Diary;

import java.text.SimpleDateFormat;
import java.util.List;

public class DiaryRecyclerViewAdapter extends RecyclerView.Adapter<DiaryRecyclerViewAdapter.ViewHolder> {

    private List<Diary> list;
    public DiaryRecyclerViewAdapter(List<Diary> list) {
        this.list = list;
    }
    @Override
    public DiaryRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary_text, parent, false);
        final DiaryRecyclerViewAdapter.ViewHolder holder = new DiaryRecyclerViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DiaryRecyclerViewAdapter.ViewHolder holder, int position) {
        //对item中的控件赋值
        Diary diary=list.get(position);
        SimpleDateFormat format=new SimpleDateFormat("MM-dd HH:mm");
        String time=format.format(diary.getCreatestamp());
        holder.createstamp.setText(time);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView createstamp;
        View view;
        ViewHolder(View view) {
            super(view);
            this.view=view;
            createstamp=(TextView) view.findViewById(R.id.item_diary_createstamp);
        }
    }
}
