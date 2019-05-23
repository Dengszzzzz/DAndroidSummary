package com.sz.dzh.dandroidsummary.model.viewDetails.dialog.dialogFragment;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.sz.dzh.dandroidsummary.widget.dialog.IDialogSelect;


/**
 * Created by dengzh on 2018/4/19.
 * 封装 DialogFragment 基类
 */

public abstract class BaseDialogFragment extends DialogFragment {

    protected IDialogSelect mDialogSelect;
    protected View mContainerView;

    public void setDialogSelect(IDialogSelect mDialogSelect){
        this.mDialogSelect = mDialogSelect;
    }

    /**
     * 一般dialog设置 宽填满，高固定
     * 在onStart()中调用
     * 如果是宽高写死的布局，不需调用此方法
     */
    protected void setMatchParent(){
        //默认 dialogFragment的布局必须设置固定宽高
        Window win = getDialog().getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );

        WindowManager.LayoutParams params = win.getAttributes();
        params.gravity = Gravity.CENTER;
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width =  ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        win.setAttributes(params);
    }


}
