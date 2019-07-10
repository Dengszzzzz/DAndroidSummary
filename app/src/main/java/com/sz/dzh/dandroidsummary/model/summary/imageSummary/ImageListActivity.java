package com.sz.dzh.dandroidsummary.model.summary.imageSummary;

import com.sz.dzh.dandroidsummary.base.BaseListShowActivity;
import com.sz.dzh.dandroidsummary.model.summary.service.keeplive.KeepServiceActivity;

/**
 * Created by Dengzh
 * on 2019/7/1
 * 知识
 * 1.图片加载是如何加载的
 * 2.三级缓存是怎么实现的。
 * 3.打开相册选择图片、拍照的代码实现。
 * 4.图片压缩、图片保存、通知图库更新的代码实现。
 * 5.圆形图片、圆角图片等如何实现。
 *
 *
 * 参考：
 * 1.Android性能优化：Bitmap详解&你的Bitmap占多大内存？
 * https://www.jianshu.com/p/4ba3e63c8cdc
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
        mAdapter.notifyDataSetChanged();
    }
}
