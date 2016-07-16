package com.shape.web.util;

import com.shape.web.controller.HomeController;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by seongahjo on 2016. 7. 16..
 */
public class RepositoryUtil {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    public static final String repositoryPrefix="GitRepository";
    public static Repository createRepository(Integer projectIdx) {
        Repository repository=null;
        try {
            File localpath = new File(FileUtil.getFoldername(projectIdx));
            localpath.delete();

             repository = FileRepositoryBuilder.create(new File(localpath, ".git"));
            repository.create();
        } catch (IOException e) {

        }
        return repository;
    }

    public static void commit(Integer projectIdx){
            FileRepositoryBuilder builder = new FileRepositoryBuilder();
        try {
            File file = new File(FileUtil.getFoldername(projectIdx),".git");
            Repository repository = builder.setGitDir(file)
                    .readEnvironment()
                    .findGitDir()
                    .build();
            if(repository==null)
                repository=createRepository(projectIdx);
            Git git= new Git(repository);
            logger.info(repository.getDirectory()+" and "+ repository.getDirectory().getParent());
            logger.info(repository.getDirectory()+" ");


            git.add().addFilepattern(".").call();
            git.commit().setMessage("upload finished").call();
            Iterable<RevCommit> logs = git.log().addPath("test.txt").call();
            for(RevCommit rev : logs){
                logger.info("Commit : "+ rev +" "+rev.getName());

            }

        }catch(IOException e) {
            logger.info("Repository not existed");
            createRepository(projectIdx);
        }catch(GitAPIException e){
            logger.info("Commit Failed");
        }
        }


}
