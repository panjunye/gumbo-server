package io.junye.android.updater.exception;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
public class AppInternalException extends RuntimeException {

    public static final String MESSAGE = "系统发生错误";
    public AppInternalException(String message) {
        super(message);
    }

    public AppInternalException() {
        super(MESSAGE);
    }

    public AppInternalException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
