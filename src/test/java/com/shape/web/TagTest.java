package com.shape.web;

import com.shape.web.parser.ParserUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

@Slf4j
public class TagTest {


    private String fileName = "test.txt";
    private String fileName2 = "test2.docx";
    private File f;

    @Before
    public void getFile() {
        f = new File(fileName);
    }

    @Test
    public void tagtest() {
        log.info("File Name : {} Content : {} ", fileName, ParserUtil.parse(f));
    }

    @Test
    public void readTest2() {
        log.info("File Name : {} Content : {} ", fileName2, ParserUtil.parse(new File(fileName2)));
    }

    @Test
    public void notReadTest(){
        log.info(ParserUtil.parse(new File("testtest.txt")));
    }

}
