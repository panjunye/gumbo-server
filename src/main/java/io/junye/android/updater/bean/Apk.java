package io.junye.android.updater.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/3/17 0017.
 */
@Entity
public class Apk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private App app;

    /**
     * APK versionCode
     */
    private Long versionCode;

    /**
     * APK versionName
     */
    private String versionName;

    /**
     * APK的更新日志
     */
    @Column(columnDefinition="text")
    private String updateLog;

    /**
     * 下载地址
     */
    private String url;

    /**
     * 文件路径
     */
    private String path;

    /**
     * APK md5
     */
    private String md5;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 增量补丁
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Patch patch;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String apkUrl) {
        this.url = apkUrl;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Patch getPatch() {
        return patch;
    }

    public void setPatch(Patch patch) {
        this.patch = patch;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Apk apk = (Apk) o;

        return versionCode != null ? versionCode.equals(apk.versionCode) : apk.versionCode == null;
    }

    @Override
    public int hashCode() {
        return versionCode != null ? versionCode.hashCode() : 0;
    }
}
