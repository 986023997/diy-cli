package com.binturong.cli.core;

/**
 * @author zhulin
 * @date 2023-06-25 17:02
 */
public class ParseUtil {
    public static String stripLeadingHyphens(String token) {
        if (token.startsWith("--")) {
            return token.substring(2);
        }
        if (token.startsWith("-")) {
            return token.substring(1);
        }
        return token;
    }

    public static String stripLeadingAndTrailingQuotes(String str) {
        final int length = str.length();
        if (length > 1 && str.startsWith("\"") && str.endsWith("\"") && str.substring(1, length - 1).indexOf('"') == -1) {
            str = str.substring(1, length - 1);
        }
        return str;
    }
}
