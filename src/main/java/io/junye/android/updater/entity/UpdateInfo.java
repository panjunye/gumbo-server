package io.junye.android.updater.entity;


import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Administrator on 2017/3/14 0014.
 * 储存APP版本信息。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateInfo {


    /**
     * 是否需要更新
     */
    private Boolean update;

    /**
     * 新版本的versionCode
     */
    private Long versionCode;

    /**
     * 新版本的versionName
     */
    private String versionName;

    /**
     * 新版本的更新日志
     */
    private String updateLog;

    /**
     * 是否增量更新
     */
    private Boolean delta;

    /**
     * 下载地址
     */
    private String apkUrl;

    /**
     * 全量更新的APK md5
     */
    private String newMd5;

    /**
     * 增量更新的patch文件的MD5
     */
    private String patchMd5;

    /**
     * 增量更新补丁的下载地址
     */
    private String patchUrl;

    /**
     * 全量更新的APK的文件大小
     */
    private Long targetSize;

    /**
     * 增量更新的patch文件的大小
     */
    private Long patchSize;


    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }

    public Long getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Long versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateLog() {
        return updateLog;
    }

    public void setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
    }

    public Boolean getDelta() {
        return delta;
    }

    public void setDelta(Boolean delta) {
        this.delta = delta;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getNewMd5() {
        return newMd5;
    }

    public void setNewMd5(String newMd5) {
        this.newMd5 = newMd5;
    }

    public String getPatchMd5() {
        return patchMd5;
    }

    public void setPatchMd5(String patchMd5) {
        this.patchMd5 = patchMd5;
    }

    public String getPatchUrl() {
        return patchUrl;
    }

    public void setPatchUrl(String patchUrl) {
        this.patchUrl = patchUrl;
    }

    public Long getTargetSize() {
        return targetSize;
    }

    public void setTargetSize(Long targetSize) {
        this.targetSize = targetSize;
    }

    public Long getPatchSize() {
        return patchSize;
    }

    public void setPatchSize(Long patchSize) {
        this.patchSize = patchSize;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "update=" + update +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", updateLog='" + updateLog + '\'' +
                ", delta=" + delta +
                ", apkUrl='" + apkUrl + '\'' +
                ", newMd5='" + newMd5 + '\'' +
                ", patchMd5='" + patchMd5 + '\'' +
                ", patchUrl='" + patchUrl + '\'' +
                ", targetSize=" + targetSize +
                ", patchSize=" + patchSize +
                '}';
    }
}
