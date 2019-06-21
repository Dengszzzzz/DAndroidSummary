package com.sz.dzh.dandroidsummary.model.specialFunc.emoji_encoder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 如果后台数据库是utf-8，那么emoji表情无法直接插入数据库，是因为utf-8是支持3字节的，而emoji表情是4字节，此外还有某些特殊字符也是4个字节的。
 * 可以交给后台处理，如果后台不处理，则ios和Andriod要统一对emoji表情编码。下面举例UTF-8编码。
 *
 */
public class EmojiUtils {

    /**
     * 对emoji表情单独编码
     * @param src
     * @return
     */
    public static String escape(String src) {

        //codePointCount()方法返回的是代码点个数，是实际上的字符个数。
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
