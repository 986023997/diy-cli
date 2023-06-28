package com.binturong.cli.core.exception;

/**
 * @author zhulin
 * @date 2023-06-27 17:42
 */
public class CliException extends RuntimeException{
    public CliException(String message) {
        super(message);
    }
}
