package com.example.cache;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by liang on 2017/4/13.
 */

public class MemoryCache implements ImageLoader.ImageCache{

    private LruCache<String,Bitmap> lruCache;

    public MemoryCache() {
        initCache();
    }

    private void initCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
        int cachesize = maxMemory;
        lruCache = new LruCache<String, Bitmap>(cachesize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };

    }

    @Override
    public Bitmap getBitmap(String url) {
        Log.i("getBitmap","MemoryCache");
        return lruCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        lruCache.put(url,bitmap);
    }
}
