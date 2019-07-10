package com.sz.dzh.dandroidsummary.model.summary.imageSummary;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;
import com.sz.dzh.dandroidsummary.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 图片加载，三级缓存
 * 1.图片加载，最终调用BitmapFactory的方法生成Bitmap。
 * 2.三级缓存： 内存 -> 文件（本地）->网络
 * 3.LruCache详解
 */
public class ImageLoadActivity extends BaseActivity {

    @BindView(R.id.iv_show)
    ImageView mIvShow;
    @BindView(R.id.et_url)
    EditText mEtUrl;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_image_load);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("图片加载及三级缓存");
    }

    private void test() {
        String mFilePath = "";
        //加载资源
        BitmapFactory.decodeResource(getResources(), R.mipmap.follow_anim0);
        //加载本地图片
        BitmapFactory.decodeFile(mFilePath);
    }

    @OnClick(R.id.btn_load)
    public void onViewClicked() {
        String url = mEtUrl.getText().toString().trim();
        if(TextUtils.isEmpty(url)){
            ToastUtils.showToast("请输入图片链接");
        }
        MyImageLoad.with(this).load(mIvShow,url);
    }


    /**
     *  LruCache详解
     *  LruCache其实是一个Hash表，内部使用的是LinkedHashMap存储数据，使用了最近最少算法。
     *
     *  1.由LruCache延伸HashMap
     *  1）默认构造函数：默认 初始容量 (16) 和 默认 负载因子(0.75) 的空 HashMap
     *  2) 容量是指table数组的容量，并不是说map.put("x",x) 多少次的意思
     *     ，而是代表桶(bucketIndex),通过key.hashCode()快速找到某个桶。
     *  3) 链表其实是一个Entry<K,V>链表，也就是一个桶可以多个Entry<K,V>的。
     *  4) put的大致流程
     *     1.根据key的hashCode计算hash值
     *     int hash = hash(key.hashCode());
     *     2.计算该键值对在数组中的存储位置（哪个桶）
     *     int i = indexFor(hash, table.length);
     *     3.在table的第i个桶上进行迭代，寻找 key 保存的位置，还要判断是否有旧值，新值替换旧值。
     *  5) get的大致流程
     *     1.根据该 key 的 hashCode 值计算它的 hash 码
     *     2.找出 table 数组中对应的桶
     *     3.若搜索的key与查找的key相同，则返回相对应的value
     *  6) HashMap 的底层数组长度总是2的n次方的原因为了  减小hash值发生碰撞的概率，优化速度和效率。
     * */

    /**
     * 4.由LruCache延伸LinkedHashMap
     *   1）LinkedHashMap 双向链表，在HashMap基础上拓展。
     *      Entry的结构有  before、hash、key、value、next、after
     *      before/after 维护整个双向链表，next维护每个桶的单链表
     *   2）LinkedHashMap区别于HashMap最大的一个不同点是，前者是有序的，而后者是无序的。
     *      为此，LinkedHashMap增加了两个属性用于保证顺序，分别是双向链表头结点header和标志位accessOrder。
     *   3）LRU（最近最少使用） 算法实现
     *      当accessOrder为true时，get方法和put方法都会调用recordAccess方法使得最近使用的Entry移到双向链表的末尾；
     *      当accessOrder为默认值false时，从源码中可以看出recordAccess方法什么也不会做。
     *   4）LinkedHashMap重写了HashMap 的迭代器，它使用其维护的双向链表进行迭代输出
     * */
}
