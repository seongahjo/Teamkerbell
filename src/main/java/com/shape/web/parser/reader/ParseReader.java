package com.shape.web.parser.reader;

import java.io.IOException;
import java.io.InputStream;

public interface ParseReader {
    String read(InputStream is) throws IOException;
}
