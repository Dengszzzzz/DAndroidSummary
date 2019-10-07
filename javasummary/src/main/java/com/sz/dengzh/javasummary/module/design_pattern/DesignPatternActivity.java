package com.sz.dengzh.javasummary.module.design_pattern;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.socks.library.KLog;
import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dengzh.javasummary.R;
import com.sz.dengzh.javasummary.bean.AnimalBean;
import com.sz.dengzh.javasummary.bean.CatBean;
import com.sz.dengzh.javasummary.bean.DogBean;
import com.sz.dengzh.javasummary.bean.StaitcTestBean;
import com.sz.dengzh.javasummary.bean.StaitcTestBean2;
import com.sz.dengzh.javasummary.module.design_pattern.builder.Computer;
import com.sz.dengzh.javasummary.module.design_pattern.factory.ConcreteFactory;
import com.sz.dengzh.javasummary.module.design_pattern.factory.ConcreteProduceA;
import com.sz.dengzh.javasummary.module.design_pattern.factory.ConcreteProduceB;
import com.sz.dengzh.javasummary.module.design_pattern.factory.Factory;
import com.sz.dengzh.javasummary.module.design_pattern.factory.Product;
import com.sz.dengzh.javasummary.module.design_pattern.proxy.DynamicProxy;
import com.sz.dengzh.javasummary.module.design_pattern.proxy.ILawsuit;
import com.sz.dengzh.javasummary.module.design_pattern.proxy.Lawyer;
import com.sz.dengzh.javasummary.module.design_pattern.proxy.XiaoMin;
import com.sz.dengzh.javasummary.module.design_pattern.state.TvController;
import com.sz.dengzh.javasummary.module.design_pattern.stragety.BusStrategy;
import com.sz.dengzh.javasummary.module.design_pattern.stragety.SubwayStrategy;
import com.sz.dengzh.javasummary.module.design_pattern.stragety.TaxiStrategy;
import com.sz.dengzh.javasummary.module.design_pattern.stragety.TranficCalculator;

import java.lang.reflect.Proxy;

import retrofit2.Retrofit;

/**
 * Created by dengzh on 2019/9/10
 */
public class DesignPatternActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_ac_test);
        initTitle();
        tvTitle.setText("设计模式");
        testBuilder();
        testFactory();
        testStrategy();
        testState();
       // testProxy();
        testDynamicProxy();
    }

    private void testBuilder(){
        Computer computer = new Computer.Builder()
                .setmBoard("微星")
                .setmDisplay("飞利浦")
                .setmOs("win 10")
                .create();
        KLog.e(TAG,computer.toString());
    }

    private void testFactory(){
        Factory factory = new ConcreteFactory();
        Product pA = factory.createProduce(ConcreteProduceA.class);
        pA.method();

        Product pB = factory.createProduce(ConcreteProduceB.class);
        pB.method();
    }

    /**
     * 策略模式
     * 如果不用策略模式，那么每添加一种道具，都要写一个计算方式，和if-else判断。
     * 抽象策略、具体策略、用来操作策略的上下文环境。
     *
     * 应用场景举例：
     * 1）对应上面的3个，在Android动画中
     *    抽象策略：Interpolator
     *    具体策略：LinearInterpolator、AccelerateInterpolator 等。
     *    用来操作策略的上下文环境：Animation
     *
     * 理解：
     * 策略模式主要用来分离算法，在相同的行为抽象下有不同的具体实现策略。可以不用写 if-esle 或 switch-case。
     * 使用场景如下，针对同一类型的问题的多种方式，仅仅是具体行为有差别时（如排序算法）。
     * 出现同一抽象类有多个子类，而又需要使用if-else或switch-case来选择具体子类时。
     */
    private void testStrategy(){
        TranficCalculator calculator = new TranficCalculator();
        calculator.setStrategy(new BusStrategy());
        KLog.e(TAG,"巴士计算价格:" + calculator.calculatePrice(10));

        calculator.setStrategy(new SubwayStrategy());
        KLog.e(TAG,"地铁计算价格:" + calculator.calculatePrice(10));

        calculator.setStrategy(new TaxiStrategy());
        KLog.e(TAG,"的士计算价格:" + calculator.calculatePrice(10));
    }


    /**
     * 状态模式
     * 它和策略模式的结构几乎一样，但目的和本质完全不一样。状态模式的行为是平行的、不可替换的。策略模式的行为是彼此独立、可互相替换的。
     *
     * 使用场景：
     * 1）一个对象的行为取决于它的状态，并且它必须在运行时根据状态去改变它的行为。
     * 2）代码种包含大量与对象状态有关的条件语句。
     *
     * 理解：
     * 关键点在于不同的状态下对于同一个行为有不同的相应，其实就是用多态来代替if-else，比例用户是否登录状态，
     * 电视的开机关机状态等（写一个操作接口，再写开机状态类、关机状态类去实现这些操作）
     */
    private void testState(){
        TvController controller = new TvController();
        //开机
        controller.powerOn();
        controller.nextChannel();
        controller.turnUp();
        //关机
        controller.powerOff();
        controller.turnDown();
    }

    /**
     * 代理模式
     * 为其他对象提供一种代理以控制对这个对象的访问，分抽象主题、真实主题、代理类。
     * 静态代理、动态代理。
     *
     */
    private void testProxy(){
        //构建一个真实主题对象
        ILawsuit xiaomin = new XiaoMin();
        //通过真实主题对象构建一个代理对象,静态代理。
        ILawsuit lawyer = new Lawyer(xiaomin);

        //代理操作
        lawyer.submit();
        lawyer.burden();
        lawyer.defend();
        lawyer.finish();
    }

    private void testDynamicProxy(){
        //构建一个真实主题对象
        ILawsuit xiaomin = new XiaoMin();
        //动态代理
        DynamicProxy proxy = new DynamicProxy(xiaomin);
        //获取被代理类小民的ClassLoader
        ClassLoader loader = xiaomin.getClass().getClassLoader();
        //动态构建一个代理类律师
        ILawsuit lawyer = (ILawsuit) Proxy.newProxyInstance(loader,new Class[]{ILawsuit.class},proxy);

        //代理操作
        lawyer.submit();
        lawyer.burden();
        lawyer.defend();
        lawyer.finish();
    }
}
