package com.sz.dzh.dandroidsummary.widget.recyclerview.sticky;

import android.view.View;

/**
 * Created by cpf on 2018/1/16.
 */

public class ExampleStickyView implements StickyView {

    /**
     * 判断是否是吸附View
     * @param view
     * @return
     */
    @Override
    public boolean isStickyView(View view) {
        if(view==null || view.getTag() == null){
            return false;
        }
        return (Boolean) view.getTag();
    }

    /**
     * 吸附View 在Adapter中的 ViewType
     * @return
     */
    @Override
    public int getStickViewType() {
        return StickyView.ViewType;
    }
}
