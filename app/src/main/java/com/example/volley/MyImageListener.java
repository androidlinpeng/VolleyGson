package com.example.volley;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.android.volley.Response;

/**
 * Created by jackiechan on 15/11/9.
 */
public class MyImageListener implements Response.Listener<Bitmap> {

    private Object object;
    private Handler handler;
    private int what;

    public MyImageListener(Object object, Handler handler, int what) {
        this.object = object;
        this.handler = handler;
        this.what = what;
    }

    @Override
    public void onResponse(Bitmap bitmap) {
        object = bitmap;
        Message message = new Message();
        message.what = what;
        message.obj = object;
        handler.sendMessage(message);
    }
}
