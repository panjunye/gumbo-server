package io.junye.android.updater.dao;

import io.junye.android.updater.entity.Patch;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Junye on 2017/3/17.
 *
 */
public interface PatchDao extends JpaRepository<Patch,Long> {


}
