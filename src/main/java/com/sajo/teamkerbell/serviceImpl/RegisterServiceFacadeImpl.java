package com.sajo.teamkerbell.serviceImpl;

import com.sajo.teamkerbell.service.FileDBService;
import com.sajo.teamkerbell.service.RegisterServiceFacade;
import com.sajo.teamkerbell.entity.FileDB;
import com.sajo.teamkerbell.entity.User;
import com.sajo.teamkerbell.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class RegisterServiceFacadeImpl implements RegisterServiceFacade {
    private UserService userService;
    private FileDBService fileDBService;

    @Autowired
    public RegisterServiceFacadeImpl(UserService userService, FileDBService fileDBService) {
        this.userService = userService;
        this.fileDBService = fileDBService;
    }

    @Override
    @Transactional
    public void registerUser(MultipartFile file, String filePath, FileDB filedb, User user) throws IOException {
        try {
            File folder = new File(filePath); // 폴더
            if (!folder.exists() && folder.mkdirs()) // 폴더 존재하지 않을 경우 만듬
                log.info("Folder created {}", folder.getPath());
            File transFile = new File(filePath + File.pathSeparator + filedb.getStoredname()); // 전송된 파일
            log.info("FILE NAME = " + file.getOriginalFilename());
            file.transferTo(transFile);
            filedb.setUser(user);
            user.setImg("loadImg?name=" + filedb.getStoredname());
            userService.save(user);
            fileDBService.save(filedb); // 파일 내용을 디비에 저장
        } catch (IOException e) {
            log.error("File Move Error", e);
            throw e;
        }
    }
}
