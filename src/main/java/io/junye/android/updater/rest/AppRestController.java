package io.junye.android.updater.rest;

import io.junye.android.updater.bean.App;
import io.junye.android.updater.service.ApkService;
import io.junye.android.updater.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20 0020.
 */
@RestController
@RequestMapping("api/apps")
public class AppRestController {

    private final ApkService apkService;
    private final AppService appService;

    @Autowired
    public AppRestController(ApkService apkService, AppService appService) {
        this.apkService = apkService;
        this.appService = appService;
    }

    @GetMapping
    public List<App> getApps(){
        return appService.getAllApps();
    }

    @PostMapping
    public App addApp(@RequestBody App app){
        return appService.addApp(app);
    }

    @DeleteMapping("{appName}")
    public void deleteApp(@PathVariable("appName") String appName){
        appService.deleteAppByName(appName);
    }

    @PatchMapping("{appName}")
    public void patchApk(@PathVariable("appName") String appName){
        appService.patchApk(appName);
    }
}
