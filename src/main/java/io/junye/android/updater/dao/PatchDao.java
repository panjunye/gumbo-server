package io.junye.android.updater.dao;

import io.junye.android.updater.bean.Patch;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2017/3/17 0017.
 */
public interface PatchDao extends JpaRepository<Patch,Long> {


}
