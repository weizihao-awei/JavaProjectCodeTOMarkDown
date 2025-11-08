package CodeToMd.DirectoryEnum;

import java.io.File;
import java.util.List;

import static java.util.Arrays.asList;

public  class FilterDirectory {

   public static File parentDir ;

   public static final String childPath = "\\src\\apis";
   public static final String childPath2 = "\\src\\router";
   public static final String childPath3 = "\\src\\views";

   /**
    * 拼接文件，获取绝对路径进行返回
    */
public static List<String> getChildFiles() {
       File childFile1 = new File(parentDir, childPath);
       File childFile2 = new File(parentDir, childPath2);
       File childFile3 = new File(parentDir, childPath3);
       return asList(
           childFile1.getAbsolutePath(),
           childFile2.getAbsolutePath(),
           childFile3.getAbsolutePath()
       );
   }






}
