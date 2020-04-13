package com.sajo.teamkerbell.parser.reader;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.IOException;
import java.io.InputStream;

public class DocReader implements ParseReader {
    @Override
    public String read(InputStream is) throws IOException {
        POIFSFileSystem pfs = new POIFSFileSystem(is);
        HWPFDocument doc = new HWPFDocument(pfs);
        WordExtractor we = new WordExtractor(doc);
        we.close();
        return we.getText();
    }
}
