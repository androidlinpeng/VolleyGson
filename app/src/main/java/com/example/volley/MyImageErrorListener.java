package com.example.volley;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class MyImageErrorListener implements Response.ErrorListener {
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("volleyError", error.getMessage() + "");
    }
}
