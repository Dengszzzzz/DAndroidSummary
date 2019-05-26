package com.sz.dzh.dandroidsummary.model.viewDetails.popwindow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.socks.library.KLog;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;
import com.sz.dzh.dandroidsummary.utils.ToastUtils;
import com.sz.dzh.dandroidsummary.widget.popwindow.BasePopupWindow;
import com.sz.dzh.dandroidsummary.widget.popwindow.TagSelectPopWindow;
import com.sz.dzh.dandroidsummary.widget.popwindow.TimeSelectPopWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengzh on 2018/4/18.
 */

public class BasePopTestActivity extends BaseActivity {

    @BindView(R.id.btPop)
    Button btPop;
    @BindView(R.id.btPop1)
    Button btPop1;
    @BindView(R.id.btPop2)
    Button btPop2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_base_pop_test);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("PopWindow 示例");
    }

    @OnClick({R.id.btPop, R.id.btPop1,R.id.btPop2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btPop:
                showBasePopWindow();
                break;
            case R.id.btPop1:
                showTimeSelectPopWindow();
                break;
            case R.id.btPop2:
                showTagPopWindow();
                break;
        }
    }

    /**
     * 测试Base弹窗，链式拼接
     */
    private void showBasePopWindow() {
        BasePopupWindow popupWindow = new BasePopupWindow.PopupWindowBuilder(this)
                .setView(R.layout.pop_test)//显示的布局，还可以通过设置一个View
                //.size(600,400) //设置显示的大小，不设置就默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .create();//创建PopupWindow

        TextView textTv = popupWindow.getView(R.id.textTv);
        textTv.setText("BasePopwindow示例");
        popupWindow.showAsDropDown(btPop, 0, 10);//显示PopupWindow
    }


    private TimeSelectPopWindow timeSelectPopWindow;
    /**
     * 显示筛选弹窗
     */
    private void showTimeSelectPopWindow() {
        if (timeSelectPopWindow == null) {
            timeSelectPopWindow = new TimeSelectPopWindow(this);
            timeSelectPopWindow.setOnChildClickListener(new TimeSelectPopWindow.OnChildClickListener() {
                @Override
                public void onConfirm(String beginData2, String endData2) {
                    try {
                        //确认->时间查询
                        ToastUtils.showToast("开始时间:" + beginData2 + "  结束时间:" + endData2);
                    }catch (Exception e){
                        KLog.e(e.getMessage());
                    }

                }
            });
        }
        timeSelectPopWindow.startHandler();  //每当dialog显示时，重置所有数据
        timeSelectPopWindow.showAsDropDown(btPop1, 0, 0);
    }


    private TagSelectPopWindow tagPopWindow;
    private String[] keys = {"1","2"};
    private String[] values = {"中国","美国"};
    /**
     * 显示标签选择弹窗
     */
    private void showTagPopWindow() {
        if (tagPopWindow == null) {
            tagPopWindow = new TagSelectPopWindow(this);
            tagPopWindow.setOnChildClickListener(new TagSelectPopWindow.OnChildClickListener() {
                @Override
                public void onTagSelect(String key) {
                    ToastUtils.showToast(key);
                }
            });
            tagPopWindow.setData(keys,values);
        }
        tagPopWindow.showAsDropDown(btPop2, 0, 0);
    }


}
