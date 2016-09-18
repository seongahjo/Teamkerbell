package com.shape.web.serviceImpl;

import com.shape.web.entity.FileDB;
import com.shape.web.entity.Project;
import com.shape.web.repository.FileDBRepository;
import com.shape.web.service.FileDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by seongahjo on 2016. 7. 27..
 */
@Service
public class FileDBServiceImpl implements FileDBService {
    /*
        file:'fileidx'
        project:'projectidx':files
        (+)
        file:'stored'
        project:'projectidx':imgs
     */
    @Autowired
    FileDBRepository fileDBRepository;

    @Override
    @Cacheable(value = "file", key = "'file:'.concat(#p0)")
    public FileDB getFile(Integer f) {
        return fileDBRepository.findOne(f);
    }

    @Override
    @Cacheable(value = "files", key = "'project:'.concat(#p0).concat(':files')")
    public List getFilesList(Integer projectIdx) {
        return fileDBRepository.groupbytest(projectIdx);
    }


    @Override
    //@Cacheable(value = "files", key = "'project:'.concat(#p0.projectidx).concat(':filesrepo')")
    public List getFilesByOriginal(Project p, String o, Integer page, Integer count) {
        return fileDBRepository.findByProjectAndOriginalnameOrderByCreatedatDesc(p, o, new PageRequest(page, count));
    }


    @Override
    @Cacheable(value = "file", key = "'file:'.concat(#p0)")
    public FileDB getFileByStored(String s) {
        return fileDBRepository.findByStoredname(s);
    }

    @Override
    @Cacheable(value = "files", key = "'project:'.concat(#p0.projectidx).concat(':imgs')")
    public List getImgs(Project p) {
        return fileDBRepository.findByProjectAndTypeOrderByCreatedatDesc(p, "img");
    }
// @CacheEvict(value = "files", key = "'project:'.concat(#p0.project.projectidx).concat(':filesrepo')"),

     /* @Override
    @Cacheable(value = "files", key = "'project:'.concat(#p0.projectidx).concat(':files')")
    public List getFilesByProject(Project p) {
        return fileDBRepository.findByProjectOrderByCreatedatDesc(p);
    }*/

    @Override
    @Caching(evict = {
            @CacheEvict(value = "files", key = "'project:'.concat(#p0.project.projectidx).concat(':files')"),
            @CacheEvict(value = "file", key = "'file:'.concat(#p0.filedbidx)"),
            @CacheEvict(value = "file", key = "'file:'.concat(#p0.storedname)"),
            @CacheEvict(value = "files", key = "'project:'.concat(#p0.project.projectidx).concat(':imgs')", condition = "#p0.type=='img'")
    })
    public FileDB save(FileDB f) {
        return fileDBRepository.saveAndFlush(f);
    }
}
