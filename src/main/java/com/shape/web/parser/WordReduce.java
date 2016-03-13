package com.shape.web.parser;
import java.io.File;
import java.util.ArrayList;

public class WordReduce {
    ArrayList<String> frontlist = new ArrayList<String>(); // 앞조사
    ArrayList<String> endfirstlist = new ArrayList<String>(); //뒷앞조사
    /*
    ex) 있,했
     */
    ArrayList<String> endsecondlist = new ArrayList<String>();
    //ex) 고, 을, 를
    ArrayList<String> endthirdlist = new ArrayList<String>();
    /*
    기호
     */
    ArrayList<String> notlist = new ArrayList<String>(); //예외

    void defaultList() {
        //
        frontlist.add("\"");
        //동사 앞쪽 형태소
        endfirstlist.add("있");
        endfirstlist.add("했");
        //
        endsecondlist.add("인");
        endsecondlist.add("가");
        endsecondlist.add("의");
        endsecondlist.add("와");
        endsecondlist.add("를");
        endsecondlist.add("로");
        endsecondlist.add("이");
        endsecondlist.add("다");
        endsecondlist.add("고");
        endsecondlist.add("는");
        endsecondlist.add("을");
        endsecondlist.add("및");
        endsecondlist.add("고");
        endsecondlist.add("하고");
        endsecondlist.add(",");
        endsecondlist.add("?");
        endsecondlist.add("에서");
        endsecondlist.add("수");
        //기호
        endthirdlist.add(".");
        endthirdlist.add(":");
        endthirdlist.add("-");
        endthirdlist.add("!");
        endthirdlist.add("\"");
        //
        notlist.add("온라인");
    }

    public WordReduce() {
        defaultList();
    }

    public WordReduce(File file) {
        defaultList();
    }

    public String simplify(String content) {
        for (String temp : notlist) {
            if (content.equals(temp))
                return content;
        }
        for (String temp : frontlist) {
            if (content.startsWith((temp))) {
                content = content.substring(temp.length(), content.length());
            }
        }
        for (String temp : endthirdlist) {
            if (content.endsWith((temp))) {
                content = content.substring(0, content.length() - temp.length());
            }
        }
        for (String temp : endsecondlist) {
            if (content.endsWith((temp))) {
                content = content.substring(0, content.length() - temp.length());
            }
        }
        for (String temp : endfirstlist) {
            if (content.endsWith((temp))) {
                content = content.substring(0, content.length() - temp.length());
            }
        }

        if (content.equals(""))
            return null;
        return content;
    }

}