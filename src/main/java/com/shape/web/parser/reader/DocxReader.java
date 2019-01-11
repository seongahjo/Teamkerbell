package com.shape.web.parser.reader;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class DocxReader implements ParseReader {
    @Override
    public String read(InputStream is) throws IOException {
        try (OPCPackage d = OPCPackage.open(is)) {
            XWPFWordExtractor xw = new XWPFWordExtractor(d);
            xw.close();
            return xw.getText();
        } catch (InvalidFormatException e) {
            log.error("Invalid Format ", e);
        } catch (XmlException e) {
            log.error("Xml Error ", e);
        } catch (OpenXML4JException e) {
            log.error("Open Xml Error ", e);
        }
        return "";
    }
}
