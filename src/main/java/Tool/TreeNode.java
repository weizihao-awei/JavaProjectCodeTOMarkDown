package Tool;

import java.io.File;
import java.util.*;

import static Tool.FileProcess.filterCodeFiles;
import static Tool.FileProcess.filterDirName;

/**
 * 树节点类
 * 目录书，每一个节点都是一个目录
 *
 */
public class TreeNode {
private File directory;
    

    private String dirName;
    

    private TreeNode parent;
    


    // 用来存储子目录
private List<TreeNode> childrenDir = new ArrayList<>();
//用来存储子文件
private List<File> childrenFile = new ArrayList<>();

/**
 * 获取子目录列表
 * @return 子目录列表的只读视图
 */
public List<TreeNode> getChildrenDir() {
    return childrenDir;
}

/**
 * 获取子文件列表
 * @return 子文件列表的只读视图
 */
public List<File> getChildrenFile() {
    return childrenFile;
}

    // 虚假的构造方法
    public TreeNode(File directory) {
        this.directory = directory;
        this.dirName = directory.getName();
    }
    //真正的构造方法
    /**
     * 递归构建目录树，保留父子双向关系
     * @param rootFile 根目录（或单个文件）
     * @return 对应的树节点；如果 rootFile 不存在则返回 null
     */
    public static TreeNode build(File rootFile) {
        if (rootFile == null || !rootFile.exists()) return null;

        TreeNode rootTreeNode = new TreeNode(rootFile);

        if (rootFile.isDirectory()) {
            File[] children = rootFile.listFiles();
            if (children != null) {
                // 分离文件和目录
                List<File> files = new ArrayList<>();
                List<File> directories = new ArrayList<>();
                
                for (File child : children) {
                    if (child.isDirectory()) {

                        directories.add(child);
                    } else {

                        files.add(child);
                    }
                }

                //过滤掉.git目录这种隐藏目录
                directories  =filterDirName(directories);
                //todo: 这里可以进一步过滤不想要的目录

                //过滤掉代码文件
                files  = filterCodeFiles( files);
                //todo: 这里可以进一步过滤不想要的文件

                // 批量添加文件
                rootTreeNode.childrenFile.addAll(files);
                
                // 递归处理目录
                for (File dir : directories) {
                    rootTreeNode.addChild(build(dir));
                }
            }
        }
        return rootTreeNode;
    }

    /* ========== 核心：双向绑定 ========== */
    public void addChild(TreeNode child) {
        Objects.requireNonNull(child);
        if (child.parent != null) {               // 先从原父节点移除
            child.parent.childrenDir.remove(child);
        }
        childrenDir.add(child);
        child.parent = this;                      // 指回父节点
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }
    public File getDirectory() {
        return directory;
    }

    /* ========== 只读接口 ========== */


    public boolean isLeaf()          { return childrenDir.isEmpty(); }
    public TreeNode getParent() { return parent; }

    /* ========== 实用工具 ========== */
    public boolean isRoot()          { return parent == null; }
    public int getDepth() {           // 根深度为 0
        int d = 0;
        TreeNode p = parent;
        while (p != null) { ++d; p = p.parent; }
        return d;
    }


    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

}