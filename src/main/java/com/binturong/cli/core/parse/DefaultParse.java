package com.binturong.cli.core.parse;

import com.binturong.cli.core.*;
import com.binturong.cli.core.exception.MissingOptionException;
import com.binturong.cli.core.exception.NotSupportOptionException;
import com.binturong.cli.core.exception.ParseException;
import java.util.List;


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


        public static boolean canAcceptOptionArgument(String token) {
            Option option = currentOption.get();
            if (!option.acceptsArg()) return false;
            CommandDefinition commandDefinition = currentCommandDefinition.get();
            return !commandDefinition.isOption(ParseUtil.stripLeadingHyphens(token));
        }


        public static void startOption(Option option) {
            if (currentOption.get() != null) {
                throw new ParseException("cannot start one option before the resolution of another is complete");
            }
            currentOption.set(option);
        }


        public static void endOption() {
            if (currentOption != null) {
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
        DefaultCommand defaultCommand = new DefaultCommand(commandDefinition.getKey());
        CommandLine commandLine = new CommandLine(cli, defaultCommand);
        Lexer.currentCommandLine.set(commandLine);
        processParse(arguments);
        //  校验
        checkRequiredOptions(commandLine, commandDefinition);
        return commandLine;
    }


    private void processParse(String[] arguments) {
       //  遍历所有的token
       //  如果是以“--” 和 “-” 开头说明当前是选项,选项可能存在参数，需要继续遍历直到读取完所有的参数，当前的选项才算读取完成，
        //  若当前的参数未读取完成，这时候读取到了其他的选项这个就抛出异常，因为这样子就无法判断了，只能是读取完当前选项的所有参数才能处理
       // 非选项的一律当作命令的参数
       if (arguments != null) {
           for (String token : arguments) {
               Lexer.currentToken.set(token);
               if (Lexer.currentOption.get() != null && Lexer.canAcceptOptionArgument(token)) {
                   handleOptionArgument(token);
               } else if (token.startsWith("--")) {
                   handleLongOption(token);
               } else if (token.startsWith("-") && !"-".equals(token)) {
                   handleShortOption(token);
               } else {
                   handleArgument(token);
               }
               Option currentOption = Lexer.currentOption.get();
               if (currentOption != null && !currentOption.acceptsArg()) {
                   Lexer.endOption();
               }
           }
       }
    }

    private void handleOptionArgument(String token) {
        Option option = Lexer.currentOption.get();
        option.addValue(ParseUtil.stripLeadingAndTrailingQuotes(token));
    }

    private void handleArgument(String token) {
        CommandLine commandLine = Lexer.currentCommandLine.get();
        commandLine.addArgument(token);
    }

    /**
     * Handles the following tokens:
     * <p/>
     * -S
     * -S V
     * -S=V
     * <p/>
     *
     * @param token the command line token to handle
     */
    private void handleShortOption(String token) {
        if (token.indexOf('=') == -1) {
            handleShortOptionWithoutEqual(token);
        } else {
            handleShortOptionWithEqual(token);
        }
    }

    private void handleShortOptionWithEqual(String token) {
        final int pos = token.indexOf('=');
        final String value = token.substring(pos + 1);
        String opt = token.substring(0, pos);
        CommandDefinition commandDefinition = Lexer.currentCommandDefinition.get();
        Option option = commandDefinition.getShortOption(ParseUtil.stripLeadingHyphens(opt));
        if (option != null) {
            Option optionClone = null;
            try {
                optionClone = option.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                throw new ParseException(e.getMessage());
            }
            Lexer.startOption(optionClone);
            CommandLine commandLine = Lexer.currentCommandLine.get();
            Command command = commandLine.getCommand();
            optionClone.addValue(value);
            command.addOption(optionClone);
        } else {
            handleUnknownToken(Lexer.currentToken.get());
        }
    }

    private void handleShortOptionWithoutEqual(String token) {
        CommandDefinition commandDefinition = Lexer.currentCommandDefinition.get();
        token = ParseUtil.stripLeadingHyphens(token);
        Option option = commandDefinition.getShortOption(token);
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
            handleUnknownToken(Lexer.currentToken.get());
        }
    }




    /**
     * Handles the following tokens:
     *
     * --L --L=V --L V
     *
     * @param token the command line token to handle
     */
    private void handleLongOption(String token) {
        if (token.indexOf('=') == -1) {
            handleLongOptionWithoutEqual(token);
        } else {
            handleLongOptionWithEqual(token);
        }
    }

    /**
     * Handles the following tokens:
     *
     * --L=V --l=V
     *
     * @param token the command line token to handle
     */
    private void handleLongOptionWithEqual(String token) {
        final int pos = token.indexOf('=');
        final String value = token.substring(pos + 1);
        String opt = token.substring(0, pos);
        CommandDefinition commandDefinition = Lexer.currentCommandDefinition.get();
        Option option = commandDefinition.getLongOption(ParseUtil.stripLeadingHyphens(opt));
        if (option != null) {
            Option optionClone = null;
            try {
                optionClone = option.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                throw new ParseException(e.getMessage());
            }
            Lexer.startOption(optionClone);
            CommandLine commandLine = Lexer.currentCommandLine.get();
            Command command = commandLine.getCommand();
            optionClone.addValue(value);
            command.addOption(optionClone);
        } else {
            handleUnknownToken(Lexer.currentToken.get());
        }
    }


    /**
     * Handles the following tokens:
     *
     * --L --l
     *
     * @param token the command line token to handle
     */
    private void handleLongOptionWithoutEqual(String token) {
        CommandDefinition commandDefinition = Lexer.currentCommandDefinition.get();
        token = ParseUtil.stripLeadingHyphens(token);
        Option option = commandDefinition.getLongOption(token);
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
            handleUnknownToken(Lexer.currentToken.get());
        }
    }


    private void checkRequiredOptions(CommandLine commandLine, CommandDefinition commandDefinition) {
        Command command = commandLine.getCommand();
        List<Option> requireOptions = commandDefinition.getRequireOptions();
        for (Option requireOption : requireOptions) {
          if (requireOption.isRequired()) {
              Option option = command.getOption(requireOption.getName());
              if (option == null) {
                  throw new MissingOptionException("the " +requireOption.getName() + " option is required");
              }
          }
        }
    }


    private void handleUnknownToken(final String token)  {
        throw new NotSupportOptionException(String.format("%s is not support", token));
    }


}

