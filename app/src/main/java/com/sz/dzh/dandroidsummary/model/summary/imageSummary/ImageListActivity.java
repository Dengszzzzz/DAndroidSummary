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
 * 项目中：
 * 图片加载，网络加载要做三级缓存。
 * 选择相册和拍照后，对图片做压缩，再上传给后台。长按保存图片，通知图库更新。
 * 圆形头像，圆角背景等。
 */

public class ImageListActivity extends BaseListShowActivity {
    @Override
    protected void initUI() {
        tvTitle.setText("Service");
    }

    @Override
    protected void initData() {
        addClazzBean("图片加载及三级缓存", ImageLoadActivity.class);
        addClazzBean("相册拍照、图片压缩保存",KeepServiceActivity.class);
        mAdapter.notifyDataSetChanged();
    }
}
