package Tool;

import java.util.regex.Pattern;

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
    /**
     * 检查路径是否匹配指定的字符串
     * @param filePath 原始路径（可以是绝对路径）
     * @param matchString 要匹配的字符串
     * @return 如果路径以指定字符串开头，返回true；否则返回false
     */
    /**
     * 检查路径是否包含指定的字符串
     * @param filePath 原始路径（可以是绝对路径）
     * @param matchString 要匹配的字符串
     * @return 如果路径包含指定字符串，返回true；否则返回false
     */
    public static boolean matchPath(String filePath, String matchString) {
        // 检查传入的参数是否为空
        if (filePath == null || matchString == null) {
            return false;
        }
        //如果 matchString字符长度>filePath也返回true
        if (matchString.length()>filePath.length()){
            return matchString.contains(filePath);
        }
        // 检查 filePath 是否包含 matchString
        return filePath.contains(matchString) || filePath.equals(matchString);
    }

}