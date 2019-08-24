package com.sz.dzh.dandroidsummary.model.viewDetails.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;
;
import com.just.agentweb.AgentWeb;
import com.socks.library.KLog;
import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dzh.dandroidsummary.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2018/4/24.
 * AgentWeb 非常规操作页面，JS交互
 * 下例：对于某些静态页面，可能统一在一个页面跳转，需要JS交互
 * Android 调 H5方法，H5调Android方法
 */

public class AgentWebJSActivity extends BaseActivity {

    @BindView(R.id.webLl)
    LinearLayout webLl;

    AgentWeb agentWebView;
    private String title;
    private String webUrl;

    /**
     * 跳转到WebView界面
     * @param context
     * @param title    标题
     * @param webUrl   url
     */
    public static void goAgentWebActivity(Context context, String title, String webUrl){
        Intent intent = new Intent(context, AgentWebJSActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("webUrl",webUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_agent_web);
        ButterKnife.bind(this);
        initTitle();
        title = getIntent().getStringExtra("title");
        webUrl = getIntent().getStringExtra("webUrl");
        tvTitle.setText(title);
        initAgentWebView();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Android端调用JS端方法，同时也要让JS端调用Android端方法来判断是否要结束当前页面
                //reIndex('-1'),reIndex是H5那边定义的方法，"-1"是参数
                agentWebView.getJsEntraceAccess().quickCallJs("reIndex('-1')");
            }
        });
    }

    private void initAgentWebView() {
        agentWebView = AgentWeb.with(this)
                .setAgentWebParent(webLl,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()      // 使用默认进度条
                .defaultProgressBarColor() // 使用默认进度条颜色
                .addJavascriptInterface("android",new AndroidInterface()) //js交互
                .setSecurityType(AgentWeb.SecurityType.strict)
                .createAgentWeb()
                .ready()
                .go(webUrl);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            agentWebView.getJsEntraceAccess().quickCallJs("reIndex('-1')");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 与Js交互，JS调用Android端的方法
     * Android端设置： xx.addJavascriptInterface("android",new AndroidInterface())
     * JS端调用：      window.android.callAndroid("test");
     */
    private class AndroidInterface {

        @JavascriptInterface
        public void callAndroid(final String result) {
            KLog.e("Info", result);
            if("-1".equals(result)){  //返回-1时
                finish();
            }
        }

        /**
         * 由H5调用，改变标题
         * @param result
         */
        @JavascriptInterface
        public void setTitle(final String result) {
            if(!TextUtils.isEmpty(result)){
                tvTitle.setText(result);  //设置标题
            }
        }
    }

}
