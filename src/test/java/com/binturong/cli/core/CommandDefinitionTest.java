package com.binturong.cli.core;

import com.binturong.cli.core.exception.ParseException;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * @author zhulin
 * @date 2023-06-30 14:53
 */
public class CommandDefinitionTest {

    @Test
    public void testBuilderInsufficientParams1() throws ParseException {
        Option option1 = new Option("class", "c","Class",true,true);
        Option option2 = new Option("class", "c","Class",true,true);
        Option option3 = new Option("class1", "c","Class",true,true);
        CommandDefinition TestCommandDefinition = new DefaultCommandDefinition("Test");
        assertThrows(RuntimeException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                TestCommandDefinition.addOption(option1);
                TestCommandDefinition.addOption(option2);
            }
        });


        assertThrows(RuntimeException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                TestCommandDefinition.addOption(option1);
                TestCommandDefinition.addOption(option3);
            }
        });
    }

}
