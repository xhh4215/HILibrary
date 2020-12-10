package com.example.hilibrary.log.utils;

public class HiStackTraceUtil {
    /***
     * 获取真实的裁剪的堆栈信息
     * @param stackTrace  被裁剪的堆栈信息
     * @param ignorePackage  忽略的报名
     * @param maxDepth  堆栈信息打印的深度
     * @return 获取真实的裁剪的堆栈信息
     */
    public static StackTraceElement[] getCroppedRealStackTrack(StackTraceElement[] stackTrace, String ignorePackage, int maxDepth) {
        return cropStackTrace(getRealStackTrack(stackTrace, ignorePackage), maxDepth);
    }

    /***
     * 裁剪堆栈信息
     * @param callstack  被裁剪的堆栈信息
     * @param maxDepth 堆栈信息打印的深度
     * @return 裁剪堆栈信息
     */
    private static StackTraceElement[] cropStackTrace(StackTraceElement[] callstack, int maxDepth) {
        int realDepth = callstack.length;
        if (maxDepth > 0) {
            realDepth = Math.min(maxDepth, realDepth);
        }
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(callstack, 0, realStack, 0, realDepth);
        return realStack;
    }

    /***
     * 获取除忽略包之外的堆栈信息
     * @param stackTrace  被裁剪的堆栈信息
     * @param ignorePackage 忽略的报名
     * @return 获取除忽略包之外的堆栈信息
     */
    private static StackTraceElement[] getRealStackTrack(StackTraceElement[] stackTrace, String ignorePackage) {
        int ignoreDepth = 0;
        int allDepth = stackTrace.length;
        String className;
        for (int i = allDepth - 1; i >= 0; i--) {
            className = stackTrace[i].getClassName();
            if (ignorePackage != null && className.startsWith(ignorePackage)) {
                ignoreDepth = i + 1;
                break;
            }
        }
        int realDepth = allDepth - ignoreDepth;
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(stackTrace, ignoreDepth, realStack, 0, realDepth);
        return realStack;
    }
}
