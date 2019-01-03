package com.shape.web.service;

import com.shape.web.entity.FileDB;
import com.shape.web.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RegisterServiceFacade {
    void registerUser(MultipartFile file, String filePath, FileDB filedb, User user) throws IOException;
}
