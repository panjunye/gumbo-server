package io.junye.android.updater.exception;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
public class AppNotFoundException extends RuntimeException {
    public AppNotFoundException(String message) {
        super(message);
    }
}
