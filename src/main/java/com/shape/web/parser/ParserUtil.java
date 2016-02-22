package com.shape.web.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;


public class ParserUtil {

    public static String Parse(File file){
        String fileName=file.getName();
        try {
            FileInputStream fs =new FileInputStream(file);
            if(fileName.endsWith(".txt")){
                BufferedReader in = new BufferedReader(new FileReader(file));
                String content=new String();
                String s;
                while ((s = in.readLine()) != null) {
                    content+="\n"+s;
                }
                in.close();
                return content;
            }
            /*else if(fileName.endsWith(".hwp")){

                H2TParser parser = new H2TParser();
                HWPMeta meta = new HWPMeta();
                StringBuilder sb = new StringBuilder();
                boolean flg= parser.GetText(file.getAbsolutePath(), meta, sb);

                fs.close();
                if(flg){
                    return sb.toString();
                }
            }*/

            else if(fileName.endsWith(".docx")){
                OPCPackage d = OPCPackage.open(fs);
                XWPFWordExtractor xw = new XWPFWordExtractor(d);
                xw.close();
                fs.close();
                return xw.getText();
            }
            else if(fileName.endsWith(".doc")) {
                POIFSFileSystem pfs = new POIFSFileSystem(fs);
                HWPFDocument doc = new HWPFDocument(pfs);
                WordExtractor we = new WordExtractor(doc);
                we.close();
                fs.close();
                return we.getText();
            }
        } catch (Exception e) {
            System.out.println("document file cant be indexed");
        }
        return null;
    }
}