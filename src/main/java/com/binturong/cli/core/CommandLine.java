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
}
