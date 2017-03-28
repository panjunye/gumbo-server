package io.junye.android.updater.rest;

import io.junye.android.updater.bean.Apk;
import io.junye.android.updater.exception.AppInternalException;
import io.junye.android.updater.service.ApkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/3/20 0020.
 *
 */
@RestController
@RequestMapping("api/apps/{appName}/apks")
public class ApkRestController {

    private final ApkService apkService;


    @Autowired
    public ApkRestController(ApkService apkService) {
        this.apkService = apkService;
    }

    @GetMapping
    public List<Apk> getApks(@PathVariable("appName") String appName){
        return apkService.getApks(appName);
    }

    @PostMapping
    public Apk addApk(@PathVariable("appName") String appName,
                      @RequestParam(value = "updateLog",required = false) String updateLog,
                      @RequestParam("apk") MultipartFile apk){
        try {
            return apkService.addApk(appName,updateLog,apk);
        } catch (IOException e) {
            throw new AppInternalException(e);
        }
    }

    @DeleteMapping("{versionCode}")
    public void deleteApk(@PathVariable("appName") String appName,
                          @PathVariable("versionCode") Long versionCode){
        apkService.deleteApk(appName,versionCode);
    }


}
