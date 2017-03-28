package io.junye.android.updater.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by Administrator on 2017/3/22 0022.
 *
 */
@Service
public class DownloadService {

    @Value("${app.apk.dir}")
    private String apkDir;

    public File getFile(String filePath) {
        return Paths.get(apkDir,filePath).toFile();
    }
}
