package com.sajo.teamkerbell.util;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class CommonUtils {

    private CommonUtils() {
    }

    public static String getRandomString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String encodeContent(final String content) {
        String ret = content;
        try {
            ret = ret.replaceAll("&", "&amp;");
            ret = ret.replace("<", "&lt;");
            ret = ret.replace(">", "&gt;");
        } catch (NullPointerException e) {
            log.error("Null Pointer", e);
        }
        return ret;
    }

    public static String decodeContent(final String content) {
        String ret = content;
        try {
            ret = ret.replaceAll("&amp;", "&");
            ret = ret.replaceAll("&lt;", "<");
            ret = ret.replaceAll("&gt;", ">");
        } catch (NullPointerException e) {
            log.error("Null Pointer", e);
        }
        return ret;
    }

    public static String dateFormat(final Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String dateTimeFormat(final Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        return format.format(date);
    }
}
