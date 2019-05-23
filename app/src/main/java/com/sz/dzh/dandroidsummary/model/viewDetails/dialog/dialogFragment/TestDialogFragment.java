package com.sz.dzh.dandroidsummary.model.viewDetails.dialog.dialogFragment;


import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sz.dzh.dandroidsummary.R;

/**
 * Created by dengzh on 2018/4/18.
 * 使用DialogFragment 替换 Dialog
 * 好处：解决屏幕旋转 activity重建，dialog问题
 * 与activity通信，接口回调
 */

public class TestDialogFragment extends DialogFragment{

    /**
     * onCreate()的时候可以设置Dialog相关的风格和属性
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);   //设置可取消
    }

    /**
     * onCreateView（）的时候设置View相关
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_dialog_test,null);
    }

}
