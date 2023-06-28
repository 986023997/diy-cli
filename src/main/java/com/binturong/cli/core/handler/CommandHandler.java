package com.binturong.cli.core.handler;

import com.binturong.cli.core.CommandLine;

/**
 * @author zhulin
 * @date 2023-06-26 13:58
 */
public interface CommandHandler {
    boolean support(String commandType);

    String handle(CommandLine commandLine);
}
