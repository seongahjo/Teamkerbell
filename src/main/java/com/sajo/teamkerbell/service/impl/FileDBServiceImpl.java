package com.sajo.teamkerbell.service.impl;

import com.sajo.teamkerbell.entity.FileDB;
import com.sajo.teamkerbell.repository.FileDBRepository;
import com.sajo.teamkerbell.service.FileDBService;
import com.sajo.teamkerbell.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class FileDBServiceImpl implements FileDBService {
    private final FileDBRepository fileDBRepository;

    @Override
    public FileDB upload(MultipartFile file, String filePath) {
        String originalFileName = file.getOriginalFilename(); // 파일 이름
        String originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf('.')); // 파일 확장자
        String storedFileName = CommonUtils.getRandomString() + originalFileExtension; //암호화된 고유한 파일 이름
        FileDB fileDB = new FileDB(storedFileName, originalFileName, FileDB.FileType.IMAGE, filePath, null);
        fileDB.upload(file, filePath);
        return fileDB;
    }

    @Override
    public FileDB save(FileDB f) {
        return null;
    }
}
