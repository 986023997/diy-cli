package com.binturong.cli.core.convert;

/**
 * @author zhulin
 * @date 2023-07-03 16:16
 */
public interface Converter <T> {

    T convert(String source);
}
