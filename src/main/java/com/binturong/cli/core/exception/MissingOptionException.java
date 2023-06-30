package com.binturong.cli.core.exception;

/**
 * @author zhulin
 * @date 2023-06-30 9:20
 */
public class MissingOptionException extends RuntimeException{
    public MissingOptionException(String message) {
        super(message);
    }
}
