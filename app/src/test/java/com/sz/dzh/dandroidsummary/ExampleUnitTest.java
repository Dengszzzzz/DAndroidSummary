package com.sz.dzh.dandroidsummary;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        System.out.println(250/100);
        System.out.println(250%100);
        //  assertEquals(4, 2 + 2);

        String originText = "去广东省深圳市罗湖区人民医院(留医部)门口旁边的小卖铺接乘客";
        String prefixText = originText.substring(0, originText.length()-3);
        String suffixText = originText.substring(originText.length()-3, originText.length());
        System.out.println(prefixText);
        System.out.println(suffixText);
    }
}