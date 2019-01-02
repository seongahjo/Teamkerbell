package com.shape.web.controller;

import com.shape.web.entity.FileDB;
import com.shape.web.entity.User;
import com.shape.web.service.FileDBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Handles requests for the whole application processing.
 */
@Slf4j
@RestController
public class ProcessController {


    private FileDBService fileDBService;

    @Autowired
    public ProcessController(FileDBService fileDBService) {
        this.fileDBService = fileDBService;
    }

    /*
           To load uploaded Image
           */
    @GetMapping(value = "/loadImg")
    public void loadImg(@RequestParam(value = "name") String name, HttpServletResponse response) {

        FileDB file = fileDBService.getFileByStored(name);
        try (BufferedInputStream in = new BufferedInputStream(
                new FileInputStream(file.getPath() + "/" + file.getStoredname()))) {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(512);
            int imageByte;
            while ((imageByte = in.read()) != -1)
                byteStream.write(imageByte);
            response.setContentType("image/*");
            byteStream.writeTo(response.getOutputStream());
            log.info("SUCCESS LOAD IMG");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @GetMapping(value = "/sessionCheck")
    public ResponseEntity sessionCheck(HttpSession session) {
        User u = (User) session.getAttribute("user");
        return new ResponseEntity<>(u, HttpStatus.OK);
    }


}