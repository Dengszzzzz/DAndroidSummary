package com.sz.dzh.dandroidsummary.model.problems;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import com.socks.library.KLog;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;

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
 */
public class EtAndSeekBarActivity extends BaseActivity {

    @BindView(R.id.et_price)
    EditText mEtPrice;
    @BindView(R.id.et_count)
    EditText mEtCount;
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
    }

    private void initEtAndEt(){
        mEtPrice.addTextChangedListener(new TextWatcher() {
            /**
             * 输入框改变前
             * @param charSequence   输入前字符串
             * @param start          起始光标
             * @param count          字符串改变数量
             * @param after          输入框中改变后的字符串与起始位置的偏移量
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
             * 输入框改变后
             * @param charSequence  字符串信息
             * @param start         字符串的起始位置
             * @param before        输入框中改变前的字符串的位置 默认为0
             * @param count         一共输入字符串的数量
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                KLog.e(TAG,"beforeTextChanged()-------------------");
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
        mEtCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick({R.id.decIv, R.id.incIv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.decIv:
                break;
            case R.id.incIv:
                break;
        }
    }
}
