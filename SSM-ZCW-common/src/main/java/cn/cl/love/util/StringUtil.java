package cn.cl.love.util;

/**
 * 标题：判断字符串是否为空
 * 作者：何处是归程
 * 时间：2020/1/28 - 19:11
 */
public class StringUtil {

    public static boolean isEmpty(String s) {
        return s == null || "".equals(s);  //     s == null | s.equals("");  //位与,逻辑与区别,非空字符串放置在前面,避免空指针
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }
}
