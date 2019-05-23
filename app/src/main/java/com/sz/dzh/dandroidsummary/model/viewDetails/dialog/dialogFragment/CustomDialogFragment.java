package com.sz.dzh.dandroidsummary.model.viewDetails.dialog.dialogFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.sz.dzh.dandroidsummary.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by dengzh on 2018/4/19
 * 带标题和内容的双按钮对话弹窗
 * 1.缺点：
 * 相比BaseDialog，DialogFragment需要写多许多方法，且无法在界面未创建前获取控件id，做处理，可操作性不强
 * 2.优点：
 * DialogFragment不用考虑手机旋转，重建activity问题
 */

public class CustomDialogFragment extends BaseDialogFragment {

    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.contentTv)
    TextView contentTv;
    @BindView(R.id.cancelTv)
    TextView cancelTv;
    @BindView(R.id.confirmTv)
    TextView confirmTv;
    Unbinder unbinder;

    private String title;
    private String content;
    private String confirmText;


    /**
     * 对于一个通用弹窗，基本样式固定只改变文字而已，如需做更多变化，需提供一个getView方法
     * @param title
     * @param content
     * @param confirmText
     * @return
     */
    public static CustomDialogFragment newInstance(String title, String content, String confirmText){
        CustomDialogFragment fragment = new CustomDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        bundle.putString("content",content);
        bundle.putString("confirmText",confirmText);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        title = getArguments().getString("title");
        content = getArguments().getString("content");
        confirmText = getArguments().getString("confirmText");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContainerView = inflater.inflate(R.layout.dialog_two_button_title, null);
        unbinder = ButterKnife.bind(this, mContainerView);
        init();
        return mContainerView;
    }

    @Override
    public void onStart() {
        super.onStart();
        setMatchParent();  //设置宽度填满空间
    }

    private void init() {
        titleTv.setText(title);
        contentTv.setText(content);
        confirmTv.setText(confirmText);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.closeIv, R.id.cancelTv, R.id.confirmTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.closeIv:
                if(mDialogSelect!=null){
                    mDialogSelect.onSelected(-1,null);
                }
                break;
            case R.id.cancelTv:
                if(mDialogSelect!=null){
                    mDialogSelect.onSelected(0,null);
                }
                break;
            case R.id.confirmTv:
                if(mDialogSelect!=null){
                    mDialogSelect.onSelected(1,null);
                }
                break;
        }
        dismiss();
    }
}
