package com.sz.dzh.dandroidsummary.model.viewDetails.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.widget.custom.EllipsizeTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2019/11/12
 * 参考：
 * [Android TextView 在指定位置自动省略字符](https://juejin.im/post/5b2c6be3e51d4558bd51849d)
 */
public class TextViewEllipsizeActivity extends BaseActivity {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.tv_ellipsize)
    EllipsizeTextView tvEllipsize;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_text_view_ellipsize);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("TextView 在指定位置自动省略字符");

        textView.post(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });

        tvEllipsize.post(new Runnable() {
            @Override
            public void run() {
                String originText = "去广东省深圳市罗湖区人民医院(留医部)门口旁边的小卖铺接乘客";
                tvEllipsize.setEllipsisText(originText,3);
            }
        });

    }

    private void init() {
        //1.获取原文字在控件上占满的长度
        String originText = "Android中可以通过 android:ellipsize=来指定文字过长时的省略方式.mp4";
        //获取原文字长度
        float originTextWidth = textView.getPaint().measureText(originText);
        //获取控件长度
        float textViewWidth = textView.getWidth();

        //2.判断控件是否可以装满文字
        if (textViewWidth >= originTextWidth) {
            textView.setText(originText);
            return;
        }

        //3.获取指定省略位置 对于文件名来说最后的"."是省略部分的标志
        int lastIndexOfPoint = originText.lastIndexOf(".");
        if (lastIndexOfPoint == -1) {
            //找不到 直接显示
            textView.setText(originText);
            return;
        }

        //4.根据省略位置对字符串切分
        //前缀 文件名 "这可能是一个专门刁难程序员的很长的文件名"
        String prefixText = originText.substring(0, lastIndexOfPoint);
        //后缀 添加省略符号 "...mp4"
        String suffixText = ".." + originText.substring(lastIndexOfPoint, originText.length());

        //5.不断递减指定位置前的字符串，以此来获取满足条件的前缀字符串。
        float prefixWidth = textView.getPaint().measureText(prefixText);
        float suffixWidth = textView.getPaint().measureText(suffixText);
        //后缀太长 不处理
        if (suffixWidth > textViewWidth) {
            textView.setText(originText);
        } else {
            //每减少前缀一个字符都去判断是否能塞满控件
            while (textViewWidth - prefixWidth < suffixWidth) {
                prefixText = prefixText.substring(0, prefixText.length() - 1);
                //关键
                prefixWidth = textView.getPaint().measureText(prefixText);
            }
            //能塞满
            textView.setText(prefixText + suffixText);
        }

    }
}
