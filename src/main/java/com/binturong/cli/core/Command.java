package com.binturong.cli.core;

import java.util.List;

/**
 * 定义命令
 *
 *
 * @author zhulin
 * @date 2023-06-25 15:14
 */
public interface Command extends Cloneable {

    String getKey();
    void addOption(Option option);
    void addArgument(Argument argument);
    Option getOption(String option);
    Argument getArgument(String argument);
    List<Option> getOptions();
}
