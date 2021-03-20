package com.zqb.myapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.zqb.myapplication.R;


/**
 * @author Dengzh
 * @date 2020/5/11
 * Description:带下划线的密码编辑框
 */
public class PasswordUnderLineEditText extends AppCompatEditText implements TextWatcher {

    //EditText 的宽高
    private int mWidth;
    private int mHeight;

    //下划线
    private int lineWidth;  //由EditText宽度 - 边距 / 数量所得
    private int lineHeight;
    private int lineMargin;
    private int lineColor;  //未输入颜色
    private int lineColor2; //已输入颜色

    //文字
    private float textSize;
    private int textColor;
    private float baseline;  //基准线

    private Context mContext;
    private TextPaint mTextPaint;
    private Paint mLinePaint;
    private int count = 4;        //个数
    private int mTextLength;  //已输入文字长度

    //其他配置
    private boolean isRoundLine = true;  //是否是圆角横线
    private boolean isPasswordInput;
    private String changedText;


    public PasswordUnderLineEditText(Context context) {
        this(context,null);
    }

    public PasswordUnderLineEditText(Context context, AttributeSet attrs) {
        this(context, attrs,android.R.attr.editTextStyle);
    }

    public PasswordUnderLineEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        //count = maxLength
        for (InputFilter filter : getFilters()) {
            if (filter instanceof InputFilter.LengthFilter) {
                count = ((InputFilter.LengthFilter) filter).getMax();
            }
        }

        //定义一系列属性
        TypedArray a = mContext.obtainStyledAttributes(attrs,R.styleable.PasswordUnderLineEditText);
        // count = a.getInt(R.styleable.PasswordUnderLineEditText_count,4);  //数量
        isPasswordInput = a.getBoolean(R.styleable.PasswordUnderLineEditText_PSWUnderLineEt_passwordInput,true);
        //Line
        lineHeight = a.getDimensionPixelSize(R.styleable.PasswordUnderLineEditText_PSWUnderLineEt_lineHeight,DisplayUtils.dip2px(mContext,6));
        lineMargin = a.getDimensionPixelSize(R.styleable.PasswordUnderLineEditText_PSWUnderLineEt_lineMargin,DisplayUtils.dip2px(mContext,18));
        lineColor = ContextCompat.getColor(mContext,a.getResourceId(R.styleable.PasswordUnderLineEditText_PSWUnderLineEt_lineColor,R.color.colorPrimary));
        lineColor2 = ContextCompat.getColor(mContext,a.getResourceId(R.styleable.PasswordUnderLineEditText_PSWUnderLineEt_lineColor2,R.color.colorPrimaryDark));
        //文字
        textSize = a.getDimensionPixelSize(R.styleable.PasswordUnderLineEditText_PSWUnderLineEt_textSize,DisplayUtils.dip2px(mContext,35));
        textColor =  ContextCompat.getColor(mContext,a.getResourceId(R.styleable.PasswordUnderLineEditText_PSWUnderLineEt_textColor,R.color.colorAccent));
        changedText = a.getString(R.styleable.PasswordUnderLineEditText_PSWUnderLineEt_changedText);
        if (isPasswordInput && TextUtils.isEmpty(changedText)){
            changedText = "*";
        }
        a.recycle();
        init();
    }

    private void init(){
        initEditTextAttrs();

        //文字画笔
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);  //水平居中了，但是上下是以基线为准
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setFakeBoldText(true); //加粗

        //线画笔
        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(lineColor);
        //设置画笔的线冒样式
        //Paint.Cap.ROUND、Paint.Cap.SQUARE 会在线长度的基础上首尾添加一个通过 setStrokeWidth 设置的宽度
        if (isRoundLine){
            mLinePaint.setStrokeCap(Paint.Cap.ROUND);
        }
        mLinePaint.setStrokeWidth(lineHeight);
    }

    /**
     * EditText本身的属性,取消背景、取消光标、文字透明
     * */
    private void initEditTextAttrs(){
        setGravity(Gravity.CENTER_VERTICAL);
        setBackground(null);
        setTextColor(ContextCompat.getColor(mContext,android.R.color.transparent));
        setCursorVisible(false);
        addTextChangedListener(this);
    }


    /**
     * 宽高确定
     * */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        //动态计算单个内容宽度和下划线的宽度
        lineWidth = (mWidth - (count-1)*lineMargin)/count;
        //动态计算baseline,Y轴居中，要计算基准线
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float distance=(fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
        baseline = (mHeight - lineHeight)/2.0f + distance;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
        drawText(canvas);
    }

    /**
     * 画下划线
     * setStrokeCap(Paint.Cap.ROUND)
     * Paint.Cap.ROUND、Paint.Cap.SQUARE 会在线长度的基础上首尾添加一个通过 setStrokeWidth 设置的宽度
     * */
    private void drawLine(Canvas canvas){
        int startX = 0;
        int startY = mHeight - lineHeight/2;
        for (int i = 0;i<count;i++){
            startX = i * (lineWidth + lineMargin);
            int stopX = startX + lineWidth;

            if (isRoundLine){
                startX = startX + lineHeight/2;
                stopX = stopX - lineHeight/2;
            }

            if (i <= mTextLength-1){
                //已输入的文字，下划线变色
                mLinePaint.setColor(lineColor2);
            }else{
                mLinePaint.setColor(lineColor);
            }
            canvas.drawLine(startX,startY,stopX,startY,mLinePaint);
        }
    }

    /**
     * 画文字
     * x居中：setTextAlign(Paint.Align.CENTER)
     * y居中：基准线
     * */
    private void drawText(Canvas canvas){
        int startX = 0;
        for (int i = 0; i < mTextLength; i++) {
            startX = i * (lineWidth + lineMargin);
            int stopX = startX + lineWidth;
            if (isPasswordInput){
                canvas.drawText(changedText, (startX + stopX) / 2, baseline, mTextPaint);
            }else{
                canvas.drawText(editable.charAt(i) + "", (startX + stopX) / 2, baseline, mTextPaint);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }


    private Editable editable;
    @Override
    public void afterTextChanged(Editable editable) {
        mTextLength = editable.length();
        this.editable = editable;
        if (editable.length() == count){
            if (listener!=null){
                listener.onEnd(editable.toString());
            }
        }
    }

    public interface PwdListener{
        void onEnd(String text);
    }

    private PwdListener listener;

    public void setPwdListener(PwdListener listener){
        this.listener = listener;
    }
}

