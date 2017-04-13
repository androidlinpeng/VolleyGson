package com.example.volley;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.MyApplication;
import com.example.cache.DoubleCache;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by liang on 2017/4/12.
 */

public class APIHttp {

    private static final String TAG = "APIHttp";
    public final static String COOKIE = "Cookie";
    public final static String SET_COOKIE = "Set-Cookie";

    //不带Cookie的Get请求
    public static void get(String url,String requestTag,Handler handler, int what, RequestQueue requestQueue) {
        removeRequest(requestQueue,requestTag);
        StringRequest requestGet = new StringRequest(Request.Method.GET, url, new MyListener(new Object(), handler, what), new MyErrorListener(new Object(), handler, what));
        requestGet.setTag(requestTag);
        requestQueue.add(requestGet);
    }

    //带Cookie的Get请求
    public static void getWithCookit(String url,String requestTag, Handler handler, int what, RequestQueue requestQueue) {
        removeRequest(requestQueue,requestTag);
        StringRequest requestPost = new StringRequest(Request.Method.GET, url, new MyListener(new Object(), handler, what), new MyErrorListener(new Object(), handler, what)) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("cookie", APIHttp.getSettingNote(APIHttp.SET_COOKIE));
                return hashMap;
            }
        };
        requestPost.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestPost.setTag(requestTag);
        requestQueue.add(requestPost);
    }

    //获取Cookie的带参数Post请求
    public static void postSetCookie(String url, String requestTag, Handler handler, int what, RequestQueue requestQueue, final Map values) {
        removeRequest(requestQueue,requestTag);
        StringRequest requestPost = new StringRequest(Request.Method.POST, url, new MyListener(new Object(), handler, what), new MyErrorListener(new Object(), handler, what)) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return values;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    Map<String, String> responseHeaders = response.headers;
                    String rawCookies = responseHeaders.get("Set-Cookie");
                    saveSettingNote(SET_COOKIE, rawCookies);
                    String dataString = new String(response.data, "UTF-8");
                    return Response.success(dataString, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };
        requestPost.setTag(requestTag);
        requestQueue.add(requestPost);
    }

    //带Cookie的带参数Post请求
    public static void postWithCookit(String url, String requestTag, Handler handler, int what, RequestQueue requestQueue, final Map values) {
        removeRequest(requestQueue,requestTag);
        StringRequest requestPost = new StringRequest(Request.Method.POST, url, new MyListener(new Object(), handler, what), new MyErrorListener(new Object(), handler, what)) {
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
        requestPost.setTag(requestTag);
        requestQueue.add(requestPost);
    }

    //不带Cookie的带参数Post请求
    public static void post(String url, String requestTag, Handler handler, int what, RequestQueue requestQueue, final Map values) {
        removeRequest(requestQueue,requestTag);
        StringRequest requestPost = new StringRequest(Request.Method.POST, url, new MyListener(new Object(), handler, what), new MyErrorListener(new Object(), handler, what)) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return values;
            }
        };
        requestPost.setTag(requestTag);
        requestQueue.add(requestPost);
    }


    //不带Cookie的Put请求
    public static void put(String url, String requestTag, Handler handler, int what, RequestQueue requestQueue, final Map values) {
        removeRequest(requestQueue,requestTag);
        StringRequest requestPost = new StringRequest(Request.Method.PUT, url, new MyListener(new Object(), handler, what), new MyErrorListener(new Object(), handler, what)) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return values;
            }
        };
        requestPost.setTag(requestTag);
        requestQueue.add(requestPost);
    }

    //带Cookie的Put请求
    public static void putWithCookit(String url,String requestTag,  Handler handler, int what, RequestQueue requestQueue, final Map values) {
        removeRequest(requestQueue,requestTag);
        StringRequest requestPost = new StringRequest(Request.Method.PUT, url, new MyListener(new Object(), handler, what), new MyErrorListener(new Object(), handler, what)) {
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
        requestPost.setTag(requestTag);
        requestQueue.add(requestPost);
    }

    //带Cookie的Delete请求
    public static void deleteWithCookit(String url, String requestTag, Handler handler, int what, RequestQueue requestQueue) {
        removeRequest(requestQueue,requestTag);
        StringRequest requestPost = new StringRequest(Request.Method.DELETE, url, new MyListener(new Object(), handler, what), new MyErrorListener(new Object(), handler, what)) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("cookie", APIHttp.getSettingNote(APIHttp.SET_COOKIE));
                return hashMap;
            }
        };
        requestPost.setTag(requestTag);
        requestQueue.add(requestPost);
    }

    //图片下载
    public static void Image(RequestQueue requestQueue, String url, Handler handler, int what, int imageViewWidth, int imageViewHeiget, Bitmap.Config decodeConfig) {
        ImageRequest request = new ImageRequest(url, new MyImageListener(new Object(), handler, what), imageViewWidth, imageViewHeiget, decodeConfig, new MyErrorListener(new Object(), handler, what));
        requestQueue.add(request);
    }

    //图片下载（内存、文件，网络缓存）
    public static void ImageWithCache(RequestQueue requestQueue, String url, Handler handler, ImageView imageView, int defaultImageResId, int errorImageResId) {
        ImageLoader imageLoader  = new ImageLoader(requestQueue,new DoubleCache());
        ImageLoader.ImageListener listener = imageLoader.getImageListener(imageView,defaultImageResId,errorImageResId);
        imageLoader.get(url,listener);
    }


    //移除请求
    public static void removeRequest(RequestQueue requestQueue,String requestTag){
        requestQueue.cancelAll(requestTag);
    }

    //保存Cookie
    public static void saveSettingNote(String s, String saveData) {
        SharedPreferences.Editor note = MyApplication.getInstance().getSharedPreferences(COOKIE, MODE_PRIVATE).edit();
        note.putString(s, saveData);
        note.commit();
    }
    //获取Cookie
    public static String getSettingNote(String s) {//获取保存设置
        SharedPreferences read = MyApplication.getInstance().getSharedPreferences(COOKIE, MODE_PRIVATE);
        Log.i(TAG, "getCookie:" + read.getString(s, ""));
        return read.getString(s, "");
    }

}
