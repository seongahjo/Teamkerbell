package com.sajo.teamkerbell.controller;

import com.sajo.teamkerbell.service.FileDBService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by seongahjo on 2016. 1. 1..
 * Handles requests for accessing file.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileDBService fileDBService;

    /*
    To download file
    */
    @GetMapping(value = "/file")
    public void download(@RequestParam(value = "name") String name, HttpServletRequest request, HttpServletResponse response) {
        fileDBService.download(name, request, response);
    }


    /*
   To render uploaded Image
   */
    @GetMapping(value = "/render")
    public void render(@RequestParam(value = "name") String name, HttpServletResponse response) {
        fileDBService.render(name, response);
    }
}
