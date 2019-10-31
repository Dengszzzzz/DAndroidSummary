package com.sz.dzh.dandroidsummary.widget.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.socks.library.KLog;
import com.sz.dzh.dandroidsummary.R;

/**
 * Created by dengzh on 2019/5/31.
 * View总结：
 * 1)对于自定义view，前两个构造函数就足够了。一般要重写onMeasure(),onDraw()。
 * 2)onMeasure()：
 *      MeasureSpec.size()获取Size，MeasureSpec.mode()获取模式，
 *      模式包括unSpecified,AT_MOST,EXACTLY,
 *      其中UNSPECIFIED一般不处理；AT_MOST就是Wrap_content；EXACTLY就是确切的值或者MATCH_PARENT；
 *      最后记得调用setMeasuredDimension(width,size);设置宽高
 * 3)onSizeChanged()：会得到最终的宽高，当view的size有变化时会调用。
 * 4)onDraw():注意不要在此方法创建新对象，例如Paint不要放在里面new出来，onDraw()需要知道Paint,Canvas,Path知识。
 *            Invalidate()和postInvalidate()，都会调用onDraw()重绘。
 *            如果在这个方法里用了path，记得用path.reset()重置一下。
 * 5)TypeArray获取attrs.xml定义的属性。
 */

public class MyView extends View {

    private String TAG = getClass().getSimpleName();

    private int defaultSize;
    private Paint paint;

    //1.一般在代码中创建View的时候用View(Context)。
    public MyView(Context context) {
        this(context, null);
    }

    //2.一般在layout文件中使用的时候会调用，关于它的所有属性(包括自定义属性)都会包含在attrs中传递进来。
    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //第二个参数就是我们在styles.xml文件中的<declare-styleable>标签
        //即属性集合的标签，在R文件中名称为R.styleable + name
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView);

        //第一个参数为属性集合里面的属性，R文件名称：R.styleable+属性集合名称+下划线+属性名称
        //第二个参数为，如果没有设置这个属性，则设置的默认的值
        defaultSize = typedArray.getDimensionPixelSize(R.styleable.MyView_defaultSize, 100);

        //最后记得将TypedArray对象回收
        typedArray.recycle();

        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        //  paint.setStrokeWidth(5);
        //  paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
    }

    /**
     * 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getSize(defaultSize, widthMeasureSpec);
        int height = getSize(defaultSize, heightMeasureSpec);
        //保持宽高一致
        if (width > height) {
            width = height;
        } else {
            height = width;
        }
        //记得调用此方法
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private int getSize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;
        int size = MeasureSpec.getSize(measureSpec);
        int mode = MeasureSpec.getMode(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: //如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            case MeasureSpec.AT_MOST:    //如果测量模式是最大取值为size
                mySize = defaultSize;    //可以自己决定大小，此处暂时取默认值
                break;
                case MeasureSpec.EXACTLY:  //如果是固定的大小（match_parent/确切值），那就不要去改变它
                mySize = size;
                break;
        }
        return mySize;
    }

    /**
     * 绘画
     * 举例：中心点画圆
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //坐标是相对View边界的，不是相对父容器的
        int r = getMeasuredWidth() / 2;
        int centerX = r;
        int centerY = r;

        canvas.drawCircle(centerX, centerY, r, paint);
    }


    /*********************************** 事件分发机制 ****************************************/

    /**
     * 一般没必要重写 view的dispatchTouchEvent()
     * 此方法伪代码如下，可以看出onTouch() 和 onTouchEvent()的优先级和区别
     * if(onFilterTouchEventForSecurity(event)){
        ListenerInfo li = mListenerInfo;
        if(li != null && li.mOnTouchListener != null && (mViewFlags & ENABLED_MASK) == ENABLED
               && li.mOnTouchListener.onTouch(this, event)) {
           return true;
          }
          if(onTouchEvent(event)){
          return true;
         }
     }
       三个条件说明：
     * 1.mOnTouchListener不能为空
     * 2.当前View必须是enable状态
     * 3.onTouch()返回true
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        KLog.d(TAG, "MyView 的dispatchTouchEvent-----" + event.getAction());
        return super.dispatchTouchEvent(event);
    }


    /**
     * 注意点：
     * 1.onTouchListener的onTouch方法优先级比onTouchEvent高，会先触发。
     * 2.假如onTouch方法返回false会接着触发onTouchEvent，反之onTouchEvent方法不会被调用。
     * 3.内置诸如click事件的实现等等都基于onTouchEvent，假如onTouch返回true，这些事件将不会被触发。
     *   当然，如下重写了onTouchEvent返回true，也不会触犯click事件了，
     *   除非在ACTION_UP里主动调performClick()，具体原因可看onTouchEvent的源代码。
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KLog.d(TAG, "MyView 的onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                KLog.d(TAG, "MyView 的 ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                KLog.d(TAG, "MyView 的 ACTION_MOVE" + "  x:" +  event.getX()
                        + "  y:" + event.getY());
                break;
            case MotionEvent.ACTION_UP:
                KLog.d(TAG, "MyView 的 ACTION_UP");
                break;
        }
        return true;  //如果不设置为true，则整个分发流程没有消费触摸事件，则ACTION_MOVE,ACTION_UP不会执行
        //return super.onTouchEvent(event);
    }


}
