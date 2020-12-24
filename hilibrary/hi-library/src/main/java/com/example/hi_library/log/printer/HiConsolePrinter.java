package com.example.hi_library.log.printer;

 import android.util.Log;


 import com.example.hi_library.log.common.HiLogConfig;

 import org.jetbrains.annotations.NotNull;

 import static com.example.hi_library.log.common.HiLogConfig.MAX_LEN;


public class HiConsolePrinter implements HiLogPrinter {
    @Override
    public void print(@NotNull HiLogConfig config, int level, String tag, @NotNull String printString) {
        int len = printString.length();
        int countOfSub = len / MAX_LEN;
        if (countOfSub > 0) {
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {
                Log.println(level, tag, printString.substring(index, index + MAX_LEN));
                index += MAX_LEN;
            }
            if (index != len) {
                Log.println(level, tag, printString.substring(index, len));
            }
        }else{
            Log.println(level,tag,printString);
        }
    }
}
