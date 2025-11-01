package CodeToMd;

import java.io.File;
import java.io.IOException;
import java.util.*;

import CodeToMd.DirectoryEnum.DirStructEnum;
import CodeToMd.DirectoryEnum.FileExtensionName;
import Tool.Write_File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static Tool.FileProcess.CreateFileName;

/**
 * 主类，用于将代码文件处理为Markdown格式
 */
public class CodeToMD_Main {
    private static final Logger logger = LoggerFactory.getLogger(CodeToMD_Main.class);

    //用一个栈来存储目录结构
    private static Stack<Map<String, Object> >directoryStack = new Stack<>();
    //文件存放位置
    private static File DEFAULT_SOURCE_DIR = new File("C:\\Users\\86158\\OneDrive\\工作仓库\\工作仓库\\Code");


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // 提示用户输入目录路径
        System.out.println("请输入要处理的源代码目录路径:");
        String inputPath = scanner.nextLine().trim();



        File sourceDir = new File(inputPath);
        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
            System.err.println("错误: 指定的路径不存在或不是目录");
            return;
        }

        // 创建md文件存放目录（使用新变量而非修改final常量）
        DEFAULT_SOURCE_DIR = Write_File.createDirectory(DEFAULT_SOURCE_DIR, sourceDir.getName());

        System.out.println("开始处理目录: " + sourceDir.getAbsolutePath());

        CodeToMD_Main codeToMD_Main = new CodeToMD_Main();
        codeToMD_Main.processDirectory(sourceDir);

        System.out.println("处理完成!");
        scanner.close();
    }





    /**
     * 递归处理目录
     * @param currentDirectory 当前要处理的目录
     */
    private void processDirectory(File currentDirectory) throws IOException {


        if (currentDirectory == null || !currentDirectory.isDirectory()) {
            logger.warn("The provided path is not a directory: {}", currentDirectory);
            return;
        }

        File[] entries;
        try {
            entries = currentDirectory.listFiles();
        } catch (SecurityException e) {
            logger.error("Access denied to directory: {}", currentDirectory.getAbsolutePath(), e);
            return;
        }

        if (entries == null) {
            logger.warn("The directory is empty or cannot be accessed: {}", currentDirectory.getAbsolutePath());
            return;
        }

        // 创建目录结构
        Map<String, Object> directoryMap = new HashMap<>();

        // 分别存储子目录和文件
        List<File> subDirectories = new ArrayList<>();
        List<File> files = new ArrayList<>();

        // 将目录项分为目录和文件两类
        for (File entry : entries) {
            if (entry.isDirectory()) {
                subDirectories.add(entry);
            } else {
                files.add(entry);
            }
        }

        //todo:将来可以在这里进行过滤，不需要处理文件和子目录

        // 将当前目录结构入栈

        /*
         *  入栈位置一定要在递归之前
         */
        directoryMap.put(DirStructEnum.CURRENT.getValue(), currentDirectory);
        directoryMap.put(DirStructEnum.SUB.getValue(), subDirectories);
        directoryStack.add(directoryMap);

        // 递归处理所有子目录
        for (File subDirectory : subDirectories) {
            processDirectory(subDirectory);
        }

        // 处理当前目录下的所有文件
        processFiles(files);
    }

    /**
     * 处理文件列表（示例方法）
     * @param codeFiles 文件列表
     */
    private void processFiles(List<File> codeFiles) throws IOException {

        //将当前目录结构出栈
        Map<String, Object> CurrentDirectoryMap = directoryStack.pop();
        //获取上一次目录
        Map<String, Object> ParentDirectoryMap = new HashMap<>();
        if(!directoryStack.isEmpty()) ParentDirectoryMap = directoryStack.peek();
        CodeFilesToMd codeFilesToMd = new CodeFilesToMd(codeFiles, CurrentDirectoryMap, ParentDirectoryMap);

        //md标题名
         File currentDirectory =(File)CurrentDirectoryMap.get(DirStructEnum.CURRENT.getValue());
         String mdTitle = currentDirectory.getName();
        //将代码文件拼接成Markdown
        String md = codeFilesToMd.CodeFilesToMd();

        //创建文件名
        String  FileName = CreateFileName(DEFAULT_SOURCE_DIR, mdTitle, FileExtensionName.MD);

        //创建文件
        Write_File.createFile(DEFAULT_SOURCE_DIR, FileName, md);
        logger.info("Generated Markdown file: {}", mdTitle+".md");


    }



}
