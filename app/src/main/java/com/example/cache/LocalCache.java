package com.example.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader;
import com.example.utils.FileUtils;
import com.example.utils.MD5Encoder;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by liang on 2017/4/13.
 */

public class LocalCache implements ImageLoader.ImageCache{

    private static final String CACHE_PATH = FileUtils.getTempPath();

    @Override
    public Bitmap getBitmap(String url) {
        Log.i("getBitmap","LocalCache");
        return BitmapFactory.decodeFile(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        try {
            String fileName = MD5Encoder.encode(url);//把图片的url当做文件名,并进行MD5加密
            File file=new File(CACHE_PATH,fileName);

            //通过得到文件的父文件,判断父文件是否存在
            File parentFile = file.getParentFile();
            if (!parentFile.exists()){
                parentFile.mkdirs();
            }
            //把图片保存至本地
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
