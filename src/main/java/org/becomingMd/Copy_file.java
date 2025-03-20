package org.becomingMd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 传入两个File对象作为参数，将源文件内容复制到目标文件
 */
public class Copy_file {
    public static void copy_file(File sourceFile, File targetFile) throws IOException {
        FileInputStream fis = new FileInputStream(sourceFile);
        FileOutputStream fos = new FileOutputStream(targetFile);

        int len = 0;
        byte[] bytes = new byte[4096];
        while ((len = fis.read(bytes)) != -1) {
            fos.write(bytes, 0, len);
        }
        fos.close();
        fis.close();
    }

    /**
     * 传入一个List<String>和一个File对象作为参数，将集合中的字符串写入目标文件
     */
    public static void copy_file(List<String> sourceStrings, File targetFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(targetFile);

        for (String str : sourceStrings) {
            fos.write(str.getBytes());
            fos.write(System.lineSeparator().getBytes()); // 添加换行符
        }
        fos.close();
    }
}
