package io.junye.android.updater.dao;

import io.junye.android.updater.entity.App;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Junye on 2017/3/20.
 */
public interface AppDao extends JpaRepository<App,Long> {
    App findByName(String name);

    App findByPkgName(String pkgName);

    App findByAppKey(String appKey);
}
