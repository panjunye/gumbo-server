package io.junye.android.updater.util;

import io.junye.android.updater.bean.Apk;
import io.junye.android.updater.bean.App;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Administrator on 2017/3/22 0022.
 */
public class AppUtils {

    public static void deleteApp(App app){
        if(app.getApks() == null){
            return;
        }

        for(Apk apk : app.getApks()){
            try {
                Files.delete(Paths.get(apk.getPath()));
                if(apk.getPatch() != null){
                    Files.delete(Paths.get(apk.getPatch().getPath()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deletePatches(App app) {
        if(app.getApks() == null){
            return;
        }

        for(Apk apk : app.getApks()){
            if(apk.getPatch() != null){
                try {
                    Files.delete(Paths.get(apk.getPatch().getPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
