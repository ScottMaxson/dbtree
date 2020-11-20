/**
 * 
 */
package net.zebrasoftware.dbtree;

import java.util.List;

/**
 * @author scott
 *
 */
public class ConstraintTreeBuilder {
	private static final int LEVEL_MAX = 20;

	private ForeignKeyDBQueryRun queryRunner;
	
	public ConstraintTreeBuilder(ForeignKeyDBQueryRun runner, TreeNode<ConstraintData> root) {
		this.queryRunner = runner;
	}
	
	public void addChildren(TreeNode<ConstraintData> node, int level) {
		if (LEVEL_MAX <= level) {
			System.err.println("exceeded max level");
			return;
		}
		ConstraintData data = node.getNode();
		List<ForeignKeyConstraintBean> children = this.queryRunner.queryForeignKeyDependents(data.getSchemaName(), data.getTableName());
		if (null != children && 0 < children.size()) {
			children.stream()
			.forEach((child) -> createChildConstraintNode(child, node, level+1));
			node.getChildren().stream()
			.filter((child) -> (!child.getNode().getTableName().equals(data.getTableName())))
			.forEach( (childNode) -> addChildren(childNode, level+1));
		}
		System.out.println("Done for " + data.getTableName() + " at " + level);
		
	}
	
	private TreeNode<ConstraintData> createChildConstraintNode(ForeignKeyConstraintBean child, TreeNode<ConstraintData> parent, int lvl) {
		ConstraintData data = new ConstraintData(child.getConstraintSchema(),
				child.getTableName(), 
				child.getConstraintName(), 
				child.getReferenceTableName(), 
				child.getForeignKeyColumns(), 
				child.getPrimaryKeyColumns());
		System.out.println("adding child level " + lvl + " to " + parent.getNode().getTableName() + " on " + data.getTableName() + "." + data.getColumnName());
		TreeNode<ConstraintData> node = new TreeNode<ConstraintData>(data, parent, lvl);
		return node;
	}
}
