package com.sz.dzh.dandroidsummary.model.viewDetails.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.widget.webview.MyWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2018/4/24.
 */

public class WebViewActivity extends BaseActivity {


    @BindView(R.id.webview)
    MyWebView webView;

    //优先加载webUrl
    private String webUrl;
    private String webData;

    /**
     * 跳转到WebView界面
     * @param context
     * @param title    标题
     * @param webUrl   url
     * @param webData  data
     */
    public static void goWebViewActivity(Context context, String title, String webUrl, String webData){
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("webUrl",webUrl);
        intent.putExtra("webData",webData);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_webview);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText(getIntent().getStringExtra("title"));
        webUrl = getIntent().getStringExtra("webUrl");
        webData = getIntent().getStringExtra("webData");
        initView();
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView.canGoBack()){
                    webView.goBack();
                }else{
                    finish();
                }
            }
        });
    }

    private void initView() {
        if(!TextUtils.isEmpty(webUrl)){
            webView.loadUrl(webUrl);
        }else if(!TextUtils.isEmpty(webData)){
            webView.loadData(webData);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview能返回的话，WebView执行回退操作，
        //因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if(keyCode== KeyEvent.KEYCODE_BACK&&webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
