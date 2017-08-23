package io.junye.android.updater.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by junye on 15/07/2017.
 */
@Entity
public class AppExtra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jpush_app_key;

    private String jpush_master_secret;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJpush_app_key() {
        return jpush_app_key;
    }

    public void setJpush_app_key(String jpush_app_key) {
        this.jpush_app_key = jpush_app_key;
    }

    public String getJpush_master_secret() {
        return jpush_master_secret;
    }

    public void setJpush_master_secret(String jpush_master_secret) {
        this.jpush_master_secret = jpush_master_secret;
    }
}
