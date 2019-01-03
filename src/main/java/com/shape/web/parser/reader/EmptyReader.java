package com.shape.web.parser.reader;

import java.io.InputStream;

public class EmptyReader implements ParseReader {
    @Override
    public String read(InputStream is) {
        return null;
    }
}
