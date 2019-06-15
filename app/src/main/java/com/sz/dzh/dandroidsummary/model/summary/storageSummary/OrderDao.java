package com.sz.dzh.dandroidsummary.model.summary.storageSummary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sz.dzh.dandroidsummary.bean.OrderBean;


/**
 * Created by administrator on 2018/8/16.
 * sqlite总结：
 * 1.继承SqliteOpenHelper的DBHelper，创建/更新数据库，onCreate()会创建一次，onUpdate()对比version后决定是否执行。
 *   onUpdate()更新数据库，一般需要先备份原数据，再删除数据库，重新创建并导入旧数据
 * 2.SQLiteDatabase
 *   在增删改时，得到可写数据库 mDBHelper.getWritableDatabase(),记得最后调用db.close();
 *   查：getReadableDatabase()可读数据库，用query(xx)查出的数据是Cursor，配合使用转成自己所需内容
 *   query(xxx)的参数较多，可看源码了解。
 * 难点:
 *   数据库升级步骤：
 *   1.表A重命名，A_temp
 *   2.创建新表表A
 *   3.将A_temp数据插入表A
 *   4.删除表A_temp
 */
public class OrderDao {

    private Context mContext;
    private DBHelper mDBHelper;

    public OrderDao(Context context) {
        mContext = context;
        mDBHelper = new DBHelper(context);  //数据库帮助类
    }

    /**
     * 增
     * @param bean
     */
    public void add(OrderBean bean){
        //得到一个可写的数据库
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        //往ContentValues对象存放数据，键-值对模式
        ContentValues values = new ContentValues();
        values.put("Id", bean.id);
        values.put("CustomName", bean.customName);
        values.put("OrderPrice", bean.orderPrice);
        values.put("Country", bean.country);
        //调用insert方法，将数据插入数据库
        db.insert(DBHelper.TABLE_NAME,null,values);
        //关闭数据库
        db.close();
    }

    /**
     * 删
     * @param id
     */
    public void deleteOrder(int id) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.delete(DBHelper.TABLE_NAME, "Id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * 改
     * @param bean
     */
    public void update(OrderBean bean){
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Id", bean.id);
        values.put("CustomName", bean.customName);
        values.put("OrderPrice", bean.orderPrice);
        values.put("Country", bean.country);
        db.update(DBHelper.TABLE_NAME, values, "Id = ?", new String[]{String.valueOf(bean.id)});
        db.close();
    }

    /**
     * 查
     * @param id
     * @return
     */
    public OrderBean query(int id){
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        //参数含义看源码描述
        Cursor cursor = db.query(DBHelper.TABLE_NAME,null,"Id = ?",new String[]
                {String.valueOf(id)},null,null,null);
        if (cursor.getCount() > 0){
            if (cursor.moveToFirst()) {
                OrderBean order = new OrderBean();
                order.id = (cursor.getInt(cursor.getColumnIndex("Id")));
                order.customName = (cursor.getString(cursor.getColumnIndex("CustomName")));
                order.orderPrice = (cursor.getInt(cursor.getColumnIndex("OrderPrice")));
                order.country = (cursor.getString(cursor.getColumnIndex("Country")));
                return order;
            }
        }
        cursor.close();
        db.close();
        return null;
    }



}
