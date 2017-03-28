package io.junye.android.updater.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * Created by Administrator on 2017/3/22 0022.
 */
public interface Bsdiff extends Library {
    Bsdiff INSTANCE = Native.loadLibrary("lib/bsdiff",Bsdiff.class);
    int diff(String oldFile,String newFile,String patchFile);
}
