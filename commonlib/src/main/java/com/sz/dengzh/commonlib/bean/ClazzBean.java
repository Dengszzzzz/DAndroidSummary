package com.sz.dengzh.commonlib.bean;

/**
 * Created by dengzh on 2018/1/17.
 * 类
 */

public class ClazzBean {

    private Class clazz;
    private String clazzName;
    private String hostAndPath;  //路由路径

    public ClazzBean() {
    }

    public ClazzBean(String clazzName, Class clazz) {
        this.clazz = clazz;
        this.clazzName = clazzName;
    }

    public ClazzBean(String clazzName, String hostAndPath) {
        this.clazzName = clazzName;
        this.hostAndPath = hostAndPath;
    }

    public String getHostAndPath() {
        return hostAndPath;
    }

    public void setHostAndPath(String hostAndPath) {
        this.hostAndPath = hostAndPath;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }



}
