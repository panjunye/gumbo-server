package io.junye.android.updater.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Administrator on 2017/3/20 0020.
 * 表示一个Android 应用
 */

@Entity
public class App {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String name;

    @Column(unique = true,nullable = false)
    private String pkgName;

    @Column(unique = true,nullable = false)
    private String appKey;

    @Column(nullable = false)
    private Boolean patch = false;

    private Boolean patching = false;

    @OneToMany(mappedBy = "app",cascade = CascadeType.ALL)
    @JsonIgnore
    @OrderBy("versionCode desc ")
    private List<Apk> apks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public boolean isPatch() {
        return patch;
    }

    public void setPatch(boolean patch) {
        this.patch = patch;
    }

    public List<Apk> getApks() {
        return apks;
    }

    public void setApks(List<Apk> apks) {
        this.apks = apks;
    }

    public Boolean isPatching() {
        return patching;
    }

    public void setPatching(Boolean patching) {
        this.patching = patching;
    }



}
