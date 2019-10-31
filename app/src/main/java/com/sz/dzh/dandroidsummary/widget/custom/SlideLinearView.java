package com.sz.dzh.dandroidsummary.widget.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.socks.library.KLog;

/**
 * Created by dengzh on 2019/10/23
 * 拖动显示View
 * 效果：在View1 “下拉显示详情” 范围内下拉，View2 逐渐展开。上拉View2 逐渐收起。
 *
 * 需求分析：
 *
 * 1、需要定义一个父View 来装 View1 和 View2，且View2 是在 View1 的底层。
 *    方案：RelativeLayout、FrameLayout、自定义ViewGroup 选一。
 *
 * 2、一开始只显示View1，View2完全不显示。
 *    方案：重写父View onMeasure()，一开始设置宽高为View1的宽高。
 *
 * 3、下拉上拉。
 *    方案：重写onTouchEvent()，对三种状态做处理。
 *
 * 4、View2展开和收起。
 *    方案： 重新设置父View的高度，重写onLayout()重新定位View2
 */
public class SlideLinearView extends LinearLayout {

    private String TAG = getClass().getSimpleName();
    private LayoutParams params;

    /**
     * 第一个View的宽高
     * */
    private int mHeight1;
    private int mWidth1;

    /**
     * 第二个View的宽高
     * */
    private int mHeight2;
    private int mWidth2;

    /**
     * 布局最大宽高
     * */
    private int maxHeight;
    private int maxWidth;

    /**
     * 按下时的点
     * */
    private int downX = 0;
    private int downY = 0;

    /**
     * 滑动距离
     * */
    private int slide = 0;

    /**
     * 方向
     * */
    private int orientation;

    public SlideLinearView(Context context) {
        this(context,null);
    }

    public SlideLinearView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideLinearView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //第一测量，需要设置初始宽高显示和设置相关数据
        if(params==null){
            //对所有的子View进行测量
            measureChildren(widthMeasureSpec,heightMeasureSpec);
            //得到直接子View的数量
            int childCount = getChildCount();
            //子View不是2个的，此控件失效
            if(childCount != 2){
                setMeasuredDimension(0,0);
            }else{
                //第一个子View的宽高
                View child1 = getChildAt(0);
                mWidth1 = child1.getMeasuredWidth();
                mHeight1 = child1.getMeasuredHeight();

                //第二个子View的宽高
                View child2 = getChildAt(1);
                mWidth2 = child2.getMeasuredWidth();
                mHeight2 = child2.getMeasuredHeight();

                //判断方向
                orientation = getOrientation();
                if (orientation == VERTICAL) {
                    //竖直方向，取最大宽度，高度取第一个子View高度
                    maxWidth = Math.max(mWidth1,mWidth2);
                    maxHeight = mHeight1 + mHeight2;
                    setMeasuredDimension(maxWidth,mHeight1);
                }else{
                    //水平方向，取最大高度，宽度取第一个子View的宽度。
                    maxWidth = mWidth1 + mWidth2;
                    maxHeight = Math.max(mHeight1,mHeight2);
                    setMeasuredDimension(mWidth1,maxHeight);
                }
            }
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        KLog.e("onSizeChanged:新宽--" + w + "，新高--" + h);
        //第一次测量完毕后，负值params
        if(params == null){
            params = (LayoutParams) getLayoutParams();
        }
    }


    /**
     * 触摸事件
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                KLog.e("downX:" + downX +",downY：" + downY);
                break;
            case MotionEvent.ACTION_MOVE:
                if(orientation == VERTICAL){ //竖直方向，比较Y值和高度
                    slide = downY - (int)event.getY();
                    if(slide < 0 && params.height < maxHeight) {//下滑，且没有超过最大高度，允许下滑
                        params.height = mHeight1 + Math.abs(slide);
                        requestLayout();
                    }else if(slide > 0 && params.height > mHeight1){  //上滑,且没有超过最小高度，允许上滑
                        params.height = maxHeight - Math.abs(slide);
                        requestLayout();
                    }
                }else{
                    //水平方向，比较X值和宽度
                    slide = (int) (downX - event.getX());
                    if(slide < 0 && params.width < maxWidth){ //右滑，且没有超过最大宽度，允许右滑
                        params.width = mWidth1 + Math.abs(slide);
                        requestLayout();
                    }else if(slide > 0 && params.width > mWidth1){ //左滑，且没有超过最小宽度，允许左滑
                        params.width = maxWidth - Math.abs(slide);
                        requestLayout();
                    }
                }
                KLog.e("slide：" + slide);

               // KLog.e("高度变化：" + params.height);
                break;
            case MotionEvent.ACTION_UP:
                if(orientation == VERTICAL){
                    //滑动决策，滑动超过第二个图的 1/3 就完全显示
                    if(params.height >= mHeight1 + mHeight2/3){
                        params.height = maxHeight;
                    }else{
                        params.height = mHeight1;
                    }
                    KLog.e("最终高度：" + params.height);
                }else{
                    if(params.width >= mWidth1 + mWidth2/3){
                        params.width = maxWidth;
                    }else{
                        params.width = mWidth1;
                    }
                    KLog.e("最终宽度：" + params.height);
                }
                requestLayout();
                break;
        }
        return true;
    }


}
