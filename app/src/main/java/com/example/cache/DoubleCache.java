package com.example.cache;

import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by liang on 2017/4/13.
 */

public class DoubleCache implements ImageLoader.ImageCache{

    private MemoryCache memoryCache;
    private LocalCache localCache;

    public DoubleCache() {
        memoryCache = new MemoryCache();
        localCache = new LocalCache();
    }

    @Override
    public Bitmap getBitmap(String url) {
        Bitmap bitmap = memoryCache.getBitmap(url);
        if (bitmap == null){
            bitmap = localCache.getBitmap(url);
        }
        return bitmap;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        memoryCache.putBitmap(url,bitmap);
        localCache.putBitmap(url,bitmap);
    }
}
