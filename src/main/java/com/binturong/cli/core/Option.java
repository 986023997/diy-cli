package com.binturong.cli.core;

import com.binturong.cli.core.exception.CliException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhulin
 * @date 2023-06-25 13:53
 */
public class Option implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;
    /** Name of option */
    private String name;
    /** Name of option */
    private String shortName;
    /** description of option */
    private String description;
    /** 是否必须 */
    private boolean required = false;

    /**
     * 参数
     */
    private Map<String,Argument> argumentMap = new HashMap<>();
    private int argCount = 0;

    public Option(String name, String description) {
        this.name = OptionValidator.validOption(name);
        this.description = description;
    }


    public Option(String name, String shortName, String description) {
        this.name = OptionValidator.validOption(name);
        this.shortName = OptionValidator.validOption(shortName);
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Option setName(String name) {
        this.name = name;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public Option setShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Option setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isRequired() {
        return required;
    }

    public Option setRequired(boolean required) {
        this.required = required;
        return this;
    }

    public int getArgCount() {
        return argCount;
    }


    @Override
    public Option clone() throws CloneNotSupportedException {
        try {
            final Option option = (Option) super.clone();
            return option;
        } catch (final CloneNotSupportedException cnse) {
            throw new RuntimeException("A CloneNotSupportedException was thrown: " + cnse.getMessage());
        }
    }

    public Map<String, Argument> getArgumentMap() {
        return argumentMap;
    }

    public Option setArgumentMap(Map<String, Argument> argumentMap) {
        this.argumentMap = argumentMap;
        return this;
    }


    public Option addArgument(Argument argument) {
       if (argument == null) {
           throw new CliException("argument can ben null");
       }
        String argName = argument.getArgName();
        if (StringUtils.isEmpty(argName)) {
            argName = argCount++ + "";
        }
        argument.setArgName(argName);
        argumentMap.put(argName,argument);
        return this;
    }

    public boolean acceptsArg() {
        return argumentMap != null && argumentMap.size() > 0;
    }


    public void setArgCount(int argCount) {
        this.argCount = argCount;
    }
}
