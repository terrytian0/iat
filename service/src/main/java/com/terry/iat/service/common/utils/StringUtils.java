package com.terry.iat.service.common.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    public static Set<String> getMatcher(String regex, String source) {
        Set<String> set=new HashSet<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            set.add(matcher.group());
        }
        return set;
    }

    public static Set<String> getParameters(String source){
        if(source==null){
            return Collections.EMPTY_SET;
        }
        Set<String> res=new HashSet<>();
        String regex = "#\\{[\\w]*\\}";
        Set<String> params = getMatcher(regex,source);
        for (String param : params) {
            res.add(param.substring(2,param.length()-1));
        }
        return res;
    }
    public static void main(String[] args) {
        String a = "{\"defaultValue\":\"string\",\"body\":\"#{body}\",\"type\":\"string\",\"apiId\":#{test}}";
        System.out.println(getParameters(a));
    }
}
