package com.sajo.teamkerbell.entity;

import com.sajo.teamkerbell.util.CommonUtils;
import com.sajo.teamkerbell.util.FileUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;


@Data
@Entity
//@NamedNativeQuery(name = "FileDB.groupbytest",
//        query = "select fd.originalname as Originalname, group_concat(distinct u.name) as Uploader, group_concat(distinct fd.tag) as tag from FileDB fd JOIN User u on fd.useridx=u.useridx JOIN Project p on fd.projectidx=p.projectidx where p.projectidx=?1 group by fd.originalname")
@Table(name = "FileDB")
@EqualsAndHashCode
@Slf4j
public class FileDB implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "FILEID")
    private Integer fileId;

    @Column(name = "USERID")
    private Integer userId;


    @Column(name = "PROJECTID")
    private Integer projectId;

    @Column(name = "STOREDNAME")
    private String storedName;

    @Column(name = "ORIGINALNAME")
    private String originalName;

    @Enumerated(EnumType.STRING)
    private FileType type;

    @Column(name = "PATH")
    private String path;

    @Column(name = "TAG")
    private String tag;

    @Column(name = "CREATEDAT")
    private Date createdAt;

    @Column(name = "UPDATEDAT")
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    public enum FileType {
        IMAGE,

    }

    public void writeTo(HttpServletResponse response) {
        try (BufferedInputStream in = new BufferedInputStream(
                new FileInputStream(this.path + "/" + this.originalName))) {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(512);
            int imageByte;
            while ((imageByte = in.read()) != -1)
                byteStream.write(imageByte);
            response.setContentType("image/*");
            byteStream.writeTo(response.getOutputStream());
        } catch (IOException e) {
            log.error("Failed to render image", e);
        }
    }

    public static FileDB upload(MultipartFile file, String directory) {
        FileDB fileDB = null;
        try {
            String originalFileName = file.getOriginalFilename(); // 파일 이름
            String originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf('.')); // 파일 확장자
            String storedFileName = CommonUtils.getRandomString() + originalFileExtension; //암호화된 고유한 파일 이름
            fileDB = new FileDB(storedFileName, originalFileName, FileDB.FileType.IMAGE, directory, null);
            File folder = new File(directory); // 폴더
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File transFile = new File(directory + File.pathSeparator + storedFileName); // 전송된 파일
            file.transferTo(transFile);
        } catch (IOException e) {
            log.error("Failed to upload", e);
        }
        return fileDB;
    }

    public void download(String name, HttpServletRequest request, HttpServletResponse response) {
        String folder = this.path;
        File file = new File(folder + File.pathSeparator + name);
        response.reset();
        FileUtil.setDownloadHeader(originalName, file, request, response);
        try (InputStream in = new BufferedInputStream(new FileInputStream(file));
             OutputStream os = new BufferedOutputStream(response.getOutputStream())) {
            byte[] b = new byte[(int) file.length()];
            int leng;
            while ((leng = in.read(b)) > 0) {
                os.write(b, 0, leng);
            }
        } catch (IOException e) {
            log.error("Failed to download", e);
        }
    }

    public void assignTo(User user) {
        this.userId = user.getUserId();
        user.setImg("loadImg?name=" + this.storedName);
    }

    public void assignTo(Project project) {
        this.projectId = project.getProjectId();
    }

    public FileDB(String storedName, String originalName, FileType type, String path, String tag) {
        this.storedName = storedName;
        this.originalName = originalName;
        this.type = type;
        this.path = path;
        this.tag = tag;
    }

    public FileDB() {
    }


}
