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
 * Created by Dengzh
 * on 2019/6/29
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
}
