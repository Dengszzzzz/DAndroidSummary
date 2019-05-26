package com.sz.dzh.dandroidsummary.bean;

/**
 * Created by dengzh on 2018/3/6.
 * 标签实体类
 */

public class TagBean {

    private String key;
    private String value;
    private boolean isSelect;

    public TagBean(String key, String value, boolean isSelect) {
        this.key = key;
        this.value = value;
        this.isSelect = isSelect;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
