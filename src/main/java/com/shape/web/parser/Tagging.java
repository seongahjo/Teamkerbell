package com.shape.web.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

public class Tagging {
    //MAP REDUCE 구현
    private static HashMap<String,Integer> Push(String value){
        StringTokenizer st=new StringTokenizer(value);
        String temp;
        int count;
        WordReduce reduce=new WordReduce();
        HashMap<String,Integer> map=new HashMap<String,Integer>();
        while(st.hasMoreTokens()){
            temp=st.nextToken();
            temp=reduce.simplify(temp);
            if(temp!=null) {
                if (map.get(temp) == null)
                    map.put(temp, 1);
                else {
                    count = map.get(temp);
                    map.remove(temp);
                    map.put(temp, count + 1);
                }
            }
        }
        return map;
    } // end Push

    private static ArrayList<String> Reduce(HashMap<String,Integer> data,int count){
        ArrayList<String> result=new ArrayList<String>();
        Vector<String> key_list=new Vector<String>();
        for(String temp:data.keySet())
            key_list.add(temp);
        int minimum;
        String key;
        for(int i=0; i<count && key_list.size()!=0; i++){
            minimum=-1;
            key=null;
            for(String s : key_list){
                if(minimum<data.get(s)){
                    minimum=data.get(s);
                    key=s;
                }
            }
            result.add(key);
            key_list.remove(key);
        } // endfor
        return result;
    } //end Reduce
    public static ArrayList<String> Tag(File file){
       String content= ParserUtil.Parse(file);
        if(content!=null)
        return Reduce(Push(content),3);
        else
            return null;
    }
    public static ArrayList<String> Tag(String content, int count){
        return Reduce(Push(content),count);
    } //end Tag

    public static ArrayList<String> Tag(String content){
        return Reduce(Push(content),3);
    } //end Tag

}