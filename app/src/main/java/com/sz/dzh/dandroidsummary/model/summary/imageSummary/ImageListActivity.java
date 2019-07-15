package com.sz.dzh.dandroidsummary.model.summary.imageSummary;

import com.sz.dzh.dandroidsummary.base.BaseListShowActivity;
import com.sz.dzh.dandroidsummary.model.viewDetails.imageView.NiceImageActivity;

/**
 * Created by Dengzh
 * on 2019/7/1
 * 相关知识：
 * 1.Bitmap
 * 2.BitmapFactory.Options
 * 3.Android的文件目录和缓存目录
 *
 * 问题：
 * 1.图片加载是如何加载的？
 * 2.三级缓存是怎么实现的？
 * 3.图片压缩是怎么实现的？
 * 4.图片保存、通知图库更新是怎么实现？
 * 5.打开相册选择图片、拍照怎么实现？
 * 6.圆形图片、圆角图片怎么实现？
 *
 *
 * 参考：
 * 1.Android性能优化：Bitmap详解&你的Bitmap占多大内存？
 * https://www.jianshu.com/p/4ba3e63c8cdc
 *
 * 2.深入理解Android Bitmap的各种操作
 * https://blog.csdn.net/wanliguodu/article/details/84973846
 *
 * 3.Android 第三方RoundedImageView设置各种圆形、方形头像
 * https://blog.csdn.net/shenggaofei/article/details/83793536
 */

public class ImageListActivity extends BaseListShowActivity {
    @Override
    protected void initUI() {
        tvTitle.setText("Service");
    }

    @Override
    protected void initData() {
        addClazzBean("图片加载及三级缓存", ImageLoadActivity.class);
        addClazzBean("相册拍照、图片压缩保存",BitmapOperaActivity.class);
        addClazzBean("圆角or圆形图片", NiceImageActivity.class);
        mAdapter.notifyDataSetChanged();
    }
}
