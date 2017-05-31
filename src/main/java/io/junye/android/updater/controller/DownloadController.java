package io.junye.android.updater.controller;

import io.junye.android.updater.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Junye on 2017/3/21.
 *
 */

@Controller
@RequestMapping("download")
public class DownloadController {


    private final DownloadService downloadService;


    @Autowired
    public DownloadController( DownloadService downloadService) {

        this.downloadService = downloadService;
    }

    @GetMapping("**")
    public void downloadFile(HttpServletRequest request,HttpServletResponse response){

        String relativeUri = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();

        String fileId = relativeUri.replace("/download","");

        try {
            fileId = URLDecoder.decode(fileId,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("下载文件错误");
        }

        downloadService.download(response,fileId);

    }


}
