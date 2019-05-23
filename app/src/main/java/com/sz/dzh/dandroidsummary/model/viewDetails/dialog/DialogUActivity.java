package com.sz.dzh.dandroidsummary.model.viewDetails.dialog;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sz.dzh.dandroidsummary.base.BaseListShowActivity;
import com.sz.dzh.dandroidsummary.utils.ToastUtils;
import com.sz.dzh.dandroidsummary.widget.dialog.DialogUtils;
import com.sz.dzh.dandroidsummary.widget.dialog.IDialogSelect;

/**
 * Created by dengzh on 2018/4/23.
 * 演示 BaseDialog
 */

public class DialogUActivity extends BaseListShowActivity {


    @Override
    protected void initUI() {
        tvTitle.setText("BaseDialog 示例");
    }

    @Override
    protected void initData() {
        addClazzBean("两按钮无标题Dialog",null);
        addClazzBean("两按钮带标题Dialog",null);
        addClazzBean("底部弹出动画Dialog",null);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        showTwoButtonNotTitleDialog();
                        break;
                    case 1:
                        showTwoButtonWithTitleDialog();
                        break;
                    case 2:
                        showBottomDialog();
                        break;
                }
            }
        });
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 显示底部弹出弹窗
     */
    private void showBottomDialog(){
        DialogUtils.showBottomDialog(this);
    }

    /**
     * 显示两按钮无标题Dialog
     */
    private void showTwoButtonNotTitleDialog(){
        DialogUtils.showTwoButtonNotTitleDialog(this, "您还没有投资项目", "去创业", false,false, new IDialogSelect() {
            @Override
            public void onSelected(int i, Object o) {
                switch (i){
                    case 0: //取消
                        break;
                    case 1:  //确定
                        ToastUtils.showToast("点击了确定按钮");
                        break;
                }
            }
        });
    }

    /**
     * 显示两按钮带标题Dialog
     */
    private void showTwoButtonWithTitleDialog(){
        DialogUtils.showTwoButtonTitleDialog(this, "支付金额"
                , "年收益值 1000元"
                , "确定支付", new IDialogSelect() {
                    @Override
                    public void onSelected(int i, Object o) {
                        if (i == 1) {
                            ToastUtils.showToast("点击了确定按钮");
                        }
                    }
                });
    }

}
