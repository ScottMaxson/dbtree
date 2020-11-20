/**
 * 
 */
package net.zebrasoftware.dbtree;

import java.util.ArrayList;

import com.ibm.db2.jcc.DB2SimpleDataSource;

/**
 * @author scott
 *
 */
public class Start {
	/**
	 * @param args Schema and Table Name
	 */
	public static void main(String[] args) {
		
		DB2SimpleDataSource dbds = new com.ibm.db2.jcc.DB2SimpleDataSource();

		dbds.setDatabaseName("dev01");
		dbds.setDescription("local dev db");
		dbds.setUser("db2admin");
		dbds.setPassword("dataiscool");
		
		if (3 > args.length) {
			System.err.println("supply schema, table name and SQL clause to build tree of dependency queries");
		}
		String schema = args[0];
		String table = args[1];
		String tableClause = args[2];
		
		ConstraintData rootData = new ConstraintData(schema, table, null, null, "root_id", null);
		TreeNode<ConstraintData> root = new TreeNode<ConstraintData>(rootData, null, 0);
		ConstraintTreeBuilder builder = new ConstraintTreeBuilder(new DB2ForeignKeyQueryRun(dbds), root);
		SqlStatementGenerator sqlGenerator = new SqlStatementGenerator();
		builder.addChildren(root, 0);
		ArrayList<OrderedStatement> orderedSQLstatments = new ArrayList<>();
		sqlGenerator.buildDeleteStatementList(orderedSQLstatments, tableClause, root);
		orderedSQLstatments.sort((OrderedStatement s1, OrderedStatement s2) -> s2.getOrder() - s1.getOrder());
		orderedSQLstatments.forEach( (statement) -> System.out.println(statement.getOrder() + ": " + statement.getStatement()) );
		
	}
	
	
	
	

}
