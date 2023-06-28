package com.binturong.cli.core.exception;

import com.binturong.cli.core.Command;
import com.binturong.cli.core.CommandDefinition;

/**
 * @author zhulin
 * @date 2023-06-25 15:18
 */
public class NotSupportCommandException extends RuntimeException {


    private static final long serialVersionUID = 1588052812677609561L;

    public NotSupportCommandException(CommandDefinition command) {
        this(String.format("The command of % is not support", command.getKey()));
    }

    public NotSupportCommandException() {
        this(String.format("The command  is not support"));
    }

    public NotSupportCommandException(String message) {
        super(message);
    }
}
