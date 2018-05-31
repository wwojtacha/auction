package views;

import models.TreeNode;

public class TreeNodeView {
    public void viewCategories(TreeNode root) {
        System.out.println(root);
        for (TreeNode child : root.getChildren()) {
            for (int i = 0; i < child.branchesToRoot(); i++) {
                System.out.print("   ");
            }
            viewCategories(child);
        }
    }
}
