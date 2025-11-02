package Tool;
import java.util.ArrayList;

import java.util.List;
/**
 * @BelongsProject: SourceCode_become_md
 * @BelongsPackage: Tool
 * @Author: ykw-weizihao
 * @CreateTime: 2025-11-02 11:41
 * @Description: 将目录树所有节点装入一个逻辑栈列
 * @Version: 1.0
 */
public class ListTreeDir {
    List<TreeNode> listTree = new ArrayList<>();
    private int stackPointer;


    public ListTreeDir( TreeNode root) {
        traverseTree(root);
        stackPointer = listTree.size(); // 初始化栈指针指向末尾
    }

    private void traverseTree(TreeNode root) {
        if (root == null) {
            return;
        }
        listTree.add(root);
        if (root.getChildrenDir() != null) {
            for (TreeNode child : root.getChildrenDir()) {
                traverseTree(child);
            }
        }
    }
    
    /**
     * 从末尾出栈一个元素
     * @return 栈顶元素，如果栈为空则返回null
     */
    public TreeNode pop() {
        if (isEmpty()) {
            return null;
        }

        TreeNode node  =  listTree.get(--stackPointer);
        // 树节点的文件夹名字不能相同，进行重命名
        renameFile(node);
        return node;
    }

    /**
     * 树节点的文件夹名字不能相同，进行重命名
     *
     */
    public void renameFile(TreeNode node) {
        int count = 1;
        String currentNodeName =  node.getDirName();
        //变量列表，如果发现列表里有文件名相同的，则进行重命名
        for (TreeNode treeNode : listTree) {
            //要排除自身元素
            if (treeNode == node) continue;
            if (treeNode.getDirName().equals(currentNodeName)) {
                String newName = currentNodeName + count;
                count++;
                treeNode.setDirName(newName);
            }
        }
    }
    

    /**
     * 判断栈是否为空
     * @return 如果栈为空返回true，否则返回false
     */
    public boolean isEmpty() {
        return stackPointer <= 0;
    }
}