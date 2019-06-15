package com.sz.dzh.dandroidsummary.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by administrator on 2018/8/15.
 * 1. apply没有返回值而commit返回boolean表明修改是否提交成功
 * 2. apply是将修改数据原子提交到内存, 而后异步真正提交到硬件磁盘, 而commit是同步的提交到硬件磁盘，因此，
 * 在多个并发的提交commit的时候，他们会等待正在处理的commit保存到磁盘后在操作，从而降低了效率。
 * 而apply只是原子的提交到内容，后面有调用apply的函数的将会直接覆盖前面的内存数据，这样从一定程度上提高了很多效率。
 * 3. apply方法不会提示任何失败的提示。
 * 由于在一个进程中，sharedPreference是单实例，一般不会出现并发冲突，如果对提交的结果不关心的话，建议使用apply，当然需要确保提交成功且有后续操作的话，还是需要用commit的。
 *
 * 备注：
 * 可以通过DDMS的【File Explorer】找到data\data\程序包名\shared_prefs目录
 */
public class SPUtils {

    private String path = "SP_data";

    private static SPUtils instance;
    private SharedPreferences sp;

    private SPUtils(Context context) {
        sp = context.getSharedPreferences(path, Context.MODE_PRIVATE);
    }

    public static SPUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (SPUtils.class) {
                if (instance == null) {
                    instance = new SPUtils(context);
                }
            }
        }
        return instance;
    }


    public void setPath(String path) {
        this.path = path;
    }

    public void putBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public void getBoolean(String key, boolean defaultValue) {
        sp.getBoolean(key, defaultValue);
    }

    public void putString(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public void putInt(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public void remove(String key) {
        sp.edit().remove(key).apply();
    }

    public void clear() {
        if (sp != null)
            sp.edit().clear().apply();
    }
}
