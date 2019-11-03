package com.sz.dzh.dandroidsummary.widget.recyclerview.sticky;

import android.view.View;

/**
 * Created by cpf on 2018/1/16.
 *
 *  获取吸附View相关的信息
 */

public interface StickyView {

    int ViewType = 11;   //吸附View 的 itemType

    /**
     * 是否是吸附view
     * @param view
     * @return
     */
    boolean isStickyView(View view);

    /**
     * 得到吸附view的itemType
     * @return
     */
    int getStickViewType();
}
