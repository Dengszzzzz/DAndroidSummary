package com.sz.dzh.dandroidsummary.model.summary.imageSummary;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.LruCache;
import android.widget.ImageView;

import com.socks.library.KLog;
import com.sz.dzh.dandroidsummary.base.App;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by administrator on 2018/8/23.
 * 自定义 图片3级缓存 简单演示
 * 原理：内存 -> 文件（本地）->网络
 *
 * 流程：
 * 1.内存，创建LruCache<String,SoftReference<Bitmap>> 作为内存缓存容器，每次从文件或网络加载图片时，要加入缓存中，LruCache内部已做了算法优化。
 * 2.文件，得到缓存目录 getExternalCacheDir() 或 getCacheDir()，在缓存目录下找到文件，用BitmapFactory.decodeFile(xx)得到bitmap。
 *   并将bitmap放入lrucache中。
 *   getExternalCacheDir()： SDCard/Android/data/<application package>/cache/目录
 *   getCacheDir():          /data/data/<application package>/cache目录
 * 3.网络，请求网络流数据，放入内存且保存file。
 */
public class MyImageLoad {

    private static String TAG = "MyImageLoad";
    private static MyImageLoad instance;
    private static WeakReference<Activity> weakReference;  //用弱引用持有Context
    /**
     * LruCache其实是一个Hash表，内部使用的是LinkedHashMap存储数据。
     * 使用LruCache类可以规定缓存内存的大小，并且这个类内部使用到了最近最少使用算法来管理缓存内存。
     * 这里定义 8M的大小作为缓存
     */
    private static LruCache<String, SoftReference<Bitmap>> mImageCache = new LruCache<>(1024 * 1024 * 8);

    private MyImageLoad() {}

    private static MyImageLoad getInstance() {
        if(instance == null){
            synchronized (MyImageLoad.class){
                if(instance == null){
                    instance = new MyImageLoad();
                }
            }
        }
        return instance;
    }

    public static MyImageLoad with(Activity context){
        weakReference = new WeakReference<>(context);
        return getInstance();
    }

    public static void load(ImageView iv, String url){
        //1.从内存读取
        SoftReference<Bitmap> reference = mImageCache.get(url);
        Bitmap cacheBitmp;
        if(reference != null){
            cacheBitmp = reference.get();
            iv.setImageBitmap(cacheBitmp);
            KLog.d(TAG,"内存中图片显示");
            return;
        }
        //2.从文件读取
        cacheBitmp = getBitmapFromFile(url);
        if(cacheBitmp!=null){
            //bitmap保存到内存
            mImageCache.put(url,new SoftReference<Bitmap>(cacheBitmp));
            iv.setImageBitmap(cacheBitmp);
            KLog.d(TAG,"文件中图片显示");
            return;
        }
        //3.连网处理
        getBitmapFromUrl(iv,url);
    }

    /**
     * 从文件获取bitmap
     * @param url
     * @return
     */
    private static Bitmap getBitmapFromFile(String url){
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        //文件名一般要用Md5加密的。
        File file = new File(getCacheDir(),fileName);
        if(file.exists() && file.length()>0){
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }else{
            return null;
        }
    }

    /**
     * FilesDir一般放一些长时间保存的数据，CacheDir放临时缓存数据，有External的是指外部SD卡。
     * 这4个路径下的数据都会随着app被用户卸载而删除，
     * FilesDir 和 CacheDir 分别对应的是 设置->应用->应用详情里面的“清除数据”和”清除缓存“选项。
     *
     * getExternalFilesDir()：  SDCard/Android/data/<application package>/files/目录
     * getFilesDir()：               data/data/<application package>/files/目录
     * getExternalCacheDir()： SDCard/Android/data/<application package>/cache/目录
     * getCacheDir()：            data/data/<application package>/cache/目录
     * @return
     */
    private static File getCacheDir(){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            return App.ctx.getExternalCacheDir();
        }
        return App.ctx.getCacheDir();
    }

    /**
     * 从网络获取bitmap
     * @param iv
     * @param url
     */
    private static void getBitmapFromUrl(final ImageView iv, final String url){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {

            }
            public void onResponse(Call call, Response response) throws IOException {
                KLog.d(TAG,"文件中图片显示");
                InputStream inputStream = response.body().byteStream();//得到图片的流
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                saveBitmap(url,bitmap);
                if(weakReference.get()!=null){
                    weakReference.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iv.setImageBitmap(bitmap);
                        }
                    });
                }
            }
        });
    }

    /**
     * 保存图片
     * @param url
     * @param bitmap
     */
    private static void saveBitmap(String url, Bitmap bitmap){
        //1.放入内存
        mImageCache.put(url,new SoftReference<Bitmap>(bitmap));
        //2.放入cache目录
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        File file = new File(getCacheDir(),fileName);
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,90,os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(os!=null){
                try {
                    os.close();
                    os = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
