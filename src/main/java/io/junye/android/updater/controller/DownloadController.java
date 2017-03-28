package io.junye.android.updater.controller;

import io.junye.android.updater.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Administrator on 2017/3/21 0021.
 *
 */

@Controller
@RequestMapping("download")
public class DownloadController {
    private final DownloadService downloadService;

    @Autowired
    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @GetMapping(value = "**",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE )
    @ResponseBody
    public FileSystemResource downloadFile(HttpServletRequest request, HttpServletResponse response){
        String uri = request.getRequestURI();
        String filePath = uri.substring(uri.indexOf("/",1)).replace("/","\\");

        try {
            filePath = URLDecoder.decode(filePath,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("下载文件错误");
        }
        Path path = Paths.get(filePath);

        String mimeType;

        try {
            mimeType = Files.probeContentType(path);
        } catch (IOException e) {
            throw new RuntimeException("下载文件错误");
        }
        File file = downloadService.getFile(filePath);
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        return new FileSystemResource(downloadService.getFile(filePath));

    }


}
