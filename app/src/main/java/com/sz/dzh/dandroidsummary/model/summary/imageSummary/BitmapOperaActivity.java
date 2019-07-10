package com.sz.dzh.dandroidsummary.model.summary.imageSummary;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_bitmap_opera);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("相册拍照、图片压缩保存");
    }

    @OnClick(R.id.btn_select)
    public void onViewClicked() {
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
                        KLog.e("BitmapUtils","imageUri--" + imageUri);
                        if (!TextUtils.isEmpty(imageUri)) {
                            String path = UriToPathUtils.getImageAbsolutePath(BitmapOperaActivity.this, Uri.parse(imageUri));
                            Bitmap bitmap = BitmapUtils.getCompressBitmap(path);
                            KLog.e("BitmapUtils", "最终压缩后图片的大小" + (bitmap.getByteCount() / 1024 + "KB"));
                            if (bitmap != null) {
                                mIvShow.setImageBitmap(bitmap);
                            }
                        }
                        break;
                }
            }
        });
        photoDialogFragment2.show(getFragmentManager(), "photoDialogFragment");
    }
}
