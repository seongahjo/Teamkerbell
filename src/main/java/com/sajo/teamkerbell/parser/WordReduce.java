package com.sajo.teamkerbell.parser;

import java.util.*;

class WordReduce {

    private static class Word implements Comparable<Word> {
        private int priority;
        private ArrayList<String> list;

        Word(int priority, List<String> list) {
            this.priority = priority;
            this.list = new ArrayList<>(list);
        }


        public List<String> getList() {
            return list;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Word word = (Word) o;
            return priority == word.priority &&
                    Objects.equals(list, word.list);
        }

        @Override
        public int hashCode() {
            return Objects.hash(priority, list);
        }

        @Override
        public int compareTo(Word o) {
            return Integer.compare(o.priority, priority);
        }
    }


    private static ArrayList<Word> endList = new ArrayList<>();
    private static ArrayList<String> startList = new ArrayList<>();
    private static ArrayList<String> exceptList = new ArrayList<>(); //예외

    static {
        startList.add("\"");
        endList.add(new Word(1, Arrays.asList("있", "했")));
        endList.add(new Word(2, Arrays.asList("인", "가", "의", "와", "를", "로", "이", "다",
                "고", "는", "을", "및", "하고", "고", ",", "?", "에서", "수")));
        endList.add(new Word(3, Arrays.asList(".", ":", "-", "!", "\"")));
        exceptList.add("온라인");
        Collections.sort(endList);
    }

    private WordReduce() {
        /*
         */
    }


    static String simplify(String content) {
        if (exceptList.stream().anyMatch(content::equals))
            return content;
        if (startList.stream().anyMatch(content::startsWith))
            content = content.substring(1);
        // 구조에 대해 생각해봐야할듯...
        if (content.equals(""))
            return "";
        return content;
    }

}