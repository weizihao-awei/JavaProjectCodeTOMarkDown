package CodeToMd;

import CodeToMd.DirectoryEnum.CodeTypeEnum;
import CodeToMd.DirectoryEnum.DirStructEnum;
import Tool.Read_File;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Tool.FileProcess.getFileExtension;

/**
 * 描述：将代码文件处理为Markdown格式
 */
public class CodeFilesToMd {
    // 存储代码文件
    List<File> CodeFiles;
    // 当前的目录
   Map<String, Object> CurrentDirectory;
    // 父级目录 这个目录可能是空的，要进行判断
    Map<String, Object> ParentDirectory;

    public CodeFilesToMd(List<File> CodeFiles, Map<String, Object> CurrentDirectory, Map<String, Object> ParentDirectory) {
        this.CodeFiles = CodeFiles;
        this.CurrentDirectory = CurrentDirectory;
        this.ParentDirectory = ParentDirectory;
    }

  public String CodeFilesToMd() throws IOException {
         // 过滤出代码文件
        CodeFiles = filterCodeFiles(CodeFiles);
        // todo: 将来有需要的话，还可以根据文件大小，日期、文件内容进行过滤

        // 拼接导航栏和代码文件
      StringBuilder md = new StringBuilder();
      md.append(md_Navigation());
      md.append("\n");
      md.append("\n");
      md.append(md_CodeFiles());
      return md.toString();


    }
  /**
   * 描述：过滤出代码文件
   */
   private List<File> filterCodeFiles(List<File> files) {
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
    /**
     *  拼接笔记导航栏
     *  @return 返回拼接好的导航栏字符串构建器
     */
   private  StringBuilder md_Navigation(){
       StringBuilder md = new StringBuilder();
       md.append("## 笔记导航栏");
       md.append("\n");
       md.append("\n");
       md.append("当前笔记代码包在电脑位置：");
       md.append("\n");

       // 获取当前目录
       File Currentfile =(File)CurrentDirectory.get(DirStructEnum.CURRENT.getValue());
       md.append("file:///"+Currentfile.getAbsolutePath());
       md.append("\n");
       md.append("\n");
       // 先判断父级目录是否为空
       if (!ParentDirectory.isEmpty()) {
           File file = (File)ParentDirectory.get(DirStructEnum.CURRENT.getValue());
           md.append("- 返回上级目录：[["+file.getName()+"]]");
           md.append("\n");
           md.append("- 同级目录:");
           md.append("\n");
           for (File file1 : (List<File>)ParentDirectory.get(DirStructEnum.SUB.getValue())) {
               md.append("  - [["+file1.getName()+"]]");
               md.append("\n");
           }

       }
       md.append("- 下级目录");
       md.append("\n");
       for (File file : (List<File>)CurrentDirectory.get(DirStructEnum.SUB.getValue())) {
           md.append("  - [["+file.getName()+"]]");
           md.append("\n");
       }
       return md;

   }
   /**
   *  拼接代码文件
   *  @return 拼接好的代码文件字符串构建器
   */
   private StringBuilder md_CodeFiles() throws IOException {
       StringBuilder Code_md = new StringBuilder();
       Code_md.append("## 代码");
       for (File file : CodeFiles){
           Code_md.append("\n");
           Code_md.append("\n");
           Code_md.append("### "+file.getName());
           Code_md.append("\n");
           Code_md.append("\n");
           Code_md.append("```"+getFileExtension(file)+"\n");
           String code = Read_File.read_md_String( file);
           Code_md.append(code);
           Code_md.append("\n");
           Code_md.append("```");
       }
       return Code_md;
   }

}
