package com.sz.dzh.dandroidsummary.model.viewDetails.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dzh.dandroidsummary.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2018/4/24.
 * AgentWeb 简单使用  常规操作页面
 */

public class AgentWebActivity extends BaseActivity {

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
        Intent intent = new Intent(context, AgentWebActivity.class);
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
    }

    private void initAgentWebView() {
        agentWebView = AgentWeb.with(this)
                .setAgentWebParent(webLl,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()      // 使用默认进度条
                .defaultProgressBarColor() // 使用默认进度条颜色
                .setSecurityType(AgentWeb.SecurityType.strict)
                .createAgentWeb()
                .ready()
                .go(webUrl);
    }

    /*@Override
    protected void onPause() {
        //mAgentWeb.getWebLifeCycle().onPause();会暂停应用内所有WebView 。
        //所以最好别加了，不然webView都无法正常工作了
        agentWebView.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        agentWebView.getWebLifeCycle().onResume();
        super.onResume();
    }*/

}
