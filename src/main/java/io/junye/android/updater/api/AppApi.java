package io.junye.android.updater.api;

import io.junye.android.updater.entity.App;
import io.junye.android.updater.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Junye on 2017/3/20.
 *
 */
@RestController
@RequestMapping("api/apps")
public class AppApi {

    private final AppService appService;

    @Autowired
    public AppApi(AppService appService) {
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
