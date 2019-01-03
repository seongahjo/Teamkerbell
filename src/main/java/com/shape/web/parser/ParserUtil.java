package com.shape.web.parser;

import com.shape.web.parser.reader.*;
import com.shape.web.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;


@Slf4j
public class ParserUtil {

    private enum ParserType {
        TEXT(".txt", new TextReader()),
        DOC(".doc", new DocReader()),
        DOCX(".docx", new DocxReader()),
        EMPTY("", new EmptyReader());

        ParserType(String extension, ParseReader typeClass) {
            this.extension = extension;
            this.typeClass = typeClass;
        }

        public String getExtension() {
            return extension;
        }

        public ParseReader getTypeClass() {
            return typeClass;
        }

        public static ParseReader getReader(String ext) {
            return Arrays.stream(ParserType.values())
                    .filter(type -> type.getExtension().equals(ext))
                    .map(ParserType::getTypeClass)
                    .findAny()
                    .orElse(EMPTY.typeClass);
        }

        String extension;
        ParseReader typeClass;
    }


    public static String parse(File file) {
        try (FileInputStream fs = new FileInputStream(file)) {
            return ParserType.getReader(FileUtil.getFileExtension(file)).read(fs);
        } catch (Exception e) {
            log.error("document file cant be indexed", e);
        }
        return null;
    }
}