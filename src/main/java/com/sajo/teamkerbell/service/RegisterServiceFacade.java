package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.User;
import com.sajo.teamkerbell.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

public interface RegisterServiceFacade {
    User registerUser(MultipartFile file, UserVO userVo);
}
