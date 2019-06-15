package com.sz.dzh.dandroidsummary.model.summary.storageSummary;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.socks.library.KLog;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by administrator on 2018/8/16.
 * Android的存储方式总结
 * 常用的5种存储方式
 * 1.SharedPreference
 * 具体可看SPUtils,知道基本用法即可。
 * 2.文件存储(内置存储/sd卡存储)
 * 具体可看 FileUtils，涉及sd卡和File，以及io流知识
 * 3.sqlite
 * 数据库创建，删除，数据插入删除查询，SqliteOpenHelper 和 SqliteDataBase 配合使用，明白如何做数据迁移。
 * 4.contentProvider
 * 1）在整个Android系统中扮演了数据管理的角色，负责整个Android系统中App数据的访问和各App之间的数据共享。
 * 2）内置应用对外共享数据，比如通讯录，日历，短信等，一般第三方app不对外共享数据。
 * 5.网络存储
 * 网络交互，http知识，此处不做详解
 */
public class StorageActivity extends BaseActivity {

    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_storage);
        ButterKnife.bind(this);
        getLinkMan();
    }

    /**
     * 获取联系人列表
     * 1.得到ContentResolver对象，并query "contacts"表，得到cursor对象，可以在此对象找到联系人id和name
     * 2.利用id在"number"表查询对应的手机号码。
     * 具体需要知道ContentResolver，cursor的用法，以及相关的uri。
     */
    private void getLinkMan() {
        StringBuilder mStringBuilder = new StringBuilder();
        // 得到ContentResolver对象
        ContentResolver cr = getContentResolver();
        // 取得电话本中开始一项的光标,主要就是查询"contacts"表
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if(cursor==null) return;
        while (cursor.moveToNext()) {
            //取得联系人ID
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //联系人姓名
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            KLog.d(TAG, contactId + " " + name);
            // 根据联系人ID查询对应的电话号码
            Cursor cursorData = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
                    + contactId, null, null);
            // 取得电话号码(可能存在多个号码)
            if(cursorData==null) return;
            while (cursorData.moveToNext()) {
                String number = cursorData.getString(cursorData.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                KLog.d(TAG, "contactId:" + contactId + "   name:" + name + "  number:" + number );
                mStringBuilder.append("contactId:").append(contactId)
                        .append("   name:").append(name)
                        .append("   number:").append(number)
                        .append("\n");

            }
            cursorData.close();
        }
        cursor.close();
        mTvDesc.setText(mStringBuilder.toString());
    }
}
