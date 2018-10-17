package com.whut.oneday.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.whut.oneday.R;
import com.whut.oneday.activity.DiaryDetailActivity;
import com.whut.oneday.entity.Diary;

import java.text.SimpleDateFormat;
import java.util.List;

public class DiaryRecyclerViewAdapter extends RecyclerView.Adapter<DiaryRecyclerViewAdapter.ViewHolder> {

    private List<Diary> list;
    private Context context;

    public DiaryRecyclerViewAdapter(List<Diary> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public DiaryRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary_text, parent, false);
        final DiaryRecyclerViewAdapter.ViewHolder holder = new DiaryRecyclerViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DiaryRecyclerViewAdapter.ViewHolder holder, final int position) {
        //对item中的控件赋值
        Diary diary = list.get(position);
        // FIXME: 2018/10/16 选择图片，天气心情对号入座
//        holder.weatherIcon.setImageDrawable();
//        holder.moodIcon.setImageDrawable();
        holder.moodText.setText(diary.getMood());
        holder.weatherText.setText(diary.getWeather());
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        String time = format.format(diary.getCreatestamp());
        holder.createstamp.setText(time);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DiaryDetailActivity.class);
                intent.putExtra("diary_detail", list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView createstamp, moodText, weatherText;
        ImageView moodIcon, weatherIcon;
        View view;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            createstamp = (TextView) view.findViewById(R.id.item_diary_createstamp);
            moodText = (TextView) view.findViewById(R.id.item_diary_mood_text);
            moodIcon = (ImageView) view.findViewById(R.id.item_diary_mood_icon);
            weatherText = (TextView) view.findViewById(R.id.item_diary_weather_text);
            weatherIcon = (ImageView) view.findViewById(R.id.item_diary_weather_icon);
        }
    }
}
