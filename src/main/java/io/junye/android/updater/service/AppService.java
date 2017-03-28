package io.junye.android.updater.service;

import io.junye.android.updater.bean.Apk;
import io.junye.android.updater.bean.App;
import io.junye.android.updater.bean.Patch;
import io.junye.android.updater.dao.ApkDao;
import io.junye.android.updater.dao.AppDao;
import io.junye.android.updater.exception.AppConflictException;
import io.junye.android.updater.exception.AppInternalException;
import io.junye.android.updater.exception.AppNotFoundException;
import io.junye.android.updater.jna.Bsdiff;
import io.junye.android.updater.util.AppUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Value("${app.apk.dir}")
    private String apkDir;

    @Value("${app.apk.download-base-url}")
    private String apkDownloadBaseUrl;


    @Autowired
    public AppService(AppDao appDao, ApkDao apkDao) {
        this.appDao = appDao;
        this.apkDao = apkDao;
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

        App app = getAppByName(appName);

        appDao.delete(app);
    }


    public void patchApk(String appName) {

        App app = getAppByName(appName);

        if(app.isPatching()){
            throw new AppConflictException("正在生成增量包");
        }

        app.setPatching(true);

        appDao.save(app);


        if(app.isPatch()){
            throw new AppConflictException("已经生成增量包");
        }

        List<Apk> apks = app.getApks();

        if(apks.size() <= 0)
            return;

        // 删除以前生成的补丁文件
        AppUtils.deletePatches(app);

        // 获取最新的APK
        Apk newApk = apks.get(0);

        try {
            for (int i = 1; i < apks.size(); i++) {
                Apk apk = apks.get(i);
                Path patchPath = Paths.get(apkDir,app.getName(),"patches",
                        app.getPkgName()+apk.getVersionCode()+"_" + newApk.getVersionCode()+".patch");
                if(patchPath.getParent() != null){
                        Files.createDirectories(patchPath.getParent());
                }

                int result = Bsdiff.INSTANCE.diff(apk.getPath(),newApk.getPath(),patchPath.toString());
                if(result == 0){
                    String md5;
                    try(InputStream is = Files.newInputStream(patchPath)){
                        md5 = DigestUtils.md5Hex(is);
                    }

                    Patch patch = new Patch();
                    patch.setPath(patchPath.toString());
                    patch.setMd5(md5);
                    patch.setSize(patchPath.toFile().length());
                    patch.setUrl(apkDownloadBaseUrl +"/" + app.getName() +  "/patches/" + patchPath.getFileName());
                    apk.setPatch(patch);
                    apkDao.save(apk);
                }else{
                    throw new IOException();
                }
            }
            app.setPatch(true);
            appDao.save(app);
        } catch (IOException e) {
            throw new AppInternalException();
        } finally {
            System.out.println("finally set app patching false");
            app.setPatching(false);
            appDao.save(app);
        }
    }


    private static String newAppKey(){
        return UUID.randomUUID().toString().replace("-","");
    }

    private App getAppByName(String appName){

        App app = appDao.findByName(appName);
        if(app == null){
            throw new AppNotFoundException(String.format("应用 %s 不存在",appName));
        }
        return app;
    }

}
