package com.binturong.cli.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhulin
 * @date 2023-06-28 16:06
 */
public class DefaultCommand implements Command {
    protected Map<String, Argument> arguments = new HashMap<>();
    protected Map<String, Option> options = new HashMap<>();


    public DefaultCommand(String commandKey) {
        this.commandKey = commandKey;
    }

    private String commandKey;

    @Override
    public String getKey() {
        return commandKey;
    }

    @Override
    public void addOption(Option option) {
        options.put(option.getName(), option);
    }

    @Override
    public void addArgument(Argument argument) {

    }

    @Override
    public Option getOption(String option) {
        return options.get(option);
    }

    @Override
    public Argument getArgument(String argument) {
        return arguments.get(argument);
    }

    @Override
    public List<Option> getOptions() {
        return new ArrayList<>(options.values());
    }
}
