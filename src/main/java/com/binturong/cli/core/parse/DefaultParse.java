package com.binturong.cli.core.parse;

import com.binturong.cli.core.*;
import com.binturong.cli.core.exception.NotSupportOptionException;

import java.util.HashMap;
import java.util.Map;


/**
 * The default implementation of the command line parser.
 *
 * @author zhulin
 */
public class DefaultParse implements Parse {

    static class Lexer {

        public static ThreadLocal<CommandDefinition> currentCommandDefinition = new ThreadLocal<>();

        public static ThreadLocal<CommandLine> currentCommandLine = new ThreadLocal<>();

        public static ThreadLocal<Option> currentOption= new ThreadLocal<>();

        public static ThreadLocal<Integer> currentOptionArgumentPos =  new ThreadLocal<>();

        public static ThreadLocal<String> currentToken = new ThreadLocal<>();

        public static ThreadLocal<Command> currentCommand = new ThreadLocal<>();

        public static ThreadLocal<Integer> currentCommandArgumentNum =  new ThreadLocal<>();


        public static void reset() {
            currentOption.remove();
            currentToken.remove();
            currentCommand.remove();
            currentOptionArgumentPos.remove(); ;
            currentCommandArgumentNum.remove();
        }


        public static boolean hasOptionArgument() {
            Option option = currentOption.get();
            if (option == null) return false;
            Integer optionArgumentPos = currentOptionArgumentPos.get();
            if (optionArgumentPos == null) {
                optionArgumentPos = 0;
                currentOptionArgumentPos.set(optionArgumentPos);
            }

            return optionArgumentPos < option.getArgCount();
        }


        public static void proceeOptionArgument() {

        }


        public static void startOption(Option option) {
            currentOption.set(option);
        }


        public static Option getCurrentOption() {
            return currentOption.get();
        }


        public static void endOption() {
            if (currentOption != null && !hasOptionArgument()) {
                currentOption.remove();
                currentOptionArgumentPos.remove();
            }
        }
    }

    @Override
    public CommandLine parse(Cli cli, CommandDefinition commandDefinition, String[] arguments) throws ParseException {
        //  解析选项、参数等
        Lexer.reset();
        Lexer.currentCommandDefinition.set(commandDefinition);
        // Object clone = command.clone();
        DefaultCommand defaultCommand = new DefaultCommand(commandDefinition.getKey());
        CommandLine commandLine = new CommandLine(cli, defaultCommand);
        Lexer.currentCommandLine.set(commandLine);
        processParse(commandLine, arguments);
        //  校验
        checkRequiredOptions(commandLine);
        return commandLine;
    }


    private void processParse(CommandLine commandLine, String[] arguments) {
       //  遍历所有的token
       //  如果是以“--” 和 “-” 开头说明当前是选项,选项可能存在参数，需要继续遍历直到读取完所有的参数，当前的选项才算读取完成，
        //  若当前的参数未读取完成，这时候读取到了其他的选项这个就抛出异常，因为这样子就无法判断了，只能是读取完当前选项的所有参数才能处理
       // 非选项的一律当作命令的参数

       if (arguments != null) {
           for (String token : arguments) {
               Lexer.currentToken.set(token);
               if (Lexer.currentOption != null && Lexer.hasOptionArgument()) {
                   handleOptionArgument(token);
               } else if (token.startsWith("--")) {
                   handleLongOption(token);
               } else if (token.startsWith("-") && !"-".equals(token)) {
                   handleShortAndLongOption(token);
               } else {
                   handleArgument(token);
               }

           }
       }
    }

    private void handleOptionArgument(String token) {
        int pos = token.indexOf("=");
        if (pos == -1) {
            Option option = Lexer.currentOption.get();
            Map<String, Argument> argumentMap = option.getArgumentMap();
            if (argumentMap == null) {
                argumentMap = new HashMap<>();
            }
            Argument argument = new Argument();
            argument.setValue(token);
            argument.setArgName(Lexer.currentOptionArgumentPos.get() + "");
            option.addArgument(argument);
        } else {
        }
    }

    private void handleArgument(String token) {

    }

    private void handleShortAndLongOption(String token) {

    }

    private void handleLongOption(String token) {
        CommandDefinition commandDefinition = Lexer.currentCommandDefinition.get();
        token = ParseUtil.stripLeadingHyphens(token);
        Option option = commandDefinition.getOption(token);
        if (option != null) {
            Option optionClone = null;
            try {
                optionClone = option.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            Lexer.startOption(optionClone);
            CommandLine commandLine = Lexer.currentCommandLine.get();
            Command command = commandLine.getCommand();
            command.addOption(optionClone);
        } else {
            throw new NotSupportOptionException(String.format("%s is not support", token));
        }
    }



    private void checkRequiredOptions(CommandLine commandLine) {

    }





}

