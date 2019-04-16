package com.henley.wxcallback.utils

/**
 * File operation utility class
 *
 * @author liyunlong
 * @date 2019/4/8 9:34
 */
final class FileOperation {

    private static final int BUFFER_SIZE = 16384

    final static boolean deleteFile(File file) {
        if (file == null) {
            return true
        }
        if (file.exists()) {
            return file.delete()
        }
        return true
    }

    final static boolean deleteDir(File file) {
        if (file == null || (!file.exists())) {
            return false
        }
        if (file.isFile()) {
            file.delete()
        } else if (file.isDirectory()) {
            File[] files = file.listFiles()
            for (File f : files) {
                deleteDir(f)
            }
        }
        file.delete()
        return true
    }

    final static void copyResourceUsingStream(String name, File dest) throws IOException {
        FileOutputStream os = null
        File parent = dest.getParentFile()
        if (parent != null && (!parent.exists())) {
            parent.mkdirs()
        }
        InputStream is = null

        try {
            is = FileOperation.class.getResourceAsStream("/" + name)
            os = new FileOutputStream(dest, false)

            byte[] buffer = new byte[BUFFER_SIZE]
            int length
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length)
            }
        } finally {
            StreamUtil.closeQuietly(os, is)
        }
    }

    final static void copyFileUsingStream(File source, File dest) throws IOException {
        FileInputStream is = null
        FileOutputStream os = null
        File parent = dest.getParentFile()
        if (parent != null && (!parent.exists())) {
            parent.mkdirs()
        }
        try {
            is = new FileInputStream(source)
            os = new FileOutputStream(dest, false)

            byte[] buffer = new byte[BUFFER_SIZE]
            int length
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length)
            }
        } finally {
            StreamUtil.closeQuietly(os, is)
        }
    }

}
