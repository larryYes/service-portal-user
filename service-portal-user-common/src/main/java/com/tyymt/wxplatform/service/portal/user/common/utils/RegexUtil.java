package com.tyymt.wxplatform.service.portal.user.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huangfei
 * @create 2020-12-04
 */
public class RegexUtil {

    /**
     * 验证是否是URL
     * @param url
     * @return
     */
    public static boolean verifyUrl(String url){

        // URL验证规则
        String regEx ="[a-zA-z]+://[^\\s]*";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();
        return rs;

    }

}
