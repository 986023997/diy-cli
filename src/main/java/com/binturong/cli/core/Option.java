package com.binturong.cli.core;

import com.binturong.cli.core.exception.CliException;
import com.binturong.cli.core.exception.MissingOptionException;

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

    private static final int UNINITIALIZED = -1;
    private static final int NOT_LIMITED = -2;

    /** Name of option */
    private String name;
    /** Name of option */
    private String shortName;
    /** description of option */
    private String description;

    /**
     * whether or not the option is required. A mandatory not set throws a {@link MissingOptionException}.
     */
    private boolean required;

    /** The type of this Option */
    private Class<?> type = String.class;

    private List<String> values = new ArrayList<>();

    private int argCount = UNINITIALIZED;

    public Option(String name, String shortName, String description, boolean required, boolean hasArg) {
        this.name = name;
        this.shortName = shortName;
        this.description = description;
        this.required = required;
        if (hasArg) {
            argCount = 1;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public int getArgCount() {
        return argCount;
    }

    public void setArgCount(int argCount) {
        this.argCount = argCount;
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

    public boolean acceptsArg() {
        return hasArg() && values.size() < argCount;
    }

    public boolean hasArg() {
        return argCount != UNINITIALIZED && (argCount > 0 || argCount == NOT_LIMITED);
    }

    public void addValue(String value) {
        if (!acceptsArg()) {
            throw new RuntimeException("the argument value is limited");
        }
        this.getValues().add(value);
    }
}
