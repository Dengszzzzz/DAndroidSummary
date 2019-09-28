package com.sz.dzh.dandroidsummary.utils.imageUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;
import com.socks.library.KLog;
import com.sz.dengzh.commonlib.CommonConfig;
import com.sz.dzh.dandroidsummary.App;

import java.io.File;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by dengzh on 2019/9/28
 */
public class ImageUtils {

    private final String TAG = getClass().getSimpleName();

    /**
     * 时间戳作为文件名
     * @return
     */
    public static String getFileName(){
        return "DAS_" + System.currentTimeMillis();
    }


    /**
     * 获取缓存目录
     * @return
     */
    public static File getCacheDir() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return CommonConfig.ctx.getExternalCacheDir();
        }
        return CommonConfig.ctx.getCacheDir();
    }


    /**
     * Luban 压缩
     *
     * @param context
     * @param filePath
     * @return
     */
    public static void compress(Context context, String filePath, OnCompressListener listener) {
        String targetPath = getCacheDir() + "/luban_disk_cache";
        File dirFile = new File(targetPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        Luban.with(context)
                .load(filePath)
                .ignoreBy(200)
                .setTargetDir(targetPath)
                .setCompressListener(listener)
                .launch();
    }

    /**
     * 删除裁剪和luban压缩的文件
     */
    public static void deleteCacheDirImgFile() {
        File compressDir = new File(getCacheDir() + File.separator + "picture_cache");
        File lubanDir = new File(getCacheDir() + File.separator + "luban_disk_cache");

        if (compressDir.exists()) {
            File[] files = compressDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }

        if (lubanDir.exists()) {
            File[] files = lubanDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }
    }
}
