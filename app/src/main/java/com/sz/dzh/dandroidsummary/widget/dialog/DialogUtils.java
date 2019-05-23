package com.sz.dzh.dandroidsummary.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sz.dzh.dandroidsummary.R;

/**
 * Created by dengzh on 2018/4/23.
 */

public class DialogUtils {


    /**
     * 显示两按钮无标题对话框
     * @param context
     * @param content
     * @param confirmText
     * @param dialogSelect
     */
    public static void showTwoButtonNotTitleDialog(Activity context, String content, String confirmText, boolean cancelOnTouchOutside, boolean cancelable, final IDialogSelect dialogSelect){
        //1.创建dialog
        final BaseDialog dialog = new BaseDialog(context, R.layout.dialog_two_button_not_title, Gravity.CENTER, cancelOnTouchOutside);
        dialog.setMatchWidthDialog();
        dialog.setCancelable(cancelable);
        //2.配置信息
        dialog.setText(R.id.contentTv, content)
                .setText(R.id.confirmTv,confirmText);
        dialog.setOnViewClick(R.id.closeIv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSelect != null) {
                    dialogSelect.onSelected(0,null);
                }
                dialog.toggleDialog();
            }
        }).setOnViewClick(R.id.cancelTv, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogSelect != null) {
                    dialogSelect.onSelected(0,null);
                }
                dialog.toggleDialog();
            }
        }).setOnViewClick(R.id.confirmTv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSelect != null) {
                    dialogSelect.onSelected(1,null);
                }
                dialog.toggleDialog();
            }
        });
        //3.显示dialog
        dialog.toggleDialog();
    }


    /**
     * 显示两按钮有标题对话框
     * @param context
     * @param title      标题
     * @param content    内容
     * @param confirmText   确认文字
     * @param dialogSelect
     */
    public static void showTwoButtonTitleDialog(Activity context, String title, String content, String confirmText, final IDialogSelect dialogSelect){
        //1.创建dialog
        final BaseDialog dialog = new BaseDialog(context, R.layout.dialog_two_button_title, Gravity.CENTER, true);
        dialog.setMatchWidthDialog();
        //2.配置信息
        dialog.setText(R.id.titleTv, title)
                .setText(R.id.contentTv,content)
                .setText(R.id.confirmTv,confirmText);
        dialog.setOnViewClick(R.id.closeIv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSelect != null) {
                    dialogSelect.onSelected(0,null);
                }
                dialog.toggleDialog();
            }
        });
        dialog.setOnViewClick(R.id.cancelTv, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogSelect != null) {
                    dialogSelect.onSelected(0,null);
                }
                dialog.toggleDialog();
            }
        });
        dialog.setOnViewClick(R.id.confirmTv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSelect != null) {
                    dialogSelect.onSelected(1,null);
                }
                dialog.toggleDialog();
            }
        });
        //3.显示dialog
        dialog.toggleDialog();
    }

    public static void showBottomDialog(Context context){
        final BaseDialog dialog = new BaseDialog(context, R.layout.dialog_select_photo2, Gravity.BOTTOM, true,true);
        dialog.toggleDialog();
    }



    public static void showCustomAlertDialog(Context context){
        //自定义View的AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //通过LayoutInflater来加载一个xml布局文件作为一个View对象
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_two_button_title,null);
        //设置view为弹窗的Content
        builder.setView(view);
        //控件id从View获取
        TextView titleTv = view.findViewById(R.id.titleTv);

        builder.show();
    }

}
