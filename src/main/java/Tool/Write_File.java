package Tool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Write_File {

    /**
     * 创建指定名称的目录
     *
     * @param dir 父目录文件对象
     * @param DirectoryName 要创建的目录名称
     * @return 返回创建的目录文件对象
     */
    public static File createDirectory(File dir, String DirectoryName) {

        File directory = new File(dir, DirectoryName);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;

    }
    /**
     * 创建指定名称的文件
     *
     * @param dir 父目录文件对象
     * @param fileName 要创建的文件名称
     * @return 创建的文件对象
     */
    public static void createFile(File dir, String fileName, String content) {

        File file = new File(dir, fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
