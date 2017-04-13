package com.example.utils;

import android.os.Environment;

import com.example.MyApplication;

import java.io.File;

/**
 * Created by liang on 2017/3/23.
 */

public class FileUtils {

    public static String getTempPath() {
        return Environment.getExternalStorageDirectory() +
                File.separator +
                "msgcopy" +
                File.separator +
                MyApplication.getInstance().getPackageName() +
                File.separator +
                "tmp" +
                File.separator;
    }

    public static void clearTmpFile() {
        File file = new File(getTempPath());
        delete(file);
    }

    private static void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }
            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            file.delete();
        }
    }

}

















