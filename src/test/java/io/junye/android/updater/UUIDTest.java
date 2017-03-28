package io.junye.android.updater;

import org.junit.Test;

import java.util.UUID;

/**
 * Created by Administrator on 2017/3/22 0022.
 */
public class UUIDTest {
    @Test
    public void uuidTest() throws Exception {

        System.out.println(UUID.randomUUID().toString().replace("-",""));

    }
}
