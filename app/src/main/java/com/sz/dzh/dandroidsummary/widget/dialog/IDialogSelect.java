package com.sz.dzh.dandroidsummary.widget.dialog;

/**
 * Created by dengzh on 2018/4/19.
 */

public interface IDialogSelect {

    /**
     * 兑换框控件选择
     * @param i     控件标志，用来区分控件
     * @param o     拓展，用来传值
     */
    void onSelected(int i, Object o);
}
