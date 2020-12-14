package com.example.hi_library.log.utils;

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
        //被裁剪的堆栈信息的长度
        int realDepth = callstack.length;
        if (maxDepth > 0) {
            //获取堆栈信息的真实长度
            realDepth = Math.min(maxDepth, realDepth);
        }
        //创建一个存储堆栈信息的数组
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        //copy指定大小的堆栈信息到创建的新的数组中
        System.arraycopy(callstack, 0, realStack, 0, realDepth);
        //返回存储拷贝信息的数组
        return realStack;
    }

    /***
     * 获取除忽略包之外的堆栈信息
     * @param stackTrace  被裁剪的堆栈信息
     * @param ignorePackage 忽略的报名
     * @return 获取除忽略包之外的堆栈信息
     */
    private static StackTraceElement[] getRealStackTrack(StackTraceElement[] stackTrace, String ignorePackage) {
        //忽略的深度
        int ignoreDepth = 0;
        //原始的堆栈信息的长度
        int allDepth = stackTrace.length;
        String className;
        for (int i = allDepth - 1; i >= 0; i--) {
            className = stackTrace[i].getClassName();
            if (ignorePackage != null && className.startsWith(ignorePackage)) {
                ignoreDepth = i + 1;
                break;
            }
        }
        //获取真实的深度
        int realDepth = allDepth - ignoreDepth;
        //创建真实深度大小的数组
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        //拷贝数据
        System.arraycopy(stackTrace, ignoreDepth, realStack, 0, realDepth);
        return realStack;
    }
}
