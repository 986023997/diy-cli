package com.binturong.cli.core;

/**
 * @author zhulin
 * @date 2023-06-25 17:19
 */
public class OptionValidator {

    public static String validOption(String option) {
        if (option == null) {
            return null;
        }

        // handle the single character opt
        if (option.length() == 1) {
            final char ch = option.charAt(0);

            if (!isValidOpt(ch)) {
                throw new IllegalArgumentException("Illegal option name '" + ch + "'");
            }
        } else {
            // handle the multi character opt
            for (final char ch : option.toCharArray()) {
                if (!isValidChar(ch)) {
                    throw new IllegalArgumentException("The option '" + option + "' contains an illegal " + "character : '" + ch + "'");
                }
            }
        }
        return option;
    }

    private static boolean isValidOpt(final char c) {
        return isValidChar(c) || c == '?' || c == '@';
    }

    private static boolean isValidChar(char c) {
        return Character.isJavaIdentifierStart(c);
    }
}
