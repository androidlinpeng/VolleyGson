package com.example.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.model.UserInfoEntity;
import com.example.myvolleygson.R;

import java.util.List;


public class Adapter2 extends MyBaseAdapter<UserInfoEntity> {

    private List<UserInfoEntity> list;

    public Adapter2(List<UserInfoEntity> list, Context context, int layoutResId) {
        super(list, context, layoutResId);
        this.list = list;
    }

    @Override
    public void setData(MyViewHolder holder, int position) {
        TextView tViewTitle = (TextView) holder.findView(R.id.tv);
        tViewTitle.setText("Post:"+list.get(position).getUsername());

    }
}