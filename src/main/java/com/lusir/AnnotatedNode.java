package com.lusir;

public class AnnotatedNode {
    TreeNode node;
    int depth, pos;

    AnnotatedNode(TreeNode node, int depth, int pos) {
        this.node = node;
        this.depth = depth;
        this.pos = pos;
    }
}
