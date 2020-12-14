package com.example.hi_library.log.formatter;

public interface HiLogFormatter<T> {
    String format(T data);
}
