package Tool;
import java.util.ArrayList;

import java.util.Iterator;
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
     * @description: 删除队列中的元素，这样子做是为了删除没有代码文件的空包目录
     * 删除队列中的元素：要被删除元素要满足这个几个条件； 1.该目录下没有代码文件 2.不是根目录
     * 删除后还要保证数结构不能断掉，所以要让父目录指向要被删除元素的子目录，而子目录的父目录指向要被删除元素的父目录后，才可以进行删除
     * @author: yekunwei
     * @date: 2025/11/8 15:27
     **/
    public void deleteNode() {
        Iterator<TreeNode> iterator = listTree.iterator();
        while (iterator.hasNext()) {
            TreeNode node = iterator.next();
            // 满足删除条件：虽然这样删除空文件夹比较彻底，但是这样删除后，目录结构变化太大了
//            if (node.getChildrenFile().isEmpty()&&!node.isRoot()){
//                TreeNode parent = node.getParent();
//                //从父节点中移除该元素
//                parent.getChildrenDir().remove(node);
//                //让node的所有孩子节点的父节点指向node的父节点
//                for (TreeNode child : node.getChildrenDir()) {
//                    //这个添加方法，会自动实现双向绑定，让孩子指向父亲，父亲也会指向孩子
//                    parent.addChild(child);
//                }
//
//                // 使用 iterator 删除以避免异常
//               iterator.remove();
//            }
            if (node.getChildrenFile().isEmpty()
                    && node.getChildrenDir().size() == 1
                    && !node.isRoot()&& !node.isLeaf()) {

                TreeNode parent = node.getParent();
                parent.getChildrenDir().remove(node);

                TreeNode child = node.getChildrenDir().get(0);

                //这个添加方法，会自动实现双向绑定，让孩子指向父亲，父亲也会指向孩子
                 parent.addChild(child);

                // 使用 iterator 删除以避免异常
                iterator.remove();
            }


        }
        // 因为进行了删除所以栈指针要重新计算
        stackPointer = listTree.size();
    }


    /**
     * 判断栈是否为空
     * @return 如果栈为空返回true，否则返回false
     */
    public boolean isEmpty() {
        return stackPointer <= 0;
    }
}