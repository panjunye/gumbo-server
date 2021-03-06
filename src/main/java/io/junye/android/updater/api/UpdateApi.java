package io.junye.android.updater.api;

import io.junye.android.updater.pojo.UpdateInfo;
import io.junye.android.updater.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * Created by Junye on 2017/3/14.
 */
@RestController
@RequestMapping("api")
public class UpdateApi {

    private final UpdateService updateService;

    @Autowired
    public UpdateApi(UpdateService updateService) {
        this.updateService = updateService;
    }

    @RequestMapping(value = "checkupdate")
    public UpdateInfo checkUpdate(
            @NotNull @RequestParam("versionCode") Long versionCode,
            @NotNull @RequestParam("appKey") String appKey){

        return updateService.checkUpdate(versionCode,appKey);
    }

}
