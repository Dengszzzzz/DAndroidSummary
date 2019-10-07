package com.sz.dengzh.javasummary.module.design_pattern.stragety;

/**
 * Created by dengzh on 2019/10/4
 */
public class SubwayStrategy implements CalculatorStrategy {

    @Override
    public int calculatePrice(int km) {
        if(km <= 6){
            return 3;
        }else if(km < 12){
            return 5;
        }
        return 7;
    }
}
