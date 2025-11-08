package Tool;

import CodeToMd.DirectoryEnum.CodeTypeEnum;
import CodeToMd.DirectoryEnum.FileExtensionName;
import CodeToMd.DirectoryEnum.FilterDirectory;

import CodeToMd.DirectoryEnum.StaticField;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileProcess {

    private static final Map<String,Map<String,Integer> > dirFileNameMap=new HashMap<>();
    /**
     * 获取文件扩展名
     */
   public static String getFileExtension(File file) {
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return "";

    }
/**
 * @description: 判断当前目录写有没有同名文件，如果有同名文件名字加1后作为文件名返回
 * @author: yekunwei
 * @date: 2025/11/1 11:15
 * @param: [dir, fileName, fileType]
 * @return: java.lang.String 返回重命名后的文件名
 **/
public static String CreateFileName(File dir, String fileName, FileExtensionName fileType){
    //获取dir绝对路径，看没有在dirFileNameMap中存在
    String dirPath = dir.getAbsolutePath();
    Map<String, Integer> FileNameMap;
    //先判断是否存在dir在文件名映射表中
    if (!dirFileNameMap.containsKey(dirPath)) {
        //不存在，创建一个空的文件名映射表
        FileNameMap = new HashMap<>();
        //将dirPath作为键，FileNameMap作为值，保存到文件名映射表中
        dirFileNameMap.put(dirPath, FileNameMap);
    }
    //Map存的是引用，所以改变FileNameMap，就会改变dirFileNameMap里的元素，这是同步的
    FileNameMap = dirFileNameMap.get(dirPath);

       //拼接文件名和后缀名
       String fileNameWithExtension ;
       // 判断文件名是否已经存在,使用这种if方式是为了优化性能，避免存在多个重命名要多次访问硬盘继续判断，让同一种同名只要判断一次就够了
       //所以下面还是要进行同名判断的，这是为了保存这个方法复用性，可以在及时要写入包下有别人同名文件也能发现，而不是覆盖
       if (FileNameMap.containsKey(fileName)) {
           //说明文件存在，取出键值加1，作为文件名
           int count = FileNameMap.get(fileName);
           count++;
           //更新键值
           FileNameMap.put(fileName, count);
           //更新文件名
           fileNameWithExtension = fileName + count + fileType.getExtension();
           //返回重命名文件名，return fileNameWithExtension;
           return fileNameWithExtension;

       }
       // 判断当前目录下，用没有同名文件
        fileNameWithExtension = fileName + fileType.getExtension();
       File file = new File(dir, fileNameWithExtension);
       Boolean isSameFileName = iSameFilename(file);
       //如果存在同名文件，就重命名，并且保留文件名到文件名映射表中
       if (isSameFileName) {
           //先保留文件名
           FileNameMap.put(fileName, 1);
           //返回拼接数字的文件名
           fileNameWithExtension = fileName + 1 + fileType.getExtension();
           return fileNameWithExtension;

      }else {
           //不存在同名文件，直接返回文件名
           //先保留文件名
           FileNameMap.put(fileName, 0);
           return fileNameWithExtension;
       }

    }
/**
 * 判断文件是否存在
 */
    public static Boolean iSameFilename(File fileNameWithExtension){
       return fileNameWithExtension.exists();
    }

    //过滤掉不需要的目录名字 .开头的目录
    public static List<File> filterDirName(List<File> files) {
        List<File> filteredFiles = filterOutHiddenDirectories( files);
         //判断是否打开前端转md模式
        if (StaticField.isOpenFrontend) {
            filteredFiles = filterFrontendDirectories(filteredFiles);
        }

        return filteredFiles;
    }
    /**
     * 保留前端项目中的特定目录
     * @param files 待过滤的文件列表
     * @return 过滤后的文件列表
     */
    public static List<File> filterFrontendDirectories(List<File> files) {

        List<File> result = new ArrayList<>();
        //filterDirectory文件 是要进行保留的
        List<String> filterDirectoryList = FilterDirectory.getChildFiles();
        for (File file : files) {
            //应该保留的文件
            boolean shouldSave = false;
            String absolutePath = file.getAbsolutePath();
            //获取所有文件不需要过滤文件
            for (String filterDirectory : filterDirectoryList) {
                if (Stirng_Process.matchPath(absolutePath, filterDirectory)) {
                    shouldSave = true;
                    break;
                }
            }

            if (shouldSave) {
                result.add(file);
            }
        }
        return result;
    }

    /**
     * 过滤掉以点(.)开头的隐藏目录
     * @param files 待过滤的文件列表
     * @return 过滤后的文件列表
     */
    public static List<File> filterOutHiddenDirectories(List<File> files) {
        return files.stream()
                .filter(file -> !file.getName().startsWith("."))
                .collect(Collectors.toList());
    }




    /**
     * 描述：过滤出代码文件
     */
    public static   List<File> filterCodeFiles(List<File> files) {
        List<File> codeFiles = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                String extension = getFileExtension(file);
                if (CodeTypeEnum.contains(extension)) {
                    codeFiles.add(file);
                }
            }
        }
        return codeFiles;
    }


}
