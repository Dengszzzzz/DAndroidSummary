package com.sz.dzh.dandroidsummary.model.specialFunc.emoji_encoder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;
import com.sz.dzh.dandroidsummary.utils.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Emoji表情以及URLEncoder编码
 */
public class EmojiEncoderActivity extends BaseActivity {


    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.tv_encode)
    TextView mTvEncode;
    @BindView(R.id.tv_decode)
    TextView mTvDecode;
    @BindView(R.id.tv_encode2)
    TextView mTvEncode2;
    @BindView(R.id.tv_decode2)
    TextView mTvDecode2;
    @BindView(R.id.tv_filter)
    TextView mTvFilter;
    @BindView(R.id.tv_code_point_count)
    TextView mTvCodePointCount;
    @BindView(R.id.tv_length)
    TextView mTvLength;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_emoji_encoder);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("Emoji表情编解码");
    }

    @OnClick(R.id.btn_code)
    public void onViewClicked() {
        String src = mEtContent.getText().toString().trim();
        if (TextUtils.isEmpty(src)) {
            ToastUtils.showToast("请输入包含表情的内容");
            return;
        }

        //1.codePointCount 和 length 对比
        mTvLength.setText("String.length()：" + src.length());
        mTvCodePointCount.setText("String.codePointCount：" + src.codePointCount(0, src.length()));

        //2.对整段编码和解码
        try {
            //编码
            String encodeStr = URLEncoder.encode(src, "UTF-8");
            //解码
            String decodeStr = URLDecoder.decode(encodeStr, "UTF-8");
            mTvEncode.setText("编码：" + encodeStr);
            mTvDecode.setText("解码：" + decodeStr);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //3.对表情单独编码、对整段解码（因为对整段编码，也是能解码出正确的原文内容的）
        try {
            String encodeStr = EmojiUtils.escape(src);
            mTvEncode2.setText("对表情单独编码：" + encodeStr);
            mTvDecode2.setText("对整段解码能还原内容：" + URLDecoder.decode(encodeStr, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        mTvFilter.setText("过滤表情：" + EmojiUtils.filter(src));
    }

    /**
     *1.位、字节、字符的概念和区别。
     *  位（bit）：  数据存储的最小单位，每个二进制数字0或者1就是1个位，比如 11010101是一个八位二进制数。
     *  字节（byte）：8个位构成一个字节；即：1 byte (字节)= 8 bit(位)；1 KB = 1024 B(字节)；
     *  字符： 是指计算机中使用的字母、数字、字和符号，给用户看的。
     *  字符集：  各种各个字符的集合，也就是某些汉字、字母（A、b、c）和符号（空格、引号..）会被收入标准中；
     *           例如ASCii字符集，gb2312字符集，Unicode字符集等。
     *  编码：  规定每个“字符”分别用一个字节还是多个字节存储，用哪些字节来存储，这个规定就叫做“编码”。通俗来讲，
     *         编码就是按照规则对字符进行翻译成对应的二进制数，在计算器中运行存储，用户看的时候，再解码显示成用户能看得懂的。
     *
     *2.UTF-8编码，字节和字符的对应关系。
     *  1个英文字符 和 英文标点 = 1个字节
     *  1个中文（含繁体） 和 中文标点 = 3个字节
     *  Emoji表情 和 某些特殊字符 = 4个字节
     * */
}
