package io.junye.android.updater.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/3/22 0022.
 */
@Entity
public class Patch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long size;

    private String md5;

    @Transient
    private String url;

    @JsonIgnore
    private String fileId;

    @OneToOne(mappedBy = "patch")
    @JsonIgnore
    private Apk apk;


    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Apk getApk() {
        return apk;
    }

    public void setApk(Apk apk) {
        this.apk = apk;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "Patch{" +
                "id=" + id +
                ", size=" + size +
                ", md5='" + md5 + '\'' +
                ", url='" + url + '\'' +
                ", fileId='" + fileId + '\'' +
                ", apk=" + apk +
                '}';
    }
}
