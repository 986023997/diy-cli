package com.binturong.cli.core;

import com.binturong.cli.core.exception.NotSupportCommandException;
import com.binturong.cli.core.handler.CommandHandler;
import com.binturong.cli.core.parse.Parse;


import java.util.*;

/**
 *
 * @author binturong
 * @date 2023-06-25 13:50
 */
public class Cli {

    /** the cli's name */
    private String name = "defaultCli";

    /** command of the cli support */
    private Map<String, CommandDefinition> commands = new HashMap<>();

    /** collection of  CommandHandler  */
    private List<CommandHandler> handlers = new ArrayList<>();

    private Parse parse;

    public Cli(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, CommandDefinition> getCommands() {
        return commands;
    }

    public void setCommands(Map<String, CommandDefinition> commands) {
        this.commands = commands;
    }

    public List<CommandHandler> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<CommandHandler> handlers) {
        this.handlers = handlers;
    }

    public Parse getParse() {
        return parse;
    }

    public void setParse(Parse parse) {
        this.parse = parse;
    }

    public void init() {
        // 初始化
    }

    /**
     * * Parses the user command line interface and create a new {@link CommandLine} containing extracting values.
     *
     * @param arguments the arguments
     * @return the creates command line
     * @throws ParseException
     */
    public String execute(String[] arguments) throws ParseException {
        // 校验
        if (arguments == null || arguments.length == 0) {
            return helpDocument();
        }
        // 1、获取命令，判断是否支持命令的执行
        String commandStr = arguments[0].trim().toLowerCase(Locale.ROOT);
        if (commandStr.isEmpty()) {
            throw new NotSupportCommandException("the command is empty!!");
        }
        CommandDefinition command = commands.get(commandStr);
        if (command == null) {
            throw new NotSupportCommandException(command);
        }
        // 2、解析当前命令为命令行
        CommandLine commandLine = parse.parse(this, command, Arrays.copyOfRange(arguments, 1, arguments.length));
        // 3、遍历执行器，若支持当前名称，则执行命令，并获取输出
        for (CommandHandler handler : handlers) {
            if (handler.support(commandLine.getCommandType())) {
                return handler.handle(commandLine);
            }
        }
        return "";
    }

    private String helpDocument() {
        //     打印帮助
        return "";
    }
}
