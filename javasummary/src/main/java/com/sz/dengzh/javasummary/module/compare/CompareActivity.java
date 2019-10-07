package com.sz.dengzh.javasummary.module.compare;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.socks.library.KLog;
import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dengzh.javasummary.R;
import com.sz.dengzh.javasummary.bean.AnimalBean;
import com.sz.dengzh.javasummary.bean.CatBean;
import com.sz.dengzh.javasummary.bean.DogBean;
import com.sz.dengzh.javasummary.bean.StaitcTestBean;
import com.sz.dengzh.javasummary.bean.StaitcTestBean2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by dengzh on 2019/9/10
 */
public class CompareActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_ac_test);
        initTitle();
        tvTitle.setText("CompareTo 理解");

        //test1();
        testStudent1();
       //testStudent2();
    }


    private void test1(){
        List<Integer> re = new ArrayList<>();
        re.add(1);
        re.add(2);
        re.add(6);
        re.add(5);
        re.add(8);
        re.add(8);
        re.add(4);

        Collections.sort(re, new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
                KLog.e(TAG,"o1 = " + o1);
                KLog.e(TAG,"o2 = " + o2);
                if(o1 < o2){
                    return 1;
                }else if(o1 > o2){
                    return -1;
                }
                return 0;
            }

        });
        KLog.e(re);
    }

    private void testStudent1(){
        Student stu[]={new Student("zhangsan",20,90.0f),
                new Student("lisi",22,90.0f),
                new Student("wangwu",20,99.0f),
                new Student("sunliu",22,100.0f)};
        Arrays.sort(stu);
        for(Student s:stu) {
            KLog.e(TAG,s);
        }
        //打印结果是：分数降序，同分情况下，年龄升序
    }


    private void testStudent2(){
        Student stu[]={new Student("zhangsan",20,90.0f),
                new Student("lisi",22,90.0f),
                new Student("wangwu",20,99.0f),
                new Student("sunliu",22,100.0f)};
        Arrays.sort(stu,new StudentComparator());
        for(Student s:stu) {
            KLog.e(TAG,s);
        }
        //打印结果是：分数降序，同分情况下，年龄升序
    }

}
