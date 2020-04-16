package com.sajo.teamkerbell.service;

import com.sajo.teamkerbell.entity.FileDB;
import com.sajo.teamkerbell.repository.FileDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class FileDBService {
    private final FileDBRepository fileDBRepository;

    public FileDB upload(MultipartFile file, String filePath) {
        return FileDB.upload(file, filePath);
    }

    public FileDB render(String name, HttpServletResponse response) {
        FileDB fileDB = fileDBRepository.findByStoredName(name);
        fileDB.writeTo(response);
        return fileDB;
    }

    public FileDB download(String fileName, HttpServletRequest request, HttpServletResponse response) {
        FileDB fd = fileDBRepository.findByStoredName(fileName);
        fd.download(fileName, request, response);
        return fd;
    }

    public FileDB save(FileDB fileDB) {
        return fileDBRepository.save(fileDB);
    }
}
