package io.junye.android.updater.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/3/20 0020.
 * 全局异常处理器
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AppConflictException.class)
    @ResponseBody
    AppError handleConflictException(HttpServletRequest req, Exception ex) {
        ex.printStackTrace();
        return new AppError(ex.getMessage());
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(AppInternalException.class)
    @ResponseBody
    AppError handleInternalException(HttpServletRequest req, Exception ex) {
        ex.printStackTrace();
        return new AppError(ex.getMessage());

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AppIllegalArgumentException.class)
    @ResponseBody
    AppError handleIllegalArgumentException(HttpServletRequest req, Exception ex) {
        ex.printStackTrace();
        return new AppError(ex.getMessage());
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AppNotFoundException.class)
    @ResponseBody
    AppError handleNotFoundException(HttpServletRequest req, Exception ex) {
        ex.printStackTrace();
        return new AppError(ex.getMessage());
    }

}
