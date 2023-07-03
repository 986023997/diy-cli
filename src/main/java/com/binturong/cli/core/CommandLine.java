package com.binturong.cli.core;

import com.binturong.cli.core.exception.NotSupportCommandException;

import java.io.Serializable;
import java.util.*;

/**
 * @author zhulin
 * @date 2023-06-25 13:54
 */
public class CommandLine implements Serializable {

    private static final long serialVersionUID = 1L;

    private Cli cli;

    /** This command corresponds to the current command line  */
    private Command command;

    private List<String> arguments = new ArrayList<>();


    public CommandLine(Cli cli,Command command) {
        this.cli = cli;
        this.command = command;
    }

    public String getCommandType() {
        if (command != null) {
            return command.getKey();
        } else {
            throw new NotSupportCommandException();
        }
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void addArgument(String token) {
        arguments.add(token);
    }


    /**
     * @return the model of this command line object.
     */
    public Cli cli() {
        return cli;
    }

    /**
     * @return the ordered list of arguments. Arguments are command line arguments not matching an option.
     */
    List<String> allArguments() {
        return arguments;
    }


    /**
     * Gets the raw value of the given option. Raw values are the values as given in the user command line.
     *
     * @param option the option
     * @return the value, {@code null} if none.
     */
    String getRawValueForOption(String option) {
        Option inputOption = command.getOption(option);
        if (inputOption != null) {
            return StringUtils.join(inputOption.getValues(), ",");
        }
        return null;
    }


    /**
     * Gets the raw value of the given option. Raw values are the values as given in the user command line.
     *
     * @param index the index
     * @return the value, {@code null} if none.
     */
    String getRawValueForArgument(int index) {
        return arguments.get(index);
    }
}
