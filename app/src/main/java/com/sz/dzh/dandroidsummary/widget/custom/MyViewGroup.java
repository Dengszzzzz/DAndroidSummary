package com.sz.dzh.dandroidsummary.widget.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.socks.library.KLog;

/**
 * Created by administrator on 2019/5/31.
 * ViewGroup总结：
 * 1)除了onMeasure(),onDraw(),一般还要重写onLayout()。
 * 2)onMeasure()：
 *    除了上述相关内容，还要注意以下几点：将所有的子View进行测量，
 *    这会触发每个子View的onMeasure函数,要调用measureChildren(),注意和measureChild()区分;
 *    调用getChildCount()获取子View数量；调用getChildAt(i)获取子View。
 * 3)onLayout():
 *    遍历循环子View，调用子View的layout(int l, int t, int r, int b)定位。
 */

public class MyViewGroup extends ViewGroup {

    private String TAG = getClass().getSimpleName();

    public MyViewGroup(Context context) {
        this(context,null);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //将所有的子View进行测量，这会触发每个子View的onMeasure函数
        //注意要与measureChild区分，measureChild是对单个view进行测量
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();
        if(childCount == 0){ //如果没有子View,当前ViewGroup没有存在的意义，不用占用空间
            setMeasuredDimension(0,0);
        }else{
            //如果宽高都是包裹内容
            if(widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
                //宽高都依据子空间决定
                setMeasuredDimension(getMaxWidth(),getTotalHeight());
            }else if(widthMode == MeasureSpec.AT_MOST){  //宽度wrap_content,高度是确切的，因而用heightSize
                setMeasuredDimension(getMaxWidth(),heightSize);
            }else if(heightMode == MeasureSpec.AT_MOST){  //高度wrap_content
                setMeasuredDimension(widthSize,getTotalHeight());
            }
        }

    }

    /**
     * 总高度
     * @return
     */
    private int getTotalHeight(){
        int childCount = getChildCount();
        int height = 0;
        for(int i = 0; i< childCount;i++){
            View child = getChildAt(i);
            height += child.getMeasuredHeight();
        }
        return height;
    }

    /**
     * 最大宽度
     * @return
     */
    private int getMaxWidth(){
        int childCount = getChildCount();
        int width = 0;
        for(int i = 0; i < childCount;i++){
            View child = getChildAt(i);
            if(width < child.getMeasuredWidth()){
                width = child.getMeasuredWidth();
            }
        }
        return width;
    }

    /**
     * 子View定位
     * @param changed
     * @param l  MyViewGroup 的 左坐标
     * @param t  MyViewGroup 的 顶坐标
     * @param r  MyViewGroup 的 右坐标
     * @param b  MyViewGroup 的 底坐标
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        //记录当前的高度位置
        int curHeight = 0;
        for(int k = 0;k<count;k++){
            View child = getChildAt(k);
            int height = child.getMeasuredHeight();
            int width = child.getMeasuredWidth();
            child.layout(0,curHeight,width,curHeight + height);
            curHeight += height;
        }
    }


    /*********************************** 事件分发机制 *******************************************/
    /**
     * 分发，在ViewGroup中，dispatchTouchEvent()方法里调用onInterceptTouchEvent()和onTouchEvent()
     * 判断onInterceptTouchEvent()是否拦截
     * 1.不拦截就调用下层View的dispatchTouchEvent()方法
     * 2.拦截就调用自己的onTouchEvent(),自己消费这个事件
     * return:
     * 1)true:终结事件传递,也不会执行自身的 onInterceptTouchEvent() 和 onTouchEvent()方法。
     *        在此View之上的父容器能收到ACTION_MOVE和ACTION_UP。
     * 2)false:回溯到父View的onTouchEvent方法
     *
     * */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        KLog.d(TAG,"MyViewGroup 的dispatchTouchEvent-----" + ev.getAction());
        return super.dispatchTouchEvent(ev);  //默认false
        //return false;
    }

    /**
     * 是否拦截
     * 1.拦截，return true。
     * 2.不拦截，return super.onInterceptTouchEvent() 或 return false，
     *   事件继续往子View的dispatchTouchEvent传递。
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        KLog.d(TAG,"MyViewGroup 的onInterceptTouchEvent-----" + ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * @param event
     * @return
     * 1)true:消费事件
     * 2)false:不消费事件，回溯到父View的onTouchEvent方法
     * 总结：
     * 在哪个View的onTouchEvent 返回true，
     * 那么ACTION_MOVE和ACTION_UP的事件从上往下传到这个View后就不再往下传递了，
     * 而直接传给自己的onTouchEvent 并结束本次事件传递过程
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KLog.d(TAG,"MyViewGroup 的onTouchEvent-----" + event.getAction());
        return super.onTouchEvent(event);
    }

    /**
     * 对于ACTION_MOVE、ACTION_UP总结：
     * ACTION_DOWN事件在哪个控件消费了（return true）， 那么ACTION_MOVE和ACTION_UP就会从上往下
     * （通过dispatchTouchEvent）做事件分发往下传，就只会传到这个控件，不会继续往下传。
     * 1.如果ACTION_DOWN事件是在dispatchTouchEvent消费，那么事件到此为止停止传递，
     * 2.如果ACTION_DOWN事件是在onTouchEvent消费的，那么会把ACTION_MOVE或ACTION_UP事件传给
     * 该控件的onTouchEvent处理并结束传递。
     * **/
}
