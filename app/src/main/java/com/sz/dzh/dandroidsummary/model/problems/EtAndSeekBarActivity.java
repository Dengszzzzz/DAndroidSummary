package com.sz.dzh.dandroidsummary.model.problems;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import com.socks.library.KLog;
import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dzh.dandroidsummary.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dengzh
 * on 2019/7/3 0003
 * <p>
 * EditText 和 EditText的联动
 * EditText 和 SeekBar的联动
 * 关键点：焦点判断
 * 知识点：
 * EditText的 addTextChangedListener(),setOnFocusChangeListener()
 * SeekBar的 setOnSeekBarChangeListener()
 *
 *
 * https://www.cnblogs.com/Free-Thinker/p/6839276.html
 * https://www.jianshu.com/p/3cd0921a735b
 * https://yq.aliyun.com/articles/662625
 *
 */
public class EtAndSeekBarActivity extends BaseActivity {

    @BindView(R.id.et_valueA)
    EditText mEtValueA;
    @BindView(R.id.et_valueB)
    EditText mEtValueB;
    @BindView(R.id.et_float_ratio)
    EditText mEtFloatRatio;
    @BindView(R.id.seekBar)
    SeekBar mSeekBar;
    @BindView(R.id.et_float_ratio2)
    EditText mEtFloatRatio2;
    @BindView(R.id.seekBar2)
    SeekBar mSeekBar2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_et_and_seekbar);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("EditText和其他控件的联动");
        initEtAndEt();
        initEtAndSb();
        initEtAndSbAI();
    }


    private boolean AFocusChangeFlag;  //valueA焦点状态
    private boolean BFocusChangeFlag;  //valueB焦点状态
    private void initEtAndEt(){
        mEtValueA.setOnFocusChangeListener((view, b) -> AFocusChangeFlag = b);
        mEtValueB.setOnFocusChangeListener((view, b) -> BFocusChangeFlag = b);
        mEtValueA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {
                //首位为"" or 0，置1，输入的数字从1开始
                if (editable.toString().startsWith("0") || editable.toString().equals("") || editable.toString().startsWith(".")) {
                    mEtValueA.setText("1");
                    mEtValueA.setSelection(1);
                    return;
                }
                if(AFocusChangeFlag){  //编辑A的情况下，去改变B
                    mEtValueB.setText(10000 - Integer.valueOf(editable.toString()) +"");
                }
            }
        });
        mEtValueB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().startsWith("0") || editable.toString().equals("") || editable.toString().startsWith(".")) {
                    mEtValueB.setText("1");
                    mEtValueB.setSelection(1);
                    return;
                }
                if(BFocusChangeFlag){ //编辑A的情况下，去改变B
                    mEtValueA.setText(10000 - Integer.valueOf(editable.toString()) +"");
                }
            }
        });

    }


    private int floatRatio;   //浮动比例，范围 [0,100]
    private void initEtAndSb(){
        mEtFloatRatio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //允许输入的范围是[0,100]
                String str = editable.toString();
                if (str.startsWith("00") || str.equals("")) {
                    mEtFloatRatio.setText("0");
                    mEtFloatRatio.setSelection(1);
                    return;
                }
                floatRatio = Integer.valueOf(str);
                if (floatRatio>100){
                    mEtFloatRatio.setText("100");
                    mEtFloatRatio.setSelection(3);
                    return;
                }
                //改变进度条
                mSeekBar.setProgress(floatRatio);
            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * 进度条改变事件
             * @param seekBar
             * @param progress  进度条
             * @param fromUser  是否是用户操作，用户手动滑动时-true，调用setProgress()-false
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){ //是用户操作,去影响EditText
                    mEtFloatRatio.setText(progress+"");
                }
            }

            /**
             * 进度条开始拖动事件
             * @param seekBar
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            /**
             * 进度条停止拖动事件（手指离开屏幕后）
             * @param seekBar
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private boolean focusChangeFlag;  //焦点状态
    private void initEtAndSbAI(){
        mEtFloatRatio2.setOnFocusChangeListener((view, b) -> focusChangeFlag = b);
        mEtFloatRatio2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //允许输入的范围是[0,100]
                String str = editable.toString();
                if (str.startsWith("00") || str.equals("")) {
                    mEtFloatRatio2.setText("0");
                    mEtFloatRatio2.setSelection(1);
                    return;
                }
                floatRatio = Integer.valueOf(str);
                if (floatRatio>100){
                    mEtFloatRatio2.setText("100");
                    mEtFloatRatio2.setSelection(3);
                    return;
                }
                //改变进度条
                if(focusChangeFlag){
                    mSeekBar2.setProgress(floatRatio);
                }
            }
        });
        mSeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * 进度条改变事件
             * @param seekBar
             * @param progress  进度条
             * @param fromUser  是否是用户操作，用户手动滑动时-true，调用setProgress()-false
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){ //是用户操作,去影响EditText
                    mEtFloatRatio2.clearFocus();
                }
                if(!focusChangeFlag){
                    mEtFloatRatio2.setText(progress+"");
                }
            }

            /**
             * 进度条开始拖动事件
             * @param seekBar
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            /**
             * 进度条停止拖动事件（手指离开屏幕后）
             * @param seekBar
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void verify1(){
        /**
         * 经过测试单字符输入删除、中间输入删除、多字符输入删除等，发现beforeTextChanged()和onTextChanged()
         * 的后面三个参数打印的数据是一一对应的，即start - start，count - before ，after - count。
         * */
        mEtValueA.addTextChangedListener(new TextWatcher() {
            /**
             * 输入框改变前的内容
             * @param charSequence   输入前字符串
             * @param start          起始光标
             * @param count          删除字符串的数量（这里的count是用str.length()计算的，因为删除一个emoji表情，count打印结果是 2）
             * @param after          输入框中改变后的字符串与起始位置的偏移量（也就是输入字符串的length）
             */
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                KLog.e(TAG,"beforeTextChanged()---------------");
                KLog.e(TAG,"输入前字符串(charSequence):" + charSequence.toString());
                KLog.e(TAG,"起始光标(start):" + start);
                KLog.e(TAG,"字符串改变数量(count):" + count);
                KLog.e(TAG,"结束偏移量(after):" + after);
            }

            /**
             * 输入框改变后的内容
             * @param charSequence  字符串信息
             * @param start         起始光标
             * @param before        输入框中改变前的字符串与起始位置的偏移量（也就是删除字符串的length）
             * @param count         输入字符串的数量（输入一个emoji表情，count打印结果是2）
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                KLog.e(TAG,"onTextChanged()-------------------");
                KLog.e(TAG,"输入后字符串(charSequence):" + charSequence.toString());
                KLog.e(TAG,"起始光标(start):" + start);
                KLog.e(TAG,"改变前的字符串的位置(before):" + before);
                KLog.e(TAG,"字符串改变数量(count):" + count);
            }

            /**
             * @param editable  输入结束呈现在输入框中的信息
             */
            @Override
            public void afterTextChanged(Editable editable) {
                KLog.e(TAG,"afterTextChanged()-------------");
                KLog.e(TAG,"输入框显示的信息:" + editable.toString());
            }
        });


        mEtValueA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                focusChangeFlag = b;
            }
        });
    }

    @OnClick({R.id.decIv, R.id.incIv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.decIv:
                mEtFloatRatio2.clearFocus();
                if(floatRatio!=0){
                    mSeekBar2.setProgress(floatRatio--);
                }
                break;
            case R.id.incIv:
                mEtFloatRatio2.clearFocus();
                if(floatRatio!=100){
                    mSeekBar2.setProgress(floatRatio++);
                }
                break;
        }
    }
}
