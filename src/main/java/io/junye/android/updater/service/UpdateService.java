package io.junye.android.updater.service;

import io.junye.android.updater.component.FileManager;
import io.junye.android.updater.dao.PatchDao;
import io.junye.android.updater.entity.Apk;
import io.junye.android.updater.entity.App;
import io.junye.android.updater.entity.Patch;
import io.junye.android.updater.entity.UpdateInfo;
import io.junye.android.updater.dao.ApkDao;
import io.junye.android.updater.dao.AppDao;
import io.junye.android.updater.exception.AppNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.io.File;

/**
 * Created by Junye on 2017/3/17 0017.
 *
 */
@Service
@Transactional
public class UpdateService {

    private final ApkDao apkDao;
    private final AppDao appDao;
    private final PatchDao patchDao;
    private final FileManager fileManager;

    @Autowired
    public UpdateService(ApkDao apkDao, AppDao appDao, PatchDao patchDao, FileManager fileManager) {
        this.appDao = appDao;
        this.apkDao = apkDao;
        this.patchDao = patchDao;
        this.fileManager = fileManager;
    }

    public UpdateInfo checkUpdate(Long versionCode,String appKey){
        App app = appDao.findByAppKey(appKey);

        if(app == null){
            throw new AppNotFoundException("应用不存在");
        }

        UpdateInfo info = new UpdateInfo();

        Apk apk = apkDao.findTopByAppOrderByVersionCodeDesc(app);


        if(apk == null || versionCode >= apk.getVersionCode()){
            // 不需要更新
            info.setUpdate(false);
            return info;
        }

        info.setUpdate(true);
        info.setVersionName(apk.getVersionName());
        info.setVersionCode(apk.getVersionCode());
        info.setNewMd5(apk.getMd5());
        info.setTargetSize(apk.getSize());
        info.setApkUrl(fileManager.build(apk.getFileId()).getAbsoluteUrl());

        info.setUpdateLog(apk.getUpdateLog());

        Apk oldApk = apkDao.findByAppAndVersionCode(app,versionCode);

        Patch patch;


        if(oldApk != null
                && (patch = oldApk.getPatch()) != null
                && patch.getFileId() != null){

            FileManager.FileHelper patchHelper = fileManager.build(patch.getFileId());

            File patchFile = patchHelper.getAbsolutePath().toFile();

            if(!patchFile.exists()){
                // Patch文件已经不存在，则在数据库中删除Patch
                oldApk.setPatch(null);
                patchDao.delete(patch);
                apkDao.save(oldApk);
                app.setPatch(false);
                info.setDelta(false);
            }else{
                info.setDelta(true);
                info.setPatchMd5(patch.getMd5());
                info.setPatchSize(patch.getSize());
                info.setPatchUrl(patchHelper.getAbsoluteUrl());
            }

        }else{
            info.setDelta(false);
        }

        return info;
    }
}
