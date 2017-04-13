package com.example.volley;

import android.os.Handler;
import android.os.Message;

import com.android.volley.Response;

/**
 * Created by jackiechan on 15/11/9.
 */
public class MyListener implements Response.Listener<String> {

    private Object object;
    private Handler handler;
    private int what;

    public MyListener(Object object, Handler handler, int what) {
        this.object = object;
        this.handler = handler;
        this.what = what;
    }


    @Override
    public void onResponse(String response) {
        //除了用来接收数据的对象不一样
//        object = new Gson().fromJson(response, object.getClass());
        object = response;
        Message message = new Message();
        message.what = what;
        message.obj = object;
        handler.sendMessage(message);
    }

}
