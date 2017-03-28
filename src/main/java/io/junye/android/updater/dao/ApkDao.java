package io.junye.android.updater.dao;

import io.junye.android.updater.bean.Apk;
import io.junye.android.updater.bean.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2017/3/17 0017.
 */
public interface ApkDao extends JpaRepository<Apk,Long> {

    Apk findByAppAndVersionCode(App app,Long versionCode);

    Apk findTopByAppOrderByVersionCodeDesc(App app);

}
