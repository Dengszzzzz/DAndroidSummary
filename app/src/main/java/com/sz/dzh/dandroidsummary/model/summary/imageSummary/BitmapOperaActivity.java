package com.sz.dzh.dandroidsummary.model.summary.imageSummary;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.socks.library.KLog;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;
import com.sz.dzh.dandroidsummary.model.viewDetails.dialog.dialogFragment.SelectPhotoDialogFragment2;
import com.sz.dzh.dandroidsummary.utils.ToastUtils;
import com.sz.dzh.dandroidsummary.utils.imageUtils.BitmapUtils;
import com.sz.dzh.dandroidsummary.utils.imageUtils.UriToPathUtils;
import com.sz.dzh.dandroidsummary.widget.dialog.IDialogSelect;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dengzh
 * on 2019/7/10 0010
 * Bitmap
 * 1.打开相册选择图片、拍照的代码实现。
 * 2.Bitmap 解析，压缩，保存。
 * 3.更新图库。
 */
public class BitmapOperaActivity extends BaseActivity {

    @BindView(R.id.iv_show)
    ImageView mIvShow;

    private Bitmap mBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_bitmap_opera);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("相册拍照、图片压缩保存");
    }

    @OnClick({R.id.btn_select, R.id.btn_save, R.id.btn_save2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_select:
                SelectPhotoDialogFragment2 photoDialogFragment2 = SelectPhotoDialogFragment2.newInstance(1);
                photoDialogFragment2.setDialogSelect(new IDialogSelect() {
                    @Override
                    public void onSelected(int i, Object o) {
                        String imageUri = (String) o;
                        KLog.e("BitmapUtils", "imageUri--" + imageUri);
                        switch (i) {
                            case 1:  //拍照
                                if (!TextUtils.isEmpty(imageUri)){
                                    String path = UriToPathUtils.getRealFilePath(BitmapOperaActivity.this, Uri.parse(imageUri));
                                    mBitmap = BitmapUtils.getCompressBitmap(path);
                                    if (mBitmap != null) {
                                        mIvShow.setImageBitmap(mBitmap);
                                    }
                                }
                                break;
                            case 2:  //从图库选择图片
                                break;
                            case 3:  //剪切图
                                //目前返回的都是uri的路径
                                if (!TextUtils.isEmpty(imageUri)) {
                                    String path = UriToPathUtils.getImageAbsolutePath(BitmapOperaActivity.this, Uri.parse(imageUri));
                                    mBitmap = BitmapUtils.getCompressBitmap(path);
                                    if (mBitmap != null) {
                                        mIvShow.setImageBitmap(mBitmap);
                                    }
                                }
                                break;
                        }
                    }
                });
                photoDialogFragment2.show(getFragmentManager(), "photoDialogFragment");
                break;
            case R.id.btn_save:
                //保存到FileDirs
                if(mBitmap!=null){
                   boolean isSuccess =  BitmapUtils.saveImageInFileDirs(BitmapOperaActivity.this,mBitmap,BitmapUtils.getFileName());
                   if(isSuccess){
                       ToastUtils.showToast("已保存到.../<application package>/files/image/ 下");
                   }else{
                       ToastUtils.showToast("保存失败");
                   }
                }
                break;
            case R.id.btn_save2:
                //保存到sd卡，且更新图库
                if(mBitmap!=null){
                    boolean isSuccess =  BitmapUtils.saveImageInSdCard(mBitmap, BitmapUtils.getFileName());
                    if(isSuccess){
                        ToastUtils.showToast("已保存到/storage/emulated/0/com.sz.dzh.dandroidsummary/DASImage/下，且更新了图库");
                    }else{
                        ToastUtils.showToast("保存失败");
                    }
                }
                break;
        }
    }
}
