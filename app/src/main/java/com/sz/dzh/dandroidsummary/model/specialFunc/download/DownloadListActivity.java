package com.sz.dzh.dandroidsummary.model.specialFunc.download;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sz.dzh.dandroidsummary.base.BaseListShowActivity;
import com.sz.dzh.dandroidsummary.model.summary.service.ServiceTestActivity;
import com.sz.dzh.dandroidsummary.model.summary.service.keeplive.KeepServiceActivity;

/**
 * Created by dengzh on 2018/4/18.
 * 文件下载
 * 未完善：权限
 */

public class DownloadListActivity extends BaseListShowActivity {

    private String downloadUrl = "http://admin123.pplussport.com/android/PPlus_V1.0.0.apk";

    @Override
    protected void initUI() {
        tvTitle.setText("Service");
    }

    @Override
    protected void initData() {
        addClazzBean("Retrofit2 + RxJava2 断点续传下载",null);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        onRxDownloadApk();
                        break;
                    case 1:
                        break;
                }
            }
        });
        mAdapter.notifyDataSetChanged();
    }

    private void onRxDownloadApk(){
        Intent intent = new Intent(this, DownloadIntentService.class);
        Bundle bundle = new Bundle();
        bundle.putString("download_url", downloadUrl);
        bundle.putString("download_file", "DAS+_v1.0.1.apk");
        intent.putExtras(bundle);
        startService(intent);
    }
}
