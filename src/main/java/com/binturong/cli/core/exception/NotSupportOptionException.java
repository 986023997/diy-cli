package com.binturong.cli.core.exception;

/**
 * @author zhulin
 * @date 2023-06-25 16:21
 */
public class NotSupportOptionException extends RuntimeException {

    public NotSupportOptionException() {
    }

    public NotSupportOptionException(String message) {
        super(message);
    }
}
