package Tool;

import CodeToMd.DirectoryEnum.FileExtensionName;

import java.io.File;

import java.util.HashMap;

import java.util.Map;

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


}
