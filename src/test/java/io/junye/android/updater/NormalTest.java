package io.junye.android.updater;

import io.junye.android.updater.component.FileManager;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;

/**
 * Created by junye on 5/8/17.
 */
public class NormalTest {

    FileManager fileManager;

    String relativeUrl = "/hello/world/123";

    @Before
    public void init() throws Exception {
        fileManager = new FileManager();
        fileManager.downloadBaseUrl = "http://example.com";
        fileManager.fileBaseDir = "/home/user";
    }


}
