package com.sz.dzh.dandroidsummary.model.viewDetails.viewpager.tabpager;

/**
 * Created by dengzh on 2018/4/10.
 * 活动类型
 */

public class EventTypeBean {

    /**
     * picture : http://oqwos88hj.bkt.clouddn.com/NNLB1523263153523
     * id : 1
     * typename : 最新招商会
     */

    private int id;
    private String typename;
    private String picture;


    public EventTypeBean(int id, String typename, String picture) {
        this.id = id;
        this.typename = typename;
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

}
