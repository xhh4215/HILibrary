package com.example.hi_library.log.formatter;

public class HiThreadFormatter implements HiLogFormatter<Thread> {
    @Override
    public String format(Thread data) {
        return "Thread" + data.getName();
    }
}
