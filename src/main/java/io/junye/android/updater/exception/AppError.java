package io.junye.android.updater.exception;

/**
 * Created by Administrator on 2017/3/20 0020.
 */
public class AppError {
    private String error;

    public AppError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
