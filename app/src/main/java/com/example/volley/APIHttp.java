package com.example.volley;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.MyApplication;
import com.example.data.ResultData;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by liang on 2017/4/12.
 */

public class APIHttp {

    private static final String TAG = "APIHttp";

    public final static String COOKIE ="Cookie";
    public final static String SET_COOKIE ="Set-Cookie";

    //不带Cookie的Get请求
    public static void get(String url,Handler handler,int what,RequestQueue requestQueue){
        ResultData data=new ResultData();
        StringRequest requestGet = new StringRequest(Request.Method.GET,url,new MyListener(new Object(), handler, what,data), new MyErrorListener());
        requestQueue.add(requestGet);
    }

    //带Cookie的Get请求
    public static ResultData getWithCookit(String url, Handler handler, int what, RequestQueue requestQueue){
        ResultData data=new ResultData();
        StringRequest requestPost = new StringRequest(Request.Method.GET,url,new MyListener(new Object(), handler, what,data), new MyErrorListener()){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("cookie", APIHttp.getSettingNote(APIHttp.SET_COOKIE));
                return hashMap;
            }
        };
        requestQueue.add(requestPost);
        return data;
    }

    //获取Cookie的带参数Post请求
    public static ResultData postSetCookie(String url, Handler handler, int what, RequestQueue requestQueue, final Map values){
        ResultData data=new ResultData();
        StringRequest requestPost = new StringRequest(Request.Method.POST,url,new MyListener(new Object(), handler, what,data), new MyErrorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return values;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    Map<String, String> responseHeaders = response.headers;
                    String rawCookies = responseHeaders.get("Set-Cookie");
                    saveSettingNote(SET_COOKIE,rawCookies);
                    String dataString = new String(response.data, "UTF-8");
                    return Response.success(dataString, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };
        requestQueue.add(requestPost);
        return data;
    }

    //带Cookie的带参数Post请求
    public static ResultData postWithCookit(String url, Handler handler, int what, RequestQueue requestQueue, final Map values){
        ResultData data=new ResultData();
        StringRequest requestPost = new StringRequest(Request.Method.POST,url,new MyListener(new Object(), handler, what,data), new MyErrorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return values;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("cookie", APIHttp.getSettingNote(APIHttp.SET_COOKIE));
                return hashMap;
            }
        };
        requestQueue.add(requestPost);
        return data;
    }

    //不带Cookie的带参数Post请求
    public static ResultData post(String url, Handler handler, int what, RequestQueue requestQueue, final Map values){
        ResultData data=new ResultData();
        StringRequest requestPost = new StringRequest(Request.Method.POST,url,new MyListener(new Object(), handler, what,data), new MyErrorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return values;
            }
        };
        requestQueue.add(requestPost);
        return data;
    }


    //不带Cookie的Put请求
    public static ResultData put(String url, Handler handler, int what, RequestQueue requestQueue, final Map values){
        ResultData data=new ResultData();
        MyListener myListener = new MyListener(new Object(), handler, what,data);
        StringRequest requestPost = new StringRequest(Request.Method.PUT,url,myListener, new MyErrorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return values;
            }
        };
        requestQueue.add(requestPost);
        return data;
    }
    //带Cookie的Put请求
    public static ResultData putWithCookit(String url, Handler handler, int what, RequestQueue requestQueue, final Map values){
        ResultData data=new ResultData();
        StringRequest requestPost = new StringRequest(Request.Method.PUT,url,new MyListener(new Object(), handler, what,data), new MyErrorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return values;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("cookie", APIHttp.getSettingNote(APIHttp.SET_COOKIE));
                return hashMap;
            }
        };
        requestQueue.add(requestPost);
        return data;
    }
    //带Cookie的Delete请求
    public static ResultData deleteWithCookit(String url, Handler handler, int what, RequestQueue requestQueue){
        ResultData data=new ResultData();
        StringRequest requestPost = new StringRequest(Request.Method.DELETE,url,new MyListener(new Object(), handler, what,data), new MyErrorListener()){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("cookie", APIHttp.getSettingNote(APIHttp.SET_COOKIE));
                return hashMap;
            }
        };
        requestQueue.add(requestPost);
        return data;
    }

    /**
     * decodeConfig：图片存储模式
     * Bitmap.Config ARGB_4444：每个像素占四位，即A=4，R=4，G=4，B=4，那么一个像素点占4+4+4+4=16位
     * Bitmap.Config ARGB_8888：每个像素占四位，即A=8，R=8，G=8，B=8，那么一个像素点占8+8+8+8=32位
     * Bitmap.Config RGB_565：每个像素占四位，即R=5，G=6，B=5，没有透明度，那么一个像素点占5+6+5=16位
     * Bitmap.Config ALPHA_8：每个像素占四位，只有透明度，没有颜色。
     */
    public static ResultData Image(RequestQueue requestQueue,String url, Handler handler, int what, int imageViewWidth, int imageViewHeiget, Bitmap.Config decodeConfig){
        ResultData data=new ResultData();
        ImageRequest request = new ImageRequest(url,new MyImageListener(new Object(), handler, what),imageViewWidth, imageViewHeiget, decodeConfig, new MyErrorListener());
        requestQueue.add(request);
        return data;
    }

    public static ResultData ImageWithCache(RequestQueue requestQueue,String url, Handler handler, int what, int imageViewWidth, int imageViewHeiget, Bitmap.Config decodeConfig){
        ResultData data=new ResultData();
        ImageRequest request = new ImageRequest(url,new MyImageListener(new Object(), handler, what),imageViewWidth, imageViewHeiget, decodeConfig, new MyErrorListener());
        requestQueue.add(request);
        return data;
    }



    public static void saveSettingNote(String s,String saveData){//保存设置
        SharedPreferences.Editor note = MyApplication.getInstance().getSharedPreferences(COOKIE,MODE_PRIVATE).edit();
        note.putString(s, saveData);
        note.commit();
    }
    public static String getSettingNote(String s){//获取保存设置
        SharedPreferences read = MyApplication.getInstance().getSharedPreferences(COOKIE, MODE_PRIVATE);
        Log.i(TAG,"getCookie:"+read.getString(s, ""));
        return read.getString(s, "");
    }

}
