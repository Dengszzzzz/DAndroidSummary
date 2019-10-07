package com.sz.dengzh.javasummary.module.design_pattern.stragety;

/**
 * Created by dengzh on 2019/10/4
 */
public class TranficCalculator {

    CalculatorStrategy mStrategy;

    public void setStrategy(CalculatorStrategy mStrategy) {
        this.mStrategy = mStrategy;
    }

    public int calculatePrice(int km){
        return mStrategy.calculatePrice(km);
    }
}
