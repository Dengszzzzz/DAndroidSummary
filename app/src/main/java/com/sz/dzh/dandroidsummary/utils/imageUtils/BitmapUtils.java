package com.sz.dzh.dandroidsummary.utils.imageUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.socks.library.KLog;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by Dengzh
 * on 2019/7/10 0010
 * 图片压缩、图片保存、通知图库更新
 */
public class BitmapUtils {

    private static String TAG = "BitmapUtils";

    /**
     * Bitmap及其相关类解释
     * 1.Bitmap：
     * Bitmap 在 Android 中指的是一张图片，可以是 png，也可以是 jpg等其他图片格式。
     * 作用是可以获取图像文件信息，对图像进行剪切、旋转、缩放、压缩等操作，并可以指定格式保存图像文件。
     *
     * 2.Bitmap.Config
     * Bitmap 编码格式，Android默认ARGB_8888（每像素占4byte），压缩一般使用RGB_565（每像素占2byte）。
     *
     * 3.BitmapFactory
     * 提供解析Bitmap的静态工厂方法。
     *
     * 4.BitmapFactory.Options
     * 用于解码Bitmap时的各种参数控制，下面介绍压缩中用到的那些
     * inPreferredConfig：编码格式，默认值为Bitmap.Config.ARGB_888；
     * inJustDecodeBounds：为true时仅返回 Bitmap 宽高等属性，返回bmp=null，为false时才返回占内存的 bmp；
     * outputWidth：返回的 Bitmap的宽；
     * outputHeight：返回的 Bitmap的高；
     * inSampleSize：表示 Bitmap 的压缩比例，值必须 > 1 & 是2的幂次方。为2是指压缩宽高各1/2；
     *
     * */

    /**
     * 常用压缩方法
     * 1.质量压缩
     * 质量压缩不会减少图片的像素，它是在保持像素的前提下改变图片的位深及透明度，来达到压缩图片的目的，图片的长，
     * 宽，像素都不会改变，那么bitmap所占内存大小是不会变的。
     * 注意：质量压缩对png格式图片没效，因为png是无损压缩。
     *
     * 2.宽高压缩
     * 一般用的是调节inSampleSize。
     * 1）采样率压缩，通过调节inSampleSize，缩放Bitmap的尺寸
     * 2）缩放法压缩（Matrix），通过矩阵对图片进行缩放。
     * 3）Bitmap.createScaledBitmap。
     *
     * 3.RGB_565压缩
     * 改用内存占用更小的编码格式来达到压缩的效果。Android默认的颜色模式为ARGB_8888，如果对透明度没有要求，可以把
     * 编码格式改为RGB_565，相比ARGB_8888将节省一半的内存开销。
     *
     * 注意点：
     * 1、质量压缩，不改变bitmap所占内存大小。
     * 2、图片的所占的内存大小和很多因素相关，bitmap.getByteCount()得到的内存大小不一定准确。
     *
     * */


    /**
     * 压缩图片
     * 压缩要求，宽480，高640，文件大小不超过1M。
     * */
    public static Bitmap getCompressBitmap(String path){
        Bitmap bitmap = getResizeBitmap(path,480,640) ;
        return getQualityBitmap(bitmap,1024);
    }

    /**
     * 宽高压缩
     * @param filePath  文件路径
     * @param width     目标宽度
     * @param height    目标高度
     * @return
     */
    public static Bitmap getResizeBitmap(String filePath, int width, int height) {
        Bitmap bitmap = null;
        File f = new File(filePath);
        if (f.exists() && f.length() > 0) {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                //RGB_565压缩
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                //只取宽高
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(filePath, options);
                int picWidth = options.outWidth;
                int picHeight = options.outHeight;
                KLog.e(TAG, "宽高压缩前图片宽度-"+ picWidth + "，高度-" + picHeight);
                //图片宽高大于所需宽高才压缩
                if(picWidth > width || picHeight > height){
                    options.inSampleSize = Math.max(options.outWidth / width, options.outHeight / height);
                }else{
                    options.inSampleSize = 1;
                }
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeFile(filePath, options);
                KLog.e(TAG, "宽高压缩后图片宽度-"+ bitmap.getWidth() + "，高度-" + bitmap.getHeight()
                        + ",所占内存大小-" + bitmap.getByteCount());
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 质量压缩
     * 这个方法只会改变图片的存储大小,不会改变bitmap的大小
     * @param bitmap  bitmap
     * @param maxFileSize 最大大小
     * @return Bitmap 压缩后bitmap
     */
    public static Bitmap getQualityBitmap(Bitmap bitmap, int maxFileSize) {
        if(bitmap == null){
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG,quality,baos);
        int baosLength = baos.toByteArray().length;
        KLog.e(TAG, "质量压缩前图片的质量  " + (bitmap.getByteCount() / 1024 +"KB")
                + "bytes.length=" + (baosLength/ 1024) + "KB"
                + "  quality=" + quality);
        while (baosLength/1024 > maxFileSize){
            //循环判断如果压缩后图片是否大于maxMemmorrySize,大于继续压缩
            baos.reset(); //清空baos
            quality = quality <= 10 ? quality - 1 : quality - 10;
            if (quality == 0) {
                break;
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,baos);
            //将压缩后的图片保存到baos中
            baosLength = baos.toByteArray().length;
        }
        KLog.e(TAG, "质量压缩后图片的质量 " + (bitmap.getByteCount() / 1024 +"KB")
                + "bytes.length=" + (baosLength/ 1024) + "KB"
                + "  quality=" + quality);
        bitmap.recycle();
        bitmap = null;
        return BitmapFactory.decodeByteArray(baos.toByteArray(),0,baosLength);
    }
}
