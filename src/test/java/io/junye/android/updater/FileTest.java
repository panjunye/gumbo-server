package io.junye.android.updater;

import org.junit.Test;
import org.springframework.util.FileCopyUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
public class FileTest {
    @Test
    public void testSeperator() throws Exception {
        Path src = Paths.get("D:","apk","test.apk");
        Path target = Paths.get("D:","apk","new.apk");
        if(target.getParent() != null){
            Files.createDirectories(target.getParent());
        }
        Files.copy(src,target, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    public void testPath() throws Exception {
        Path path = Paths.get("D:","a","b","c");
        System.out.println(path.toString());

    }
}
