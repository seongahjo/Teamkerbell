package com.shape.web.util;

import java.util.UUID;

public class CommonUtils {
    public static String getRandomString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    public static String encodeContent(String content) {
        String ret = content;
        try {
            ret = ret.replaceAll("&", "&amp;");
            ret = ret.replace("<", "&lt;");
            ret = ret.replace(">", "&gt;");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String decodeContent(String content) {
        String ret = content;
        try {
            ret = ret.replaceAll( "&amp;", "&");
            ret = ret.replaceAll( "&lt;", "<");
            ret = ret.replaceAll("&gt;", ">");
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        return ret;
    }
}
