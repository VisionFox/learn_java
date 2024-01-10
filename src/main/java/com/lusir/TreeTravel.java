package com.lusir;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class TreeTravel {
    // 前序遍历 递归
    public void preOrderRecur(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.println(root.val);
        preOrderRecur(root.left);
        preOrderRecur(root.right);
    }

    // 前序遍历 非递归
    public void preOrder(TreeNode root) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode top = stack.pop();
            System.out.println(top.val);
            // 注意：右子节点先入栈，左子节点后入栈，确保左子节点先出栈
            if (top.right != null) {
                stack.push(top.right);
            }
            if (top.left != null) {
                stack.push(top.left);
            }
        }
    }


    // 中序遍历 递归
    public void inorderRecur(TreeNode root) {
        if (root == null) {
            return;
        }
        preOrderRecur(root.left);
        System.out.println(root.val);
        preOrderRecur(root.right);
    }

    // 中序遍历 非递归
    public void inorder(TreeNode root) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode curNode = root;

        while (curNode != null || !stack.isEmpty()) {
            // 将当前节点及其左子节点入栈
            while (curNode != null) {
                stack.push(curNode);
                curNode = curNode.left;
            }
            // 出栈并访问节点
            curNode = stack.pop();
            System.out.println(curNode.val);
            // 然后转向右子树
            curNode = curNode.right;
        }
    }

    // 后序遍历 递归
    public void behindOrderRecur(TreeNode root) {
        if (root == null) {
            return;
        }
        preOrderRecur(root.left);
        preOrderRecur(root.right);
        System.out.println(root.val);
    }

    public void behindOrder(TreeNode root) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        TreeNode lastVisit = root;

        while (node != null || !stack.isEmpty()) {
            //压左子树
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            //查看当前栈顶元素
            node = stack.peek();
            //如果其右子树也为空，或者右子树已经访问
            //则可以直接输出当前节点的值
            if (node.right == null || node.right == lastVisit) {
                System.out.print(node.val + " ");
                stack.pop();
                lastVisit = node;
                node = null;
            } else {
                //否则，继续遍历右子树
                node = node.right;
            }
        }

    }

    public void bfs(TreeNode root) {
        if (root == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode curNode = queue.poll();
            System.out.println(curNode.val);
            if (curNode.left != null) {
                queue.add(curNode.left);
            }
            if (curNode.right != null) {
                queue.add(curNode.right);
            }
        }
    }


    public void dfs(TreeNode root) {
        // 有前中后三种遍历方式
        // 一般为前序遍历
    }
}
