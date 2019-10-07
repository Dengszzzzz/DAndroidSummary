package com.sz.dengzh.javasummary.module.compare;

import java.util.Comparator;

/**
 * Created by dengzh on 2019/10/7
 * 方式1：实现Comparator 接口
 */
public class StudentComparator implements Comparator<Student> {


    /**
     * 原序列里 o1 在 o2 的后面一个
     * @param o1
     * @param o2
     * @return  返回值 <0，表示交换顺序，一把返回值用 1 0 -1
     */
    @Override
    public int compare(Student o1, Student o2) {
        if(o1.getScore() > o2.getScore()){
            return -1;
        }else if(o1.getScore() < o2.getScore()){
            return 1;
        }else{
            if(o1.getAge() > o2.getAge()){
                return 1;
            }else if(o1.getAge() < o2.getAge()){
                return -1;
            }
            return 0;
        }
    }
}
