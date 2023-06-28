package com.binturong.cli.core;

/**
 * @author zhulin
 * @date 2023-06-28 9:56
 */
public class Argument {

    private static final String DEFAULT_ARG_NAME = "value";

    /**
     * The argument name used in the usage.
     */
    protected String argName = DEFAULT_ARG_NAME;

    /**
     * The argument description.
     */
    protected String description;

    protected boolean required = true;

    /**
     * The optional default value of this argument.
     */
    protected String defaultValue;

    protected String value;


    private Class<?> type = String.class;

    /**
     * The argument index. Must be positive or null.
     * It is set fo -1 by default to check that the index was set.
     */
    protected int index = -1;

    public String getArgName() {
        return argName;
    }

    public void setArgName(String argName) {
        this.argName = argName;
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

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
