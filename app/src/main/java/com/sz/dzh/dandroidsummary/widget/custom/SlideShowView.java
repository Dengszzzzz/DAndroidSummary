package com.sz.dzh.dandroidsummary.widget.custom;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.socks.library.KLog;
import com.sz.dzh.dandroidsummary.R;

/**
 * Created by dengzh on 2019/10/23
 * 拖动显示View
 * 效果：在上层View “下拉显示详情” 范围内下拉，底层View 逐渐展开。上拉底层View 逐渐收起。
 * 为了方便解释，可拖动的View 叫 sView， 上层View 叫 topView;
 *
 * 需求分析：
 *
 * 1、需要定义一个父View 来装 sView 和 topView，且 sView 是在 topView 的底层。
 *    方案：RelativeLayout、FrameLayout、自定义ViewGroup 选一。
 *
 * 2、一开始只显示topView，sView完全不显示。
 *    方案：重写父View onMeasure()，一开始设置高度为 topView 的宽高。
 *
 * 3、下拉上拉。
 *    方案：重写onTouchEvent()，对三种状态做处理。
 *
 * 4、sView 展开和收起。
 *    方案：重新测量父View 的高度，重写onLayout()重新定位 sView。
 *
 */
public class SlideShowView extends ViewGroup {


    private String TAG = getClass().getSimpleName();

    /**
     * 可拖动View的宽高
     * */
    private int msHeight;
    private int msWidth;

    /**
     * 上层View的宽高
     * */
    private int mTopHeight;
    private int mTopWidth;

    /**
     * 布局最大宽高
     * */
    private int maxHeight;
    private int maxWidth;



    /**
     * 按下时的点
     * */
    private int downY = 0;

    /**
     * 当前高度
     * */
    private int curHeight;

    /**
     * 按下时，父View的高度
     * */
    private int downHeight;

    /**
     * 抬起时，父View的目标高度
     * */
    private int targetHeight;

    /**
     * 滑动距离
     * */
    private int slide = 0;

    /**
     * 属性：滑动有效距离
     * */
    private int mSlideEffectSize;

    /**
     * 属性：是否能滑动
     * */
    private boolean mEnableSlideShow;




    public SlideShowView(Context context) {
        this(context,null);
    }

    public SlideShowView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideShowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlideShowView, 0, 0);
        mSlideEffectSize = a.getDimensionPixelSize(R.styleable.SlideShowView_slide_effect_size,50);
        mEnableSlideShow = a.getBoolean(R.styleable.SlideShowView_enable_slide_show,true);
        a.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //第一测量，需要得到子View宽高
        if(curHeight == 0){
            //对所有的子View进行测量
            measureChildren(widthMeasureSpec,heightMeasureSpec);
            //得到直接子View的数量
            int childCount = getChildCount();
            //子View不是2个的，此控件失效
            if(childCount != 2){
                setMeasuredDimension(0,0);
            }else{
                //第一个View的宽高
                View child1 = getChildAt(0);
                msWidth = child1.getMeasuredWidth();
                msHeight = child1.getMeasuredHeight();

                //第二个子View的宽高
                View child2 = getChildAt(1);
                mTopWidth = child2.getMeasuredWidth();
                mTopHeight = child2.getMeasuredHeight();

                //整个viewGroup最大宽高
                maxWidth = Math.max(msWidth,mTopWidth);
                maxHeight = msHeight + mTopHeight;
                //初始设置高度为 上层View  的高度
                setMeasuredDimension(maxWidth,mTopHeight);
            }
        }else{
            //经由上下滑动改变高度测量
            setMeasuredDimension(maxWidth,curHeight);
        }
    }


    /**
     * 测量后确定的值
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        KLog.e("onSizeChanged:新宽--" + w + "，新高--" + h);
        curHeight = h;
    }


    /**
     * 定位,其实是定子View 相对于父View 的位置信息。
     * 此处两个子View。
     * topView：顶部和 父View 保持一致，不收滑动影响。
     * sView: 底部和 父View 保持一致，收滑动音响。
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //第一个子View是可拖动的
        View child1 = getChildAt(0);
        //layout()里的参数，是指子View 在 父View 里的坐标,因为要和顶部保持一致，所以l和t都是0。
        child1.layout(0,curHeight - msHeight,msWidth,curHeight);


        //第二个子View是不变的
        View child2 = getChildAt(1);
        child2.layout(0,0,mTopWidth,mTopHeight);

    }




    /**
     * 触摸事件
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!mEnableSlideShow){
            return false;
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downY = (int) event.getY();
                KLog.e("downY：" + downY);
                //记录按下时，整个父view的高
                downHeight = curHeight;
                break;
            case MotionEvent.ACTION_MOVE:
                /**
                 * slide < 0，往下滑动。 slide>0，往上滑动
                 * */
                slide = downY - (int)event.getY();
                if(slide < 0 && curHeight < maxHeight) {
                    //下滑操作，且当前高度没达到最大高度
                    curHeight = downHeight + Math.abs(slide);
                    requestLayout();
                }else if(slide > 0 && curHeight > mTopHeight){
                    //上滑操作，当前高度没有达到最小高度
                    curHeight = downHeight - Math.abs(slide);
                    requestLayout();
                }
                KLog.e("slide：" + slide);
                break;
            case MotionEvent.ACTION_UP:
                //滑动决策，滑动距离达到某个值，就进行展开 or 收起
                if(Math.abs(slide) > mSlideEffectSize){
                    if(slide<0){
                        targetHeight = maxHeight;
                    }else{
                        targetHeight = mTopHeight;
                    }
                }else{
                    //恢复原样
                    targetHeight = downHeight;
                }
                showAnim();
                KLog.e("最终高度：" + targetHeight);
                //requestLayout();
                break;
        }
        return true;
    }



    private void showAnim(){
        ValueAnimator animator = ValueAnimator.ofInt(curHeight,targetHeight);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                curHeight = (int) animation.getAnimatedValue();
                requestLayout();
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }


}
