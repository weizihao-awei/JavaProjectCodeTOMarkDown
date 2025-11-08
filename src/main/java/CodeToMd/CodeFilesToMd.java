package CodeToMd;


import Tool.Read_File;
import Tool.TreeNode;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static Tool.FileProcess.getFileExtension;

/**
 * 描述：将代码文件处理为Markdown格式
 */
public class CodeFilesToMd {
    // 存储代码文件
    List<File> CodeFiles;
    // 存储当前目录结构
    TreeNode node;



    public CodeFilesToMd(TreeNode node) {
        this.CodeFiles = node.getChildrenFile();
        this.node = node;


    }


    public String CodeFilesToMd(Integer mode) throws IOException {

        // todo: 将来有需要的话，还可以根据文件大小，日期、文件内容进行过滤

        // 拼接导航栏和代码文件
        StringBuilder md = new StringBuilder();
        switch (mode) {
            case 1:
                md.append(md_Navigation_StandardLink());
                break;
            case 2:
                md.append(md_Navigation_Obsidian());
                break;
            default:
                // 默认使用标准md格式
                md.append(md_Navigation_StandardLink());
                break;
        }
        md.append("\n");
        md.append("\n");
        md.append(md_CodeFiles());
        return md.toString();

    }



    /**
     * 拼接笔记导航栏 - 使用标准 Markdown 链接格式
     * @return 返回拼接好的导航栏字符串构建器
     */

    private StringBuilder md_Navigation_StandardLink() {
        StringBuilder md = new StringBuilder();
        md.append("## 笔记导航栏");
        md.append("\n");
        md.append("\n");
        md.append("当前笔记代码包在电脑位置：");
        md.append("\n");

        // 获取当前目录
        File Currentfile = node.getDirectory().getAbsoluteFile();
        md.append("[打开文件夹](file:///" + Currentfile.getAbsolutePath() + ")");
        md.append("\n");
        md.append("\n");

        // 只要不是根节点，就一定会有父节点。
        if (!node.isRoot()) {
            //直接返回根目录
            String rootName = TreeNode.root.getDirName();
            md.append("- [根目录](" + rootName + ".md)");
            md.append("\n");
            String fileName = node.getParent().getDirName();
            md.append("- [返回上级目录](" + fileName + ".md)");
            md.append("\n");
        }

        if (!node.isLeaf()) {
            md.append("- 下级目录");
            md.append("\n");
            appendSubDirectoriesStandardLink(md, node.getChildrenDir(), 1); // 开始递归
        }

        return md;
    }

    /**
     * 递归添加子目录到 Markdown 字符串中（使用标准链接）
     *
     * @param md     要追加内容的 StringBuilder
     * @param nodes  当前层级的所有子目录节点
     * @param level  当前层级（从1开始）
     */
    private void appendSubDirectoriesStandardLink(StringBuilder md, List<TreeNode> nodes, int level) {
        for (TreeNode treeNode : nodes) {
            // 添加适当数量的缩进空格
            md.append(getIndent(level));

            md.append("- [" + treeNode.getDirName() + "](" + treeNode.getDirName() + ".md)");
            md.append("\n");

            // 如果还有子目录，则递归调用
            if (!treeNode.isLeaf()) {
                appendSubDirectoriesStandardLink(md, treeNode.getChildrenDir(), level + 1);
            }

            // 添加该目录下的所有文件
            if (!treeNode.getChildrenFile().isEmpty()) {
                for (File file : treeNode.getChildrenFile()) {
                    // 添加文件缩进
                    md.append(getIndent(level+1));

                    md.append("- " + file.getName());
                    md.append("\n");
                }
            }
        }
    }

    /**
     *  拼接笔记导航栏 -obsidian格式的双链
     *  @return 返回拼接好的导航栏字符串构建器
     */

    private StringBuilder md_Navigation_Obsidian() {
        StringBuilder md = new StringBuilder();
        md.append("## 笔记导航栏");
        md.append("\n");
        md.append("\n");
        md.append("当前笔记代码包在电脑位置：");
        md.append("\n");

        // 获取当前目录
        File Currentfile = node.getDirectory().getAbsoluteFile();
        md.append("file:///" + Currentfile.getAbsolutePath());
        md.append("\n");
        md.append("\n");

        // 只要不是根节点，就一定会有父节点。
        if (!node.isRoot()) {
            //直接返回根目录
            String rootName = TreeNode.root.getDirName();
            md.append("- 根目录：[[" + rootName + "]]");
            md.append("\n");
            String fileName = node.getParent().getDirName();
            md.append("- 返回上级目录：[[" + fileName + "]]");
            md.append("\n");
        }

        if (!node.isLeaf()) {
            md.append("- 下级目录");
            md.append("\n");
            appendSubDirectories(md, node.getChildrenDir(), 1); // 开始递归

        }
        return md;
    }

    /**
     * 生成指定层级的缩进
     * @param level 缩进层级
     * @return 缩进字符串
     */
    private String getIndent(int level) {
        if (level <= 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }
        return sb.toString();
    }

    /**
     * 递归添加子目录到 Markdown 字符串中
     *
     * @param md     要追加内容的 StringBuilder
     * @param nodes  当前层级的所有子目录节点
     * @param level  当前层级（从1开始）
     */
    private void appendSubDirectories(StringBuilder md, List<TreeNode> nodes, int level) {
        for (TreeNode treeNode : nodes) {
            // 添加适当数量的缩进空格
            md.append(getIndent(level));
            md.append("- [[").append(treeNode.getDirName()).append("]]");
            md.append("\n");



            // 如果还有子目录，则递归调用 ,说明就是非叶子节点
            if (!treeNode.isLeaf()) {

                appendSubDirectories(md, treeNode.getChildrenDir(), level + 1);
            }

            // 添加该节点下的所有文件
            if (!treeNode.getChildrenFile().isEmpty()) {

                for (File file : treeNode.getChildrenFile()) {
                    // 添加文件缩进
                    md.append(getIndent(level+1));
                    md.append("- ").append(file.getName());
                    md.append("\n");
                }
            }
        }
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
