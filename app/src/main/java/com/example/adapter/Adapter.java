package com.example.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.model.InfoEntity;
import com.example.myvolleygson.R;

import java.util.List;


public class Adapter extends MyBaseAdapter<InfoEntity> {

    private List<InfoEntity> list;

    public Adapter(List<InfoEntity> list, Context context, int layoutResId) {
        super(list, context, layoutResId);
        this.list = list;
    }

    @Override
    public void setData(MyViewHolder holder, int position) {
        TextView tViewTitle = (TextView) holder.findView(R.id.tv);
        tViewTitle.setText("Get:"+list.get(position).getTitle());

    }
}