package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.FileDB;
import com.sajo.teamkerbell.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RegisterServiceFacade {
    void registerUser(MultipartFile file, String filePath, FileDB filedb, User user) throws IOException;
}
