package com.henley.wxcallback.utils
/**
 * Simplify the message print.
 *
 * @author liyunlong
 * @date 2019/4/9 9:32
 */
final class Logger {

    private static final String TAG = Consts.PREFIX_OF_LOGGER

    final static void info(String message) {
        System.out.println(TAG + message)
    }

    final static void info(String message, Object... objects) {
        System.out.println(TAG + String.format(message, objects))
    }

    final static void info(String message, Throwable error) {
        System.out.println(TAG + message, "An exception is encountered, [" + error.getMessage() + "]" + "\n" + formatStackTrace(error.getStackTrace()))
    }

    final static void error(String message) {
        System.err.println(TAG + message)
    }

    final static void error(String message, Object... objects) {
        System.err.println(TAG + String.format(message, objects))
    }

    final static void error(String message, Throwable error) {
        System.err.println(TAG + message, "An exception is encountered, [" + error.getMessage() + "]" + "\n" + formatStackTrace(error.getStackTrace()))
    }

    private static String formatStackTrace(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder()
        for (StackTraceElement element : stackTrace) {
            sb.append("    at ").append(element.toString())
            sb.append("\n")
        }
        return sb.toString()
    }

}
