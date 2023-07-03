package com.binturong.cli.core;

import com.binturong.cli.core.exception.ParseException;
import com.binturong.cli.core.handler.CommandHandler;
import com.binturong.cli.core.parse.DefaultParse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author zhulin
 * @date 2023-06-30 15:01
 */
public class ApplicationTest {

    public static class TestCommandHandler implements CommandHandler {

        public static final Logger log = Logger.getLogger("TestCommandHandler.class");

        public TestCommandHandler() {
        }

        @Override
        public boolean support(String commandType) {
            return commandType.equalsIgnoreCase("test");
        }

        @Override
        public String handle(CommandLine commandLine) {
            if (commandLine != null) {
                Command command = commandLine.getCommand();
                List<Option> options = command.getOptions();
                for (Option option : options) {
                    log.info("option:" + option.getName() + " values:" + StringUtils.join(option.getValues(), ",") );
                }
                List<String> arguments = commandLine.allArguments();
                for (int i = 0; i < arguments.size(); i++) {
                    log.info("argument index-" + i + ",value: " + arguments.get(i));
                }
                log.info("command exec done");
                return "";
            }
            return null;
        }
    }



    @Test
    public void testBuilderInsufficientParams1() throws ParseException {
        DefaultParse defaultParse = new DefaultParse();
        Option classOption = new Option("class", "c","Class",true,true);
        Option methodOption = new Option("method", "m","Method",true,true);
        HashMap<String, CommandDefinition> commandHashMap = new HashMap<>();
        CommandDefinition TestCommandDefinition = new DefaultCommandDefinition("Test");
        TestCommandDefinition.addOption(classOption);
        TestCommandDefinition.addOption(methodOption);
        commandHashMap.put("test", TestCommandDefinition);
        String[] args = {"Test","--class", "com.binturong.cli.core.OptionTest", "-m","testBuilderInsufficientParams1", "arg1","arg2"};
        Cli cli = new Cli("test");
        cli.setCommands(commandHashMap);
        cli.setParse(defaultParse);
        ArrayList<CommandHandler> handlers = new ArrayList<>();
        handlers.add(new TestCommandHandler());
        cli.setHandlers(handlers);
        String execute = cli.execute(args);
        System.out.println(execute);
    }
}
