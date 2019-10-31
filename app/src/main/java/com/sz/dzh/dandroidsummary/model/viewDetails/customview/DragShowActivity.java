package com.sz.dzh.dandroidsummary.model.viewDetails.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dengzh.commonlib.utils.ToastUtils;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.widget.custom.SlideView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2019/10/22
 */
public class DragShowActivity extends BaseActivity {


    @BindView(R.id.slideview)
    SlideView slideview;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_drag_show);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("SlideView");
        initView();
        initSlide();
    }

    private void initView() {

    }

    private int num;
    private void initSlide(){
        slideview.addSlideListener(new SlideView.OnSlideListener() {
            @Override
            public void onSlideSuccess() {
                num++;
                slideview.setBackgroundText("滑动" + num + "次");
                if(num % 2 == 0){
                    slideview.setDrawableBg(R.drawable.slideview_bg_enable);
                    slideview.setDrawableIcon(R.mipmap.ic_launcher);
                }else{
                    slideview.setDrawableBg(R.drawable.slideview_bg_enable2);
                    slideview.setDrawableIcon(R.mipmap.btn_slide_handle_enable_false);
                }
                if(num > 2){
                    slideview.setEnabled(false);
                }
                slideview.reset();
            }
        });
    }

}
