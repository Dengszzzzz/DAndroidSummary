package com.sz.dengzh.javasummary.module.compare;

/**
 * Created by dengzh on 2019/10/7
 * 方式1：实现Comparable接口
 */
public class Student implements Comparable<Student>{

    private String name;
    private int age;
    private float score;

    public Student(String name, int age, float score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }


    /**
     * @param o
     * @return  返回值 <0，表示交换顺序，一把返回值用 1 0 -1
     */
    @Override
    public int compareTo(Student o) {
        //后面的score 比 前面的score 大，则交换顺序，所以分数是降序
        if(this.score > o.score){
            return -1;
        }else if(this.score < o.score){
            return 1;
        }else{
            if(this.age > o.age){
                return 1;
            }else if(this.age < o.age){
                //后面的年龄比前面的年龄小，则交换顺序，所以年龄是升序
                return -1;
            }
            return 0;
        }
    }
}
