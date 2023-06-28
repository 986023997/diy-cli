package com.binturong.cli.core.parse;

import com.binturong.cli.core.*;

/**
 * The interface of the command line parser.
 *
 * @author zhulin
 */
public interface Parse {
    CommandLine parse(Cli cli, CommandDefinition commandDefinition, String[] arguments) throws ParseException;

}
