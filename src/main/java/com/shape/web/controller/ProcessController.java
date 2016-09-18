package com.shape.web.controller;

import com.shape.web.entity.FileDB;
import com.shape.web.service.FileDBService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Handles requests for the whole application processing.
 */
@Log
@RestController
public class ProcessController {

   
    @Autowired
    FileDBService fileDBService;

    /*
       To load uploaded Image
       */
    @RequestMapping(value = "/loadImg", method = RequestMethod.GET)
    public void loadImg(@RequestParam(value = "name") String name, HttpServletResponse response) {
        try {
            FileDB file = fileDBService.getFileByStored(name);
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file.getPath() + "/" + file.getStoredname()));
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(512);
            int imageByte;
            while ((imageByte = in.read()) != -1)
                byteStream.write(imageByte);
            in.close();
            response.setContentType("image/*");
            byteStream.writeTo(response.getOutputStream());
            log.info("SUCCESS LOAD IMG");
        } catch (IOException ioe) {
            // InputStream Error
        } catch (NullPointerException e) {
            // file 존재 안할시
        }
    }

    @RequestMapping(value = "/sessionCheck", method = RequestMethod.GET)
    public ResponseEntity sessionCheck() {
        return new ResponseEntity(HttpStatus.OK);
    }

}