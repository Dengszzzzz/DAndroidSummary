package com.sz.dzh.dandroidsummary.model.specialFunc.emoji_encoder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *  UTF-8编码有可能是两个、三个、四个字节。Emoji表情或者某些特殊字符是4个字节，而Mysql的utf8编码最多3个字节，
 *  所以数据插不进去。遇到这种问题可以交给后台处理，如果后台不处理，则ios和Andriod要统一对emoji表情编码。
 *  在解决这个问题之前，需要了解以下知识
 *  1.位、字节、字符的概念和区别。
 *  2.UTF-8编码，字节和字符的对应关系。
 *
 *  */
public class EmojiUtils {

    public static String escape(String src){
        //1.得到代码点数量，也即是实际字符数，注意和length()的区别
        //比如一个emoji表情是一个字符，codePointCount()是1，length()是2
        int cpCount = src.codePointCount(0, src.length());
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<cpCount;){
            //2.获得代码点，判断是否是emoji表情
            int codepoint = src.codePointAt(i);
            if (!isEmojiCharacter(codepoint)) {
                sb.append((char) codepoint);
            }else{
                try {
                    //3.对emoji表情UTF-8编码，+2是因为emoji是增补字符，length是2
                    String encoderStr = URLEncoder.encode(src.substring(i,i + 2), "UTF-8");
                    sb.append(encoderStr);
                } catch (UnsupportedEncodingException e) {

                }
            }
            //4.确定指定字符（Unicode代码点）是否在增补字符范围内。
            //因为除了表情，还有些特殊字符也是在增补字符方位内的。
            i += Character.isSupplementaryCodePoint(codepoint)? 2 : 1;
        }
        return sb.toString();
    }

    /**
     * 对emoji表情单独编码
     * @param src
     * @return
     */
    public static String escape2(String src) {

        //codePointCount()方法返回的是代码点个数，是实际上的字符个数。
        //举例：输入一个emoji表情，此时codePointCount=1，length=2。但是对我们来说，字符就是1个。
        int cpCount = src.codePointCount(0, src.length());
        int firCodeIndex = src.offsetByCodePoints(0, 0);
        int lstCodeIndex = src.offsetByCodePoints(0, cpCount - 1);

        StringBuilder sb = new StringBuilder(src.length());
        for (int index = firCodeIndex; index <= lstCodeIndex;) {
            //遍历每个codePoint
            int codepoint = src.codePointAt(index);
            if (!isEmojiCharacter(codepoint)) {
                sb.append((char) codepoint);
            }else{
                int length = (Character.isSupplementaryCodePoint(codepoint)) ? 2 : 1;
                String encoderStr = "";
                try {
                    encoderStr = URLEncoder.encode(src.substring(index,index + length), "UTF-8");
                } catch (UnsupportedEncodingException e) {

                }
                sb.append(encoderStr);
            }
            index += ((Character.isSupplementaryCodePoint(codepoint)) ? 2 : 1);
        }
        return sb.toString();
    }


    /**
     * 过滤掉emoji表情
     * @param src
     * @return
     */
    public static String filter(String src) {
        if (src == null) {
            return null;
        }
        //得到codePointCount
        int cpCount = src.codePointCount(0, src.length());
        int firCodeIndex = src.offsetByCodePoints(0, 0);
        int lstCodeIndex = src.offsetByCodePoints(0, cpCount - 1);
        StringBuilder sb = new StringBuilder(src.length());
        for (int index = firCodeIndex; index <= lstCodeIndex;) {
            //遍历每个codePoint
            int codepoint = src.codePointAt(index);
            if (!isEmojiCharacter(codepoint)) {
                System.err.println("codepoint:" + Integer.toHexString(codepoint));
                sb.append((char) codepoint);
            }
            index += ((Character.isSupplementaryCodePoint(codepoint)) ? 2 : 1);

        }
        return sb.toString();
    }


    /**
     * 是否是Emoji表情
     * @param codePoint
     * @return
     */
    private static boolean isEmojiCharacter(int codePoint) {
        return (codePoint >= 0x2600 && codePoint <= 0x27BF) // 杂项符号与符号字体
                || codePoint == 0x303D || codePoint == 0x2049 || codePoint == 0x203C
                || (codePoint >= 0x2000 && codePoint <= 0x200F)//
                || (codePoint >= 0x2028 && codePoint <= 0x202F)//
                || codePoint == 0x205F //
                || (codePoint >= 0x2065 && codePoint <= 0x206F)//
                /* 标点符号占用区域 */
                || (codePoint >= 0x2100 && codePoint <= 0x214F)// 字母符号
                || (codePoint >= 0x2300 && codePoint <= 0x23FF)// 各种技术符号
                || (codePoint >= 0x2B00 && codePoint <= 0x2BFF)// 箭头A
                || (codePoint >= 0x2900 && codePoint <= 0x297F)// 箭头B
                || (codePoint >= 0x3200 && codePoint <= 0x32FF)// 中文符号
                || (codePoint >= 0xD800 && codePoint <= 0xDFFF)// 高低位替代符保留区域
                || (codePoint >= 0xE000 && codePoint <= 0xF8FF)// 私有保留区域
                || (codePoint >= 0xFE00 && codePoint <= 0xFE0F)// 变异选择器
                || codePoint >= 0x10000; // Plane在第二平面以上的，char都不可以存，全部都转
    }
}
