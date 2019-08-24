package com.sz.dzh.dandroidsummary.widget.popwindow;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dengzh.commonlib.utils.ToastUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by dengzh on 2017/11/10.
 * 时间选择器  handler 刷新数据
 *   界面显示->开启计时器，界面消失->取消计时器
 *   继承BasePopupWindow 再用链式略显麻烦
 *   使用了 Android-PickerView
 */

public class TimeSelectPopWindow extends BasePopupWindow implements View.OnClickListener{

    private final int MSG_UPDATE_DATE = 1001;   //0.2秒刷新一次数据
    private long delayMillis = 200L;

    private LinearLayout timePanelLl;
    private TextView startTimeTv,endTimeTv;

    private TimePickerView pvTime;
    private String beginDateStr = "";//开始时间
    private String endDateStr = "";//结束时间
    private int timeType = -1;  // -1代表不选择时间，0-开始时间，1-结束时间
    private OnChildClickListener listener;
    private SimpleDateFormat dateFormat;  //时间格式

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_UPDATE_DATE:
                    if(pvTime!=null){
                        pvTime.returnData();  //每0.2秒执行一次数据刷新，相当于手动调用onTimeSelect()
                    }
                    handler.sendEmptyMessageDelayed(MSG_UPDATE_DATE,delayMillis);
                    break;
            }
        }
    };


    public TimeSelectPopWindow(Context mContext) {
        super(mContext, R.layout.pop_time_select,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        init();
    }

    private void init(){

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                handler.removeMessages(MSG_UPDATE_DATE);  //界面消失，取消计时器
            }
        });

        getView(R.id.translucentView).setOnClickListener(this);
        getView(R.id.resetTv).setOnClickListener(this);
        getView(R.id.confirmTv).setOnClickListener(this);

        timePanelLl = getView(R.id.timePanelLl);
        startTimeTv = getView(R.id.startTimeTv);
        endTimeTv = getView(R.id.endTimeTv);
        startTimeTv.setOnClickListener(this);
        endTimeTv.setOnClickListener(this);

        dateFormat = new SimpleDateFormat("yyyyMMdd");
        initTimePicker();
        resetTime();
    }

    /**
     * 初始化时间选择器
     */
    private void initTimePicker() {
        //当前时间
        Calendar selectedDate = Calendar.getInstance();
        //1.设定时间选择范围
        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2066, 11, 28);
        //2.构建时间选择器
        pvTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String sTime = dateFormat.format(date);
                if(timeType == 0){  //开始时间
                    beginDateStr = sTime;
                    startTimeTv.setText(beginDateStr);
                }else if(timeType == 1){   //结束时间
                    endDateStr = sTime;
                    endTimeTv.setText(endDateStr);
                }
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(false)  //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)    // 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)  //起始终止年月日设定
                .setDecorView(timePanelLl)    //绑定到父容器中
                .isDialog(false)
                .setTitleBgColor(ContextCompat.getColor(mContext,R.color.white))
                .build();
        pvTime.findViewById(com.bigkoo.pickerview.R.id.rv_topbar).setVisibility(View.GONE);
        //3.显示
        pvTime.show();
    }


    /**
     * 再次点开弹窗
     */
    public void startHandler(){
       // timeType = 0;   //每次把时间选在开始时间
        if(!handler.hasMessages(MSG_UPDATE_DATE)){
            handler.sendEmptyMessageDelayed(MSG_UPDATE_DATE,delayMillis);
        }
    }



    /**
     * 重置时间
     */
    private void resetTime(){
        timeType = -1;
        Calendar selectedDate = Calendar.getInstance();
        Date date=selectedDate.getTime();
        String nowTime = dateFormat.format(date);
        beginDateStr = "";
        endDateStr = "";
        startTimeTv.setText("--");
        endTimeTv.setText("--");
        pvTime.setDate(selectedDate);
        startTimeTv.setTextColor(ContextCompat.getColor(mContext,R.color.c_333333));
        endTimeTv.setTextColor(ContextCompat.getColor(mContext,R.color.c_999999));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.translucentView:
                dismiss();
                break;
            case R.id.startTimeTv: //开始时间
                timeType = 0;
                startTimeTv.setTextColor(ContextCompat.getColor(mContext,R.color.c_333333));
                endTimeTv.setTextColor(ContextCompat.getColor(mContext,R.color.c_999999));
                //如果已经存在开始时间，则改变时间选择器当前时间
                if(!TextUtils.isEmpty(beginDateStr)){
                    try {
                        Date date = dateFormat.parse(beginDateStr);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        pvTime.setDate(calendar);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.endTimeTv:  //结束时间
                timeType = 1;
                startTimeTv.setTextColor(ContextCompat.getColor(mContext,R.color.c_999999));
                endTimeTv.setTextColor(ContextCompat.getColor(mContext,R.color.c_333333));
                if(!TextUtils.isEmpty(endDateStr)){
                    try {
                        Date date = dateFormat.parse(endDateStr);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        pvTime.setDate(calendar);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.resetTv:
                resetTime();
                break;
            case R.id.confirmTv:
                if(!TextUtils.isEmpty(beginDateStr) && !TextUtils.isEmpty(endDateStr)){
                    if(Integer.parseInt(endDateStr) < Integer.parseInt(beginDateStr)){
                        //时间格式年分月，直接如此比较也行
                        ToastUtils.showToast("起始日期不能超过结束日期");
                        return;
                    }
                }
                if (listener != null) {
                    listener.onConfirm(beginDateStr, endDateStr);
                }
                dismiss();
                break;
        }
    }

    public interface OnChildClickListener{
        void onConfirm(String beginData, String endData);
    }

    public void setOnChildClickListener(OnChildClickListener listener){
        this.listener = listener;
    }

}
