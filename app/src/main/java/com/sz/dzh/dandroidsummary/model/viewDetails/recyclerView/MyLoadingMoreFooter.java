package com.sz.dzh.dandroidsummary.model.viewDetails.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.CustomFooterViewCallBack;
import com.jcodecraeer.xrecyclerview.LoadingMoreFooter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.SimpleViewSwitcher;
import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;
import com.sz.dengzh.drxjavasummary.module.rxlifecycle.RxlifecContract;
import com.sz.dzh.dandroidsummary.R;

public class MyLoadingMoreFooter extends LinearLayout {

    private SimpleViewSwitcher progressCon;
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;

    private TextView mText;
    private View leftLine,rightLine;
    private String loadingHint;
    private String noMoreHint;
    private String loadingDoneHint;

    private AVLoadingIndicatorView progressView;

	public MyLoadingMoreFooter(Context context) {
		super(context);
		initView();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public MyLoadingMoreFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

    public void initView(){
        setGravity(Gravity.CENTER);
        setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

	    View rootView = LayoutInflater.from(getContext()).inflate(R.layout.xrv_footer, this);

        //加载圈
        progressCon = rootView.findViewById(R.id.progressCon);
        progressView = new  AVLoadingIndicatorView(this.getContext());
        progressView.setIndicatorColor(0xffB5B5B5);
        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
        progressCon.setView(progressView);

        //提示文字
        leftLine = rootView.findViewById(R.id.leftLine);
        rightLine = rootView.findViewById(R.id.rightLine);
        mText = rootView.findViewById(R.id.descTv);
        mText.setText(getContext().getString(R.string.listview_loading));

        if(loadingHint == null || loadingHint.equals("")){
            loadingHint = (String)getContext().getText(R.string.listview_loading);
        }
        if(noMoreHint == null || noMoreHint.equals("")){
            noMoreHint = "到底了";
        }
        if(loadingDoneHint == null || loadingDoneHint.equals("")){
            loadingDoneHint = (String)getContext().getText(R.string.loading_done);
        }

    }

    public void setProgressStyle(int style) {
        if(style == ProgressStyle.SysProgress){
            progressCon.setView(new ProgressBar(getContext(), null, android.R.attr.progressBarStyle));
        }else{
            progressView = new  AVLoadingIndicatorView(this.getContext());
            progressView.setIndicatorColor(0xffB5B5B5);
            progressView.setIndicatorId(style);
            progressCon.setView(progressView);
        }
    }

    public void  setState(int state) {
        switch(state) {
            case STATE_LOADING:
                leftLine.setVisibility(GONE);
                rightLine.setVisibility(GONE);
                progressCon.setVisibility(View.VISIBLE);
                mText.setText(loadingHint);
                this.setVisibility(View.VISIBLE);
                    break;
            case STATE_COMPLETE:
                leftLine.setVisibility(GONE);
                rightLine.setVisibility(GONE);
                mText.setText(loadingDoneHint);
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                leftLine.setVisibility(VISIBLE);
                rightLine.setVisibility(VISIBLE);
                mText.setText(noMoreHint);
                progressCon.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }
    }

    CustomFooterViewCallBack callBack = new CustomFooterViewCallBack() {
        @Override
        public void onLoadingMore(View yourFooterView) {
            setState(STATE_LOADING);
        }

        @Override
        public void onLoadMoreComplete(View yourFooterView) {
            setState(STATE_COMPLETE);
        }

        @Override
        public void onSetNoMore(View yourFooterView, boolean noMore) {
            setState(STATE_NOMORE);
        }
    };
}
