package com.whut.oneday.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.whut.oneday.R;
import com.whut.oneday.entity.Memo;

import java.text.SimpleDateFormat;
import java.util.List;

public class MemoRecyclerViewAdapter extends RecyclerView.Adapter<MemoRecyclerViewAdapter.ViewHolder> {
    private List<Memo> list;
    public MemoRecyclerViewAdapter(List<Memo> list) {
        this.list = list;
    }
    @Override
    public MemoRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memo_text, parent, false);
            final MemoRecyclerViewAdapter.ViewHolder holder = new MemoRecyclerViewAdapter.ViewHolder(view);
            return holder;
    }
    @Override
    public void onBindViewHolder(MemoRecyclerViewAdapter.ViewHolder holder, int position) {
        //对item中的控件赋值
        Memo memo=list.get(position);
        holder.title.setText(memo.getTitle());
        SimpleDateFormat format=new SimpleDateFormat("MM-dd HH:mm");
        String time=format.format(memo.getCreatestamp());
        holder.createstamp.setText(time);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,createstamp;
        View view;
        ViewHolder(View view) {
            super(view);
            this.view=view;
            title = (TextView) view.findViewById(R.id.item_memo_title);
            createstamp=(TextView) view.findViewById(R.id.item_memo_createstamp);
        }
    }
}
