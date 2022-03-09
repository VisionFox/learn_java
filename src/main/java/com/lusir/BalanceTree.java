package com.lusir;

public class BalanceTree {

    public boolean isBalanced(TreeNode root) {
        return getHeight(root) >= 0;
    }

    private int getHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = getHeight(node.left);
        int rightRight = getHeight(node.right);
        // -1 标记自树的不平衡，自底而上提前打断
        if (leftHeight == -1 || rightRight == -1 || Math.abs(leftHeight - rightRight) > 1) {
            return -1;
        } else {
            return Math.max(leftHeight, rightRight) + 1;
        }
    }
}
