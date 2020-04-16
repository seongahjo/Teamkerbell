package com.sajo.teamkerbell.service.impl;

import com.sajo.teamkerbell.entity.FileDB;
import com.sajo.teamkerbell.entity.User;
import com.sajo.teamkerbell.service.FileDBService;
import com.sajo.teamkerbell.service.RegisterServiceFacade;
import com.sajo.teamkerbell.service.UserService;
import com.sajo.teamkerbell.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterServiceFacadeImpl implements RegisterServiceFacade {
    private final UserService userService;
    private final FileDBService fileDBService;


    @Override
    @Transactional
    public User registerUser(MultipartFile file, UserVO userVo) {
        User user = User.from(userVo);
        FileDB fileDB = fileDBService.upload(file, "img");
        fileDB.assignTo(user);
        fileDBService.save(fileDB);
        return userService.save(user);
    }
}
