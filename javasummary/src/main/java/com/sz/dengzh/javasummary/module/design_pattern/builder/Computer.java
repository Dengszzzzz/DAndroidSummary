package com.sz.dengzh.javasummary.module.design_pattern.builder;

/**
 * Created by dengzh on 2019/10/4
 * Builder模式
 * 将一个复杂对象的构建与它的表示分离，用Builder模式构建一个不可变的配置对象，只在构建时设置它的参数。可看retrofit、glide、AlertDialog。
 *
 * 通常为链式调用，如下。
 * 1、外部类构造函数、字段私有化。(private or protect)
 * 2、静态内部类 Builder
 *   1）初始化时创建外部类。
 *   2）setXX()，实际上是设置外部类的属性，同时返回Builder自身，this。
 *   3）create()，返回外部类。
 */
public class Computer {

    private String mBoard;
    private String mDisplay;
    private String mOs;

    private Computer(){

    }

    @Override
    public String toString() {
        return "Computer{" +
                "mBoard='" + mBoard + '\'' +
                ", mDisplay='" + mDisplay + '\'' +
                ", mOs='" + mOs + '\'' +
                '}';
    }

    public static class Builder{
        private Computer mComputer;

        public Builder() {
            this.mComputer = new Computer();
        }

        public Builder setmBoard(String mBoard) {
            mComputer.mBoard = mBoard;
            return this;
        }

        public Builder setmDisplay(String mDisplay) {
            mComputer.mDisplay = mDisplay;
            return this;
        }

        public Builder setmOs(String mOs) {
            mComputer.mOs = mOs;
            return this;
        }

        public Computer create(){
            return mComputer;
        }

    }



}
