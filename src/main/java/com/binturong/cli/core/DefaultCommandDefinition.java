package com.binturong.cli.core;

import com.binturong.cli.core.exception.CliException;
import com.sun.istack.internal.Nullable;
import com.sun.javafx.UnmodifiableArrayList;

import java.util.*;

/**
 * @author zhulin
 * @date 2023-06-27 11:23
 */
public class DefaultCommandDefinition implements CommandDefinition {

    private String commandKey;
    private Map<String, Option> fullOptionMap = new HashMap<>();
    private Map<String, Option> shortOptionMap = new HashMap<>();
    private Map<String, Argument> argumentMap = new HashMap<>();

    public DefaultCommandDefinition(String commandKey) {
        this.commandKey = commandKey;
    }

    @Override
    public String getKey() {
        return commandKey;
    }


    @Override
    public void addOption(@Nullable Option option) {
        if (option == null) {
            throw new CliException("option not be null");
        }
        String name = option.getName();
        if (StringUtils.isEmpty(name)) {
          throw new RuntimeException("option's name cannot be empty");
        }
        Option existFull = fullOptionMap.get(name);
        if (existFull != null && !allowedOverwrite()) {
            throw new RuntimeException("command not allowed overwrite");
        }
        fullOptionMap.put(name, option);
        String shortName = option.getShortName();
        if (StringUtils.isNotEmpty(shortName)) {
            Option existShortOption = shortOptionMap.get(shortName);
            if (existShortOption != null && existShortOption != option) {
                throw new RuntimeException("command not allowed overwrite");
            }
            shortOptionMap.put(shortName, option);
        }
    }


    public void addArgument(Argument argument) {
        if (argument == null) {
            throw new CliException("argument not be null");
        }
        argumentMap.put(argument.getArgName(), argument);
    }

    @Override
    public Option getLongOption(String option) {
        return fullOptionMap.get(option);
    }

    @Override
    public boolean isOption(String option) {
        return fullOptionMap.containsKey(option) || shortOptionMap.containsKey(option);
    }

    @Override
    public List<Option> getRequireOptions() {
       return Collections.unmodifiableList(new ArrayList<>(fullOptionMap.values()));
    }

    @Override
    public boolean allowedOverwrite() {
        return false;
    }

    @Override
    public Option getShortOption(String token) {
        return shortOptionMap.get(token);
    }
}
