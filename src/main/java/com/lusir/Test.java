package com.lusir;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class Test {
    private static volatile Integer counter = 1;
    private static volatile boolean flag = true;

    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>();
        Executors.newSingleThreadExecutor();
    }

    private void BFS(TreeNode root) {
        if (root == null) {
            return;
        }

        ArrayList<TreeNode> list = new ArrayList<>();
        list.add(root);
        while (list.size() > 0) {
            int levelSize = list.size();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = list.remove(0);
                System.out.printf("%n\t", node.val);

                TreeNode leftNode = node.left;
                if (leftNode != null) {
                    list.add(leftNode);
                }

                TreeNode rightNode = node.right;
                if (rightNode != null) {
                    list.add(rightNode);
                }
            }
            System.out.println();
        }
    }
}
