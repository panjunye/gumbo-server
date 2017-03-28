package io.junye.android.updater.service;

import io.junye.android.updater.bean.Apk;
import io.junye.android.updater.bean.App;
import io.junye.android.updater.dao.ApkDao;
import io.junye.android.updater.dao.AppDao;
import io.junye.android.updater.exception.AppConflictException;
import io.junye.android.updater.exception.AppIllegalArgumentException;
import io.junye.android.updater.exception.AppNotFoundException;
import io.junye.android.updater.util.AppUtils;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * Created by Administrator on 2017/3/20 0020.
 *
 */
@Service
public class ApkService {

    public static final String PREFIX = "APK";
    public static final String SUFFIX = ".tmp";

    private final ApkDao apkDao;
    private final AppDao appDao;

    @Value("${app.apk.dir}")
    private String apkDir;

    @Value("${app.apk.download-base-url}")
    private String apkDownloadBaseUrl;

    @Autowired
    public ApkService(ApkDao apkDao, AppDao appDao) {
        this.apkDao = apkDao;
        this.appDao = appDao;
    }

    public Apk addApk(String appName,String updateLog,MultipartFile multiPartApk) throws IOException{
        App app = getAppByName(appName);

        Path tempApkPath = Files.createTempFile(PREFIX,SUFFIX);
        tempApkPath.toFile().deleteOnExit();
        Files.copy(multiPartApk.getInputStream(),tempApkPath, StandardCopyOption.REPLACE_EXISTING);

        ApkMeta apkMeta;
        try(ApkFile apkFile = new ApkFile(tempApkPath.toFile())){
            apkMeta = apkFile.getApkMeta();
        }

        // 计算md5
        String md5;
        try(InputStream is = Files.newInputStream(tempApkPath)){
            md5 = DigestUtils.md5Hex(is);
        }

        String pkgName = apkMeta.getPackageName();
        Long versionCode = apkMeta.getVersionCode();
        String versionName = apkMeta.getVersionName();

        if(apkDao.findByAppAndVersionCode(app,versionCode) != null){
            throw new AppConflictException(String.format("版本 %s 已存在",versionCode.toString()));
        }

        Apk newest = apkDao.findTopByAppOrderByVersionCodeDesc(app);
        if(newest != null && newest.getVersionCode() > versionCode){
            throw new AppIllegalArgumentException(String.format("当前版本%s小于最新版本%s",
                    versionCode.toString(),newest.getVersionCode().toString()));

        }

        // TODO 应当校验APK的合法性，如果APK合法，那么把APK复制到下载目录中

        Path realAPkPath = Paths.get(apkDir,appName,pkgName+versionName + ".apk");
        if(realAPkPath.getParent() != null){
            Files.createDirectories(realAPkPath.getParent());
        }
        Files.copy(tempApkPath,realAPkPath,StandardCopyOption.REPLACE_EXISTING);

        Apk apk = new Apk();
        apk.setPath(realAPkPath.toFile().getAbsolutePath());
        apk.setVersionName(versionName);
        apk.setVersionCode(versionCode);
        apk.setMd5(md5);
        apk.setSize(tempApkPath.toFile().length());
        apk.setUpdateLog(updateLog);
        apk.setUrl(apkDownloadBaseUrl + "/" + appName + "/" + pkgName+versionName+".apk");
        apk.setApp(app);

        // 新增加了APK，应该重新生成补丁文件
        app.setPatch(false);

        apkDao.save(apk);

        return apk;
    }

    public List<Apk> getApks(String appName) {
        App app = getAppByName(appName);

        return app.getApks();
    }

    public void deleteApk(String appName, Long versionCode) {
        App app = getAppByName(appName);

        Apk apk = apkDao.findByAppAndVersionCode(app,versionCode);
        if(apk == null){
            throw new AppNotFoundException(String.format("版本 %s 不存在",versionCode.toString()));
        }

        Apk latestApk = apkDao.findTopByAppOrderByVersionCodeDesc(app);

        if(apk.equals(latestApk)){
            app.setPatch(false);
        }
        appDao.save(app);
        apkDao.delete(apk);
    }



    private App getAppByName(String appName){
        App app = appDao.findByName(appName);
        if(app == null){
            throw new AppNotFoundException(String.format("应用 %s 不存在",appName));
        }
        return app;
    }

}
