package com.sajo.teamkerbell.serviceImpl;

import com.sajo.teamkerbell.entity.FileDB;
import com.sajo.teamkerbell.repository.FileDBRepository;
import com.sajo.teamkerbell.service.FileDBService;
import com.sajo.teamkerbell.entity.Project;
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
    private FileDBRepository fileDBRepository;


    @Autowired
    public FileDBServiceImpl(FileDBRepository fileDBRepository) {
        this.fileDBRepository = fileDBRepository;
    }


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
    public List<FileDB> getFilesByOriginal(Project p, String o, Integer page, Integer count) {
        return fileDBRepository.findByProjectAndOriginalnameOrderByCreatedatDesc(p, o, new PageRequest(page, count));
    }


    @Override
    @Cacheable(value = "file", key = "'file:'.concat(#p0)")
    public FileDB getFileByStored(String s) {
        return fileDBRepository.findByStoredname(s);
    }

    @Override
    @Cacheable(value = "files", key = "'project:'.concat(#p0.projectidx).concat(':imgs')")
    public List<FileDB> getImgs(Project p) {
        return fileDBRepository.findByProjectAndTypeOrderByCreatedatDesc(p, "image");
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "files", condition = "#p0.project!=null", key = "'project:'.concat(#p0.project.projectidx).concat(':files')"),
            @CacheEvict(value = "file", key = "'file:'.concat(#p0.filedbidx)"),
            @CacheEvict(value = "file", key = "'file:'.concat(#p0.storedname)"),
            @CacheEvict(value = "files", key = "'project:'.concat(#p0.project.projectidx).concat(':imgs')", condition = "#p0.project!=null && #p0.type=='image'")
    })
    public FileDB save(FileDB f) {
        return fileDBRepository.saveAndFlush(f);
    }
}
