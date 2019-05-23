package com.sz.dzh.dandroidsummary.model.viewDetails.dialog;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.socks.library.KLog;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;
import com.sz.dzh.dandroidsummary.base.BaseListShowActivity;
import com.sz.dzh.dandroidsummary.model.viewDetails.dialog.dialogFragment.CustomDialogFragment;
import com.sz.dzh.dandroidsummary.model.viewDetails.dialog.dialogFragment.SelectPhotoDialogFragment;
import com.sz.dzh.dandroidsummary.model.viewDetails.dialog.dialogFragment.SelectPhotoDialogFragment2;
import com.sz.dzh.dandroidsummary.model.viewDetails.dialog.dialogFragment.TestDialogFragment;
import com.sz.dzh.dandroidsummary.model.viewDetails.dialog.dialogFragment.TestDialogFragment2;
import com.sz.dzh.dandroidsummary.utils.ToastUtils;
import com.sz.dzh.dandroidsummary.widget.dialog.IDialogSelect;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengzh on 2018/4/19.
 * 演示 DialogFragment
 */

public class DialogFrActivity extends BaseListShowActivity implements TestDialogFragment2.DataCallback {

    private SelectPhotoDialogFragment photoDialogFragment;
    private CustomDialogFragment customDialogFr;
    private FragmentManager fragmentManager;

    @Override
    protected void initUI() {
        tvTitle.setText("DialogFragment示例");
        fragmentManager = getFragmentManager();
    }

    @Override
    protected void initData() {
        addClazzBean("DialogFragment简单示例",null);
        addClazzBean("DialogFragment简单示例2",null);
        addClazzBean("CustomDialog的Fr实现方式",null);
        addClazzBean("选择图片弹窗的Fr实现方式",null);
        addClazzBean("选择图片弹窗的Fr实现方式2",null);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        new TestDialogFragment().show(fragmentManager, "dialog_fragment");
                        break;
                    case 1:
                        new TestDialogFragment2().show(fragmentManager, "dialog_fragment2");
                        break;
                    case 2:
                        showCustomDialogFr();
                        break;
                    case 3:
                        showPhotoDialogFragment();
                        break;
                    case 4:
                        showPhotoDialogFragment2();
                        break;
                }
            }
        });
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getTestData(String data) {
        ToastUtils.showToast("DialogFragment2选择的结果:" + data);
    }


    /**
     * 显示CustomDialogFragment
     */
    private void showCustomDialogFr() {
        if (customDialogFr == null) {
            customDialogFr = CustomDialogFragment.newInstance("支付", "月收益值100元", "确认支付");
            customDialogFr.setDialogSelect(new IDialogSelect() {
                @Override
                public void onSelected(int i, Object o) {
                    switch (i) {
                        case -1:
                            ToastUtils.showToast(i + "");
                            break;
                        case 0:
                            ToastUtils.showToast(i + "");
                            break;
                        case 1:
                            ToastUtils.showToast( "确定");
                            break;
                    }
                }
            });
        }
        customDialogFr.show(fragmentManager, "CustomDialogFr");
    }

    /**
     * 显示SelectPhotoDialogFragment
     */
    private void showPhotoDialogFragment() {
        if (photoDialogFragment == null) {
            photoDialogFragment = SelectPhotoDialogFragment.newInstance(1);
            photoDialogFragment.setDialogSelect(new IDialogSelect() {
                @Override
                public void onSelected(int i, Object o) {
                    switch (i) {
                        case 1:  //拍照
                        case 2:  //从图库选择图片
                        case 3:  //剪切图
                            //目前返回的都是uri的路径
                            String imageUri = (String) o;
                            ToastUtils.showToast("图片Uri:" + imageUri);
                            /*if (!TextUtils.isEmpty(imageUri)) {
                                String path = UriToPathUtils.getImageAbsolutePath(DialogFrActivity.this, Uri.parse(imageUri));
                                Bitmap bitmap = BitmapCompressUtils.getCompressBitmap(path);
                                KLog.e("BitmapCompressUtils", "压缩后图片的大小" + (bitmap.getByteCount() / 1024 + "KB"));
                                if (bitmap != null) {
                                    GlideUtils.loadImg(DialogFrActivity.this, photoIv, imageUri);
                                }
                                bitmap.recycle();
                                bitmap = null;
                            }*/
                            break;
                    }
                }
            });
        }
        photoDialogFragment.show(fragmentManager, "photoDialogFragment");
    }


    private void showPhotoDialogFragment2() {
        SelectPhotoDialogFragment2 photoDialogFragment2 = SelectPhotoDialogFragment2.newInstance(1);
        photoDialogFragment2.setDialogSelect(new IDialogSelect() {
                @Override
                public void onSelected(int i, Object o) {
                    switch (i) {
                        case 1:  //拍照
                        case 2:  //从图库选择图片
                        case 3:  //剪切图
                            //目前返回的都是uri的路径
                            String imageUri = (String) o;
                            ToastUtils.showToast("图片Uri:" + imageUri);
                            break;
                    }
                }
            });
        photoDialogFragment2.show(fragmentManager, "photoDialogFragment");
    }
}
