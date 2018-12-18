package com.uguke.reflect;

import android.annotation.TargetApi;
import android.os.Build;

/**
 * 功能描述：类型异常
 * @author LeiJue
 * @date 2018/12/18
 */
public class TypeException extends RuntimeException {

    public TypeException() {}

    public TypeException(String message) {
        super(message);
    }

    public TypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeException(Throwable cause) {
        super(cause);
    }

    @TargetApi(Build.VERSION_CODES.N)
    public TypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
