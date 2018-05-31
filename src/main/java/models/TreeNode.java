package models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TreeNode {
    private int id;
    private String name;
    private TreeNode parent;
    private List<TreeNode> children;

    private List<Auction> auctions;

    public TreeNode(int id, String name) {
        this.id = id;
        this.name = name;
        auctions = new ArrayList<>();
        children = new LinkedList<>();
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    private TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public void addChild(TreeNode child) {
        child.setParent(this);
        this.children.add(child);
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public void addAuction(Auction auction) {
        this.auctions.add(auction);
    }

    public int branchesToRoot() {
        int level = 0;
        if (!isRoot()) {
            level = this.getParent().branchesToRoot() + 1;
        }
        return level;
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public TreeNode searchById(int categoryID) {
        if (categoryID == this.id) {
            return this;
        } else {
            for (TreeNode child : this.children) {
                if (child.searchById(categoryID) != null) {
                    return child.searchById(categoryID);
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return id + ". " + name;
    }
}