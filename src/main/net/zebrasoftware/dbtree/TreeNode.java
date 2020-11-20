package net.zebrasoftware.dbtree;

import java.util.HashSet;
import java.util.Set;

public class TreeNode<T> {
	private T node;
	private TreeNode<T> parent;
	private int level;
	private Set<TreeNode<T>> children = new HashSet();
	
	public TreeNode(T me, TreeNode<T> parentNode, int nodeLevel) {
		this.node = me;
		this.parent = parentNode;
		this.level = nodeLevel;
		if (null != parentNode) {
			this.parent.addChild(this);
		}
		
	}
	
	public T getNode() {
		return this.node;
	}
	
	public void addChild(TreeNode<T> node) {
		children.add(node);
	}

	public TreeNode<T> getParent() {
		return parent;
	}

	public int getLevel() {
		return level;
	}

	public Set<TreeNode<T>> getChildren() {
		return children;
	}

}