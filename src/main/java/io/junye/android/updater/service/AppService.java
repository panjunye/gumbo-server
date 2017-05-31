package io.junye.android.updater.service;

import io.junye.android.updater.component.FileManager;
import io.junye.android.updater.entity.Apk;
import io.junye.android.updater.entity.App;
import io.junye.android.updater.entity.Patch;
import io.junye.android.updater.dao.ApkDao;
import io.junye.android.updater.dao.AppDao;
import io.junye.android.updater.exception.AppConflictException;
import io.junye.android.updater.exception.AppInternalException;
import io.junye.android.updater.exception.AppNotFoundException;
import io.junye.android.updater.jna.Bsdiff;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/3/20 0020.
 *
 */
@Service
public class AppService {

    private final AppDao appDao;
    private final ApkDao apkDao;

    private final FileManager fileManager;


    @Autowired
    public AppService(AppDao appDao, ApkDao apkDao, FileManager fileManager) {
        this.appDao = appDao;
        this.apkDao = apkDao;
        this.fileManager = fileManager;
    }

    public List<App> getAllApps() {
        return appDao.findAll();
    }

    public App addApp(App app){
        if(appDao.findByName(app.getName()) != null){
            throw new AppConflictException(String.format("%s 已存在",app.getName()));
        }
        if(appDao.findByPkgName(app.getPkgName()) != null){
            throw new AppConflictException(String.format("包名 %s 已存在 ",app.getPkgName()));
        }

        app.setAppKey(newAppKey());

        return appDao.save(app);
    }

    public void deleteAppByName(String appName) {

        App app = checkAppExistByName(appName);

        appDao.delete(app);
    }


    public void patchApk(String appName) {

        App app = checkAppExistByName(appName);

        if(app.isPatch()){

            throw new AppConflictException("已经生成增量包");
        }

        if(app.isPatching()){
            throw new AppConflictException("正在生成增量包");
        }


        List<Apk> apks = app.getApks();

        if(apks.size() <= 0)
            return;



        app.setPatching(true);

        appDao.save(app);

        // TODO 删除以前生成的补丁文件

        // 提出最新的APK
        Apk newApk = apks.get(0);

        String newApkPath = fileManager.build(newApk.getFileId()).getAbsolutePathString();

        try {
            for (int i = 1; i < apks.size(); i++) {

                Apk oldApk = apks.get(i);

                String oldApkPath = fileManager.build(oldApk.getFileId()).getAbsolutePathString();

                String[] more = new String[]{app.getPkgName(),"patches",app.getPkgName()+oldApk.getVersionName()+"-" + newApk.getVersionName()+".patch"};

                FileManager.FileHelper patchFileHelper = fileManager.build(more);

                Path patchPath = patchFileHelper.getAbsolutePath();

                if(patchPath.getParent() != null){
                    Files.createDirectories(patchPath.getParent());
                }

                int result = Bsdiff.INSTANCE.diff(oldApkPath,newApkPath,patchPath.toString());

                if(result == 0){
                    String md5;
                    try(InputStream is = Files.newInputStream(patchPath)){
                        md5 = DigestUtils.md5Hex(is);
                    }

                    Patch patch = new Patch();
                    patch.setMd5(md5);
                    patch.setSize(patchPath.toFile().length());
                    patch.setFileId(patchFileHelper.getFileId());
                    oldApk.setPatch(patch);
                    apkDao.save(oldApk);
                }else{
                    throw new IOException();
                }
            }
            app.setPatch(true);
            appDao.save(app);
        } catch (IOException e) {
            throw new AppInternalException(e);
        } finally {
            System.out.println("finally set app patching false");
            app.setPatching(false);
            appDao.save(app);
        }
    }


    /**
     *
     * @return 返回唯一APP KEY
     */
    private static String newAppKey(){
        return UUID.randomUUID().toString().replace("-","");
    }

    private App checkAppExistByName(String appName){

        App app = appDao.findByName(appName);
        if(app == null){
            throw new AppNotFoundException(String.format("应用 %s 不存在",appName));
        }
        return app;
    }

}
