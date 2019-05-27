package com.sz.dzh.dandroidsummary.model.viewDetails.webview;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sz.dzh.dandroidsummary.base.BaseListShowActivity;
import com.sz.dzh.dandroidsummary.utils.ToastUtils;

/**
 * Created by dengzh on 2018/4/18.
 */

public class WebListActivity extends BaseListShowActivity {

    private String webData = "<p style=\"line-height: 2em;\"><strong><span style=\"font-family:宋体\">测试文本</span></strong></p><p style=\"line-height: 2em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style=\"font-family:宋体\">犊牛是指</span>3<span style=\"font-family:宋体\">～</span>6<span style=\"font-family:宋体\">月龄之内以乳汁为主要营养来源的初生小牛。由于其消化特点和成牛有显著不同，因此，在饲养管理上也应有自己的特点，主要突出u201c三早u201d，把好u201c三关u201d，防好u201c两病u201d。</span></p><p style=\"line-height: 2em;\">(<span style=\"font-family:宋体\">一</span>)<span style=\"font-family:宋体\">早吃初乳母牛分娩后一周内所产的乳叫初乳。初乳中含有十分丰富的养分、各种抗体和溶菌酶，对小牛的健康成长十分有益。因此，小牛出生后，要在</span>30<span style=\"font-family:宋体\">分钟内让其吃到初乳，以确保小牛的健康生长。</span></p><p style=\"line-height: 2em;\">(<span style=\"font-family:宋体\">二</span>)<span style=\"font-family:宋体\">早期断奶自然哺乳一般让犊牛吃六个月的初乳，人工哺乳也要吃三个月的母乳。早期断奶一般指</span>1<span style=\"font-family:宋体\">～</span>2<span style=\"font-family:宋体\">月龄断奶。促进犊牛瘤胃的早期发育，从而提高犊牛的培育质量，提高犊牛的成活率，减少死亡损失。</span></p><p style=\"line-height: 2em;\">(<span style=\"font-family:宋体\">三</span>)<span style=\"font-family:宋体\">及早开食就是让犊牛尽量早一点吃上草料。犊牛一般在</span>10<span style=\"font-family:宋体\">日龄时出现反刍，</span>15<span style=\"font-family:宋体\">日龄就可以采食一点柔软的干草，</span>30<span style=\"font-family:宋体\">日龄时其胃肠机能已基本发育健全。生产中为促进犊牛的胃肠发育和机能健全，一般于</span>10<span style=\"font-family:宋体\">日龄前，就开始喂给易消化的麦麸、玉米粉。</span></p><p><br/></p>";
    private String url = "https://www.baidu.com/";


    @Override
    protected void initUI() {
        tvTitle.setText("WebView示例");
    }

    @Override
    protected void initData() {

        addClazzBean("WebView Url",WebViewActivity.class);
        addClazzBean("WebView Data",WebViewActivity.class);
        addClazzBean("AgentWeb",AgentWebActivity.class);
        addClazzBean("AgentWeb JS交互",AgentWebActivity.class);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        WebViewActivity.goWebViewActivity(WebListActivity.this,"webView",url,"");
                        break;
                    case 1:
                        WebViewActivity.goWebViewActivity(WebListActivity.this,"webView","",webData);
                        break;
                    case 2:
                        AgentWebActivity.goAgentWebActivity(WebListActivity.this,"AgentWeb",url);
                        break;
                    case 3:
                        ToastUtils.showToast("进入此页面，将无法退出");
                        AgentWebJSActivity.goAgentWebActivity(WebListActivity.this,"AgentWeb JS交互",url);
                        break;
                }
            }
        });

        mAdapter.notifyDataSetChanged();
    }
}
