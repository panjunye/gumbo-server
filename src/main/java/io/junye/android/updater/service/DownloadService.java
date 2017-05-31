package io.junye.android.updater.service;

import io.junye.android.updater.component.FileManager;
import io.junye.android.updater.exception.AppNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by junye on 5/26/17.
 *
 */
@Service
public class DownloadService {

    private final FileManager fileManager;

    public DownloadService(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void download(HttpServletResponse response, String fileId) {

        FileManager.FileHelper fileHelper = fileManager.build(fileId);

        Path path = fileHelper.getAbsolutePath();

        File file = path.toFile();

        if(!file.exists()){
            throw new AppNotFoundException("文件不存在");
        }
        String mimeType;

        try {
            mimeType = Files.probeContentType(path);
        } catch (IOException e) {
            throw new RuntimeException("下载文件错误");
        }
        response.setContentType(mimeType);

        response.setContentLengthLong(file.length());

        response.addHeader("Content-Disposition", "attachment; filename=" + file.getName());


        try{
            Files.copy(path,response.getOutputStream());
            response.getOutputStream().flush();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
