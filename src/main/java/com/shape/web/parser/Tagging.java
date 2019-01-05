package com.shape.web.parser;

import java.io.File;
import java.util.*;

public class Tagging {
    private Tagging() {
    }

    private static HashMap<String, Integer> push(String value) {
        StringTokenizer st = new StringTokenizer(value);
        HashMap<String, Integer> map = new HashMap<>();

        while (st.hasMoreTokens()) {
            String temp = st.nextToken();
            temp = WordReduce.simplify(temp);
            Optional<Integer> mappingValue = Optional.ofNullable(map.get(temp));
            if (temp != null)
                map.put(temp, mappingValue.orElse(0) + 1);
        }
        return map;
    } // end Push

    private static ArrayList<String> reduce(HashMap<String, Integer> data, int count) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> keyList = new ArrayList<>(data.keySet());

        for (int i = 0; i < count && !keyList.isEmpty(); i++) {
            int minimum = -1;
            String key = null;
            for (String s : keyList) {
                if (minimum < data.get(s)) {
                    minimum = data.get(s);
                    key = s;
                }
            }
            result.add(key);
            keyList.remove(key);
        } // endfor
        return result;
    } //end Reduce

    public static List<String> tag(File file) {
        String content = ParserUtil.parse(file);
        if (content != null)
            return reduce(push(content), 3);
        else
            return Collections.emptyList();
    }

    public static String tagbyString(File file) {
        List<String> list = tag(file);
        StringBuilder sb = new StringBuilder();

        if (list != null) {
            list.forEach(s -> {
                sb.append(s);
                sb.append(",");
            });
            if (sb.length() != 0)
                sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static List<String> tag(String content, int count) {
        return reduce(push(content), count);
    } //end Tag

    public static List<String> tag(String content) {
        return reduce(push(content), 3);
    } //end Tag

}