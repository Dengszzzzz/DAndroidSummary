package com.sz.dengzh.javasummary.module.design_pattern.stragety;

/**
 * Created by dengzh on 2019/10/4
 */
public class TaxiStrategy implements CalculatorStrategy {

    @Override
    public int calculatePrice(int km) {
        return km * 2;
    }
}
