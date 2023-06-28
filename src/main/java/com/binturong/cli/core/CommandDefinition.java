package com.binturong.cli.core;

import com.binturong.cli.core.exception.CliException;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhulin
 * @date 2023-06-27 11:23
 */
public class CommandDefinition{

    private String commandKey;
    private Map<String, Option> fullOptionMap = new HashMap<>();
    private Map<String, Option> shortOptionMap = new HashMap<>();
    private Map<String, Argument> argumentMap = new HashMap<>();

    public CommandDefinition(String commandKey) {
        this.commandKey = commandKey;
    }

    public String getKey() {
        return commandKey;
    }


    public void addOption(@Nullable Option option) {
        if (option == null) {
            throw new CliException("option not be null");
        }
        String name = option.getName();
        if (StringUtils.isNotEmpty(name)) {
            fullOptionMap.put(name, option);
        }
        String shortName = option.getShortName();
        if (StringUtils.isNotEmpty(shortName)) {
            shortOptionMap.put(shortName, option);
        }
    }


    public void addArgument(Argument argument) {
        if (argument == null) {
            throw new CliException("argument not be null");
        }
        argumentMap.put(argument.getArgName(), argument);
    }

    public Option getOption(String option) {
        return fullOptionMap.containsKey(option) ? fullOptionMap.get(option) : shortOptionMap.get(option);
    }
}
