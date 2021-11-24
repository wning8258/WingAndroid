package com.wning.demo.test.algorithm;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/binary-tree-level-order-traversal/
 */
class 二叉树的层序遍历 {
    public void levelOrder(TreeNode root){
        if (root == null){
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        // 先将根节点放入队列
        queue.add(root);

        while (!queue.isEmpty()){
            // 从队列中取出节点
            TreeNode node = queue.poll();
            System.out.println(node.val);

            // 如果其左子树不为空，则将左子树加入队列
            if (node.left != null){
                queue.add(node.left);
            }
            // 如果其右子树不为空，则将其右子树加入队列
            if (node.right != null){
                queue.add(node.right);
            }
        }
    }
}
