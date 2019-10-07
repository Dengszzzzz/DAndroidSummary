package com.sz.dengzh.javasummary.module.design_pattern.factory;

/**
 * Created by dengzh on 2019/10/4
 * 具体工厂类
 */
public class ConcreteFactory extends Factory{

    /**
     * 通过反射获取类的实例，可以避免创建多个具体工厂类
     * @param clz 产品对象类类型
     * @param <T>
     * @return
     */
    @Override
    public <T extends Product> T createProduce(Class<T> clz) {
        Product p = null;
        try {
            p = (Product) Class.forName(clz.getName()).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (T) p;
    }
}
