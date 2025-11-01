package CodeToMd;

import java.io.File;

public class FileLayerExample {
    public static void main(String[] args) {
        // 创建一个 File 对象
        File file = new File("C:\\Users\\86158\\Desktop\\ob_整理文件测试");

        // 获取文件的绝对路径
        String absolutePath = file.getAbsolutePath();
        System.out.println("文件的绝对路径: " + absolutePath);

        // 获取文件层级（路径深度）
        int layer = getFileLayer(file);
        System.out.println("文件所在层级: " + layer);
    }

    /**
     * 获取文件的层级（路径深度）
     * @param file 文件对象
     * @return 文件层级
     */
    public static int getFileLayer(File file) {
        // 获取文件的绝对路径
        String absolutePath = file.getAbsolutePath();

        // 使用分隔符分割路径，计算层级
        String separator = "\\\\"; // 获取系统的文件分隔符（如 Windows 是 \，Linux 是 /）
        String[] parts = absolutePath.split(separator);

        // 层级等于路径部分的数量减去 1（根目录不算一层）
        return parts.length - 1;
    }
}