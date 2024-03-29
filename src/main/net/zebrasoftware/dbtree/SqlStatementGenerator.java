/**
 * Generate standard SQL statements
 * from a tree view of table constraints.
 */
package net.zebrasoftware.dbtree;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author scott
 *
 */
public class SqlStatementGenerator {
	
	/**
	 * Build an ordered list of SQL Delete statements.
	 * Normally these should be executed in reverse order number.
	 * @param holder the list to fill by this method.
	 * @param clause starting constraint for the root node, which will perform deletes on that table with "WHERE " + clause
	 * @param nextNode root node of the tree, which must at least contain the schema + table names.
	 */
	public void buildDeleteStatementList(ArrayList<OrderedStatement> holder, String clause, TreeNode<ConstraintData> nextNode) {
		int order = 10*nextNode.getLevel();
		ConstraintData constraint = nextNode.getNode();
		if (null != nextNode.getParent() && constraint.getTableName().equals(constraint.getRefersToTableName())) {
			//bump order of self-reference earlier in list
			order = order-1;
		}
		String table = constraint.getTableName();
		String column = constraint.getColumnName();
		String schema = constraint.getSchemaName();
		String refColumn = constraint.getRefersToColumnName();
		String refTable = constraint.getRefersToTableName();
		String childClause = column + " IN (SELECT " + refColumn + " FROM " + schema + "." + refTable + " WHERE " + clause + ")";
		if (null == nextNode.getParent()) {
			holder.add( new OrderedStatement(order, "DELETE FROM " + schema + "." + table + " WHERE " + clause));
			childClause = clause;
		} else {
			holder.add( new OrderedStatement(order, "DELETE FROM " + schema + "." + table + " WHERE " + childClause));
		}
		String nestedClause = childClause;
		nextNode.getChildren().stream().forEach( (child) -> buildDeleteStatementList(holder, nestedClause, child));
	}
	
	/**
	 * Build map from each constraint held in the supplied tree, to a SQL statement that counts the records satisfying the supplied root-node clause.
	 * @param mappedSQLQuery The map filled by this method, for each constraint a SQL COUNT() is generated.
	 * @param clause starting SQL clause for the root node
	 * @param nextNode root node of the tree, which must at least contain column name, table name, and schema name.
	 */
	public void buildSelectCountsList(Map<ConstraintData, String> mappedSQLQuery, String clause, TreeNode<ConstraintData> nextNode) {
		ConstraintData constraint = nextNode.getNode();
		String table = constraint.getTableName();
		String column = constraint.getColumnName();
		String schema = constraint.getSchemaName();
		String refColumn = constraint.getRefersToColumnName();
		String refTable = constraint.getRefersToTableName();
		String childClause = column + " IN (SELECT " + refColumn + " FROM " + schema + "." + refTable + " WHERE " + clause + ")";
		if (null == nextNode.getParent()) {
			mappedSQLQuery.put(constraint, "SELECT COUNT(" +  column + ") FROM " + schema + "." + table + " WHERE " + clause);
			childClause = clause;
		} else {
			mappedSQLQuery.put(constraint, "SELECT COUNT(" + column + ") FROM " + schema + "." + table + " WHERE " + childClause);
		}
		String nestedClause = childClause;
		nextNode.getChildren().stream().forEach( (child) -> buildSelectCountsList(mappedSQLQuery, nestedClause, child));
	}

}
