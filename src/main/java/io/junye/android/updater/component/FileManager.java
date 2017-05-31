package io.junye.android.updater.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Created by junye on 5/8/17.
 *
 */
@Component
public class FileManager {

    @Value("${app.apk.dir}")
    public String fileBaseDir;

    @Value("${app.apk.download-base-url}")
    public String downloadBaseUrl;

    public FileHelper build(String... more){
        return new FileHelper(more);
    }

    /**
     *
     * @param fileId 文件的id
     * @return 返回FileHelper实例
     */
    public FileHelper build(String fileId){
        if(fileId.startsWith("/")){
            fileId = fileId.substring(1);
        }
        String[] more = fileId.split("/");
        return build(more);
    }

    public class FileHelper{

        private String[] more;

        private FileHelper(String... more){
            this.more = more;
        }

        public Path getAbsolutePath(){
            return FileSystems.getDefault().getPath(fileBaseDir,more);
        }

        public File getFile(){
            return getAbsolutePath().toFile();
        }

        public String getAbsolutePathString(){
            return getAbsolutePath().toFile().getAbsolutePath();
        }

        public String getAbsoluteUrl(){

            return downloadBaseUrl + getFileId();
        }

        public String getFileId(){
            StringBuilder sb = new StringBuilder();
            for(String s : more){
                sb.append("/").append(s);
            }
            return sb.toString();
        }
    }


}
