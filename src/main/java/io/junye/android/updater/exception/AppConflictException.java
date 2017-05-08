package io.junye.android.updater.exception;

/**
 * Created by Junye on 2017/3/20.
 *
 */
public class AppConflictException extends RuntimeException{

    public AppConflictException(String message) {
        super(message);
    }
}
