package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class MyViewHolder {

    private View mView;//用于返回给 getview 的对象

    public MyViewHolder(Context context, int layoutResId) {
        mView = LayoutInflater.from(context).inflate(layoutResId, null);
        mView.setTag(this);

    }

    public View getmView() {
        return mView;
    }

    public static MyViewHolder getHolder(View convertView, Context context, int layoutResId) {
        MyViewHolder myViewHolder = null;
        //根据具体情况来判断是否创建新的 viewholder
        if (convertView == null) {
            myViewHolder = new MyViewHolder(context, layoutResId);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        return myViewHolder;
    }

    public View findView(int id) {
        return mView.findViewById(id);
    }
}