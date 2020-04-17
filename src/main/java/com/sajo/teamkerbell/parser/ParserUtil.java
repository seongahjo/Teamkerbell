package com.sajo.teamkerbell.parser;

import com.sajo.teamkerbell.parser.reader.*;
import com.sajo.teamkerbell.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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


    private ParserUtil() {
    }

    public static String parse(File file) {
        try (FileInputStream fs = new FileInputStream(file)) {
            return ParserType.getReader(FileUtil.getFileExtension(file)).read(fs);
        } catch (FileNotFoundException e) {
            log.error("file not exsits {} ", file.getName(), e);
        } catch (IOException e) {
            log.error("IOException ", e);
        }
        return "";
    }
}