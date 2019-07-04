package com.sz.dzh.dandroidsummary.model.summary.imageSummary;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;

/**
 * Created by Dengzh
 * on 2019/7/1
 * 知识
 * 1.图片加载是如何加载的
 * 2.三级缓存是怎么实现的。
 * 3.图片压缩、图片保存、通知图库更新的代码实现。
 * 4..打开相册选择图片、拍照的代码实现。
 * 5.圆形图片、圆角图片等如何实现。
 *
 */
public class ImageActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_image);

        String mFilePath = "";
        //加载资源
        BitmapFactory.decodeResource(getResources(),R.mipmap.follow_anim0);
        //加载本地图片
        BitmapFactory.decodeFile(mFilePath);

    }
}