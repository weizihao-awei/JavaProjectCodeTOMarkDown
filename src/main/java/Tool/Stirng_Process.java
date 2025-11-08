package Tool;

/**
 * @BelongsProject: SourceCode_become_md
 * @BelongsPackage: Tool
 * @Author: ykw-weizihao
 * @CreateTime: 2025-11-08 16:00
 * @Description: TODO
 * @Version: 1.0
 */
public class Stirng_Process {
    //静态方法，判断给出文件地址字符串是携带双引号，如果有就去掉
    public static String removeDoubleQuotes(String filePath) {
        return filePath.replace("\"", "");
    }
}