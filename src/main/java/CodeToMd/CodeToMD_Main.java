package CodeToMd;

import java.io.File;
import java.io.IOException;
import java.util.*;


import CodeToMd.DirectoryEnum.FileExtensionName;
import Tool.ListTreeDir;
import Tool.TreeNode;
import Tool.Write_File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static Tool.FileProcess.CreateFileName;

/**
 * 主类，用于将代码文件处理为Markdown格式
 */
public class CodeToMD_Main {
    private static final Logger logger = LoggerFactory.getLogger(CodeToMD_Main.class);


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
     * @param rootDirectory 当前要处理的目录
     */
    private void processDirectory(File rootDirectory) throws IOException {


        if (rootDirectory == null || !rootDirectory.isDirectory()) {
            logger.warn("The provided path is not a directory: {}", rootDirectory);
            return;
        }
        //构建目录树,返回根节点
        TreeNode treeNode = TreeNode.build(rootDirectory);
        //将所有目录节点装入逻辑栈列
        ListTreeDir listTreeDir = new ListTreeDir(treeNode);
        //遍历目录树
        while (!listTreeDir.isEmpty()) {
            TreeNode node = listTreeDir.pop();
            processCodeFilesToMd( node);
        }

    }

    /**
     * 处理文件列表（示例方法）
     * @param node 文件列表
     */
    private void processCodeFilesToMd(TreeNode node) throws IOException {
        CodeFilesToMd codeFilesToMd = new CodeFilesToMd(node);
        //md标题名
         String mdTitle = node.getDirName();
        //将代码文件拼接成Markdown
        String content_Md = codeFilesToMd.CodeFilesToMd();

        //创建文件名
        String  FileName = CreateFileName(DEFAULT_SOURCE_DIR, mdTitle, FileExtensionName.MD);

        //创建文件
        Write_File.createFile(DEFAULT_SOURCE_DIR, FileName, content_Md);
        logger.info("Generated Markdown file: {}", mdTitle+".md");


    }



}
