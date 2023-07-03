package com.binturong.cli.core;

import java.util.List;

/**
 * @author zhulin
 * @date 2023-06-29 17:57
 */
public interface CommandDefinition {

    String getKey();

    void addOption(Option option);

    Option getLongOption(String token);

    boolean isOption(String token);

    List<Option> getRequireOptions();

    boolean  allowedOverwrite();

    Option getShortOption(String token);
}
