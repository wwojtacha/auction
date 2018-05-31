package helpers;

import models.TreeNode;

public class TreeNodeSetup {
    public static TreeNode setupTree() {
        final TreeNode root = new TreeNode(0, "Categories");
        final TreeNode child1 = new TreeNode(1, "Computers");
        final TreeNode child11 = new TreeNode(11, "Laptops");
        final TreeNode child111 = new TreeNode(111, "Lenovo");
        final TreeNode child112 = new TreeNode(112, "Asus");
        final TreeNode child113 = new TreeNode(113, "Dell");
        final TreeNode child12 = new TreeNode(12, "PC");
        final TreeNode child2 = new TreeNode(2, "Cars");
        final TreeNode child21 = new TreeNode(21, "SUVs");
        root.addChild(child1);
        root.addChild(child2);
        child1.addChild(child11);
        child11.addChild(child111);
        child11.addChild(child112);
        child11.addChild(child113);
        child1.addChild(child12);
        child2.addChild(child21);
        return root;
    }
}
