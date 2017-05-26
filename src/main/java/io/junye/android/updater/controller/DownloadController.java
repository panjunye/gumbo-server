package io.junye.android.updater.controller;

import io.junye.android.updater.component.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Administrator on 2017/3/21 0021.
 *
 */

@Controller
@RequestMapping("download")
public class DownloadController {

    private final FileManager fileManager;

    @Autowired
    public DownloadController(FileManager fileManager) {

        this.fileManager = fileManager;

    }

    @GetMapping(value = "**")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response){

        String relativeUri = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();

        relativeUri = relativeUri.replace("/download","");

        try {
            relativeUri = URLDecoder.decode(relativeUri,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("下载文件错误");
        }


        System.out.println("relativeUri:" + relativeUri);

        FileManager.FileHelper fileHelper = fileManager.build(relativeUri);


        Path path = fileHelper.getAbsolutePath();

        File file = path.toFile();



        String mimeType;

        try {
            mimeType = Files.probeContentType(path);
        } catch (IOException e) {
            throw new RuntimeException("下载文件错误");
        }


        response.setContentType(mimeType);

        response.addHeader("Content-Disposition", "attachment; filename=" + file.getName());

        try{
            Files.copy(path,response.getOutputStream());
            response.getOutputStream().flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
