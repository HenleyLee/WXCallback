package com.henley.wxcallback.utils

/**
 * IO stream utility class
 *
 * @author liyunlong
 * @date 2019/4/8 9:38
 */
final class StreamUtil {

    /**
     * 安静关闭{@link Closeable}
     */
    final static void closeQuietly(Closeable... closeables) {
        if (closeables == null || closeables.length <= 0) {
            return
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close()
                } catch (IOException ignored) {
                }
            }
        }
    }

}
