package com.sz.dzh.dandroidsummary.utils.glideUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;


/**
 * Glide 圆形转换器
 * 继承BitmapTransformation ，只能对静态图进行图片变换。
 * 关键是要知道算法换算，顺便了解BitmapShader。
 */
public class GlideCircleTransform extends BitmapTransformation {
    public GlideCircleTransform(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        //举例：宽200、高100情况，size=100，x=50，y=0。也就是(50,0)到(150,100)范围的矩形。
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        //从缓存池取出一个原bitmap
        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }
        //创建一个要转换的bitmap
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

        float r = size / 2f;
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        //设置shader，使用位图平铺的渲染效果.
        //CLAMP表示，当所画图形的尺寸大于Bitmap的尺寸的时候，会用Bitmap四边的颜色填充剩余空间
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        canvas.drawCircle(r, r, r, paint);  //画一个圆
        return result;
    }

    /**
     * 要求返回一个唯一的字符串来作为id，以和其他的图片变换做区分。
     * 通常情况下，我们直接返回当前类的完整类名就可以了。
     * @return
     */
//    @Override
//    public String getId() {
//        return getClass().getName();
//    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }

}

