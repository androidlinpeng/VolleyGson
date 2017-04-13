package com.example.volley;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class MyErrorListener implements Response.ErrorListener {

    private Object object;
    private Handler handler;
    private int what;

    public MyErrorListener(Object object, Handler handler, int what) {
        this.object = object;
        this.handler = handler;
        this.what = what;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("volleyError", error.getMessage() + "");
        object = error.getMessage();
        Message message = new Message();
        message.what = what;
        message.obj = object;
        handler.sendMessage(message);
    }
}
