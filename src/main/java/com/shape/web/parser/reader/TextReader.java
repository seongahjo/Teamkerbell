package com.shape.web.parser.reader;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
public class TextReader implements ParseReader {
    @Override
    public String read(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String s;
            while ((s = in.readLine()) != null) {
                sb.append("\n");
                sb.append(s);
            }
            in.close();
        } catch (IOException e) {
            log.error("File Open Error ", e);
            throw e;
        }
        return sb.toString();
    }
}
