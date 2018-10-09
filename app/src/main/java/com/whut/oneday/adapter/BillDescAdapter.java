package com.whut.oneday.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.whut.oneday.R;
import com.whut.oneday.entity.Bill;
import com.whut.oneday.tools.Content;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * billoverview页面list的adapter
 */
public class BillDescAdapter extends RecyclerView.Adapter<BillDescAdapter.ViewHolder> {
    private List<Bill> list;

    public BillDescAdapter(List<Bill> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill_desc, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //对item中的控件赋值
        Bill billDesc=list.get(position);
        // TODO: 2018/9/6 写一个函数根据分类返回图片 
//        holder.itemBillImage.setImageDrawable();
        holder.itemBillName.setText(billDesc.getTitle());
        holder.itemBillClassify.setText(Content.billTypeMap.get(billDesc.getBillType()));
        SimpleDateFormat format=new SimpleDateFormat("MM-dd HH:mm");
        String time=format.format(billDesc.getCreatestamp());
        holder.itemBillTime.setText(time);
        String money;
        if(billDesc.getIOE()){
            money="+"+billDesc.getMoney();
        }else {
            money="-"+billDesc.getMoney();
        }
        holder.itemBillMoney.setText(money);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemBillImage;
        TextView itemBillName;
        TextView itemBillClassify;
        TextView itemBillTime;
        TextView itemBillMoney;

        ViewHolder(View view) {
            super(view);
            //定位控件
            itemBillImage=(ImageView)view.findViewById(R.id.item_bill_image);
            itemBillName=(TextView)view.findViewById(R.id.item_bill_name);
            itemBillClassify=(TextView)view.findViewById(R.id.item_bill_classify);
            itemBillTime=(TextView)view.findViewById(R.id.item_bill_time);
            itemBillMoney=(TextView)view.findViewById(R.id.item_bill_money);
        }
    }
}
