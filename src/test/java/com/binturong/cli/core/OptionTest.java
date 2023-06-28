package com.binturong.cli.core;

import com.binturong.cli.core.parse.DefaultParse;
import org.junit.Test;

import java.util.HashMap;

/**
 * @author zhulin
 * @date 2023-06-25 15:32
 */
public class OptionTest {

    @Test
    public void testBuilderInsufficientParams1() throws ParseException {
        DefaultParse defaultParse = new DefaultParse();
        Option option = new Option("port", "p","port");
        option.setArgCount(1);
        HashMap<String, CommandDefinition> commandHashMap = new HashMap<>();
        CommandDefinition serverCommand = new CommandDefinition("server");
        serverCommand.addOption(option);
        commandHashMap.put("server", serverCommand);
        String[] args = {"server","--port", "8080"};
        Cli cli = new Cli("test");
        cli.setCommands(commandHashMap);
        cli.setParse(defaultParse);
        String execute = cli.execute(args);
        System.out.println(execute);
    }
}
