/**
 * 
 */
package net.zebrasoftware.dbtree;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;

import com.ibm.db2.jcc.DB2SimpleDataSource;

/**
 * @author scott
 * Demonstration class.
 * Builds the foreign-key relationship tree and traverses "down" the tree from the given table.
 * 
 */
public class Start {
	/**
	 * @param args Schema, Table name, and a query clause on that table. 
	 * Optional 4th parameter "execute" to run the generated SQL against the database, 
	 * otherwise will print the SQL statements.
	 */
	public static void main(String[] args) {
		
		DB2SimpleDataSource dbds = new com.ibm.db2.jcc.DB2SimpleDataSource();
		Properties dbprops = new Properties();
		InputStream propValueStream = net.zebrasoftware.dbtree.Start.class.getClassLoader().getResourceAsStream("resource/dbtree.properties");
		try {
			dbprops.load(propValueStream);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		dbds.setDatabaseName(dbprops.getProperty("DatabaseName"));
		dbds.setDescription(dbprops.getProperty("Description"));
		dbds.setUser(dbprops.getProperty("User"));
		dbds.setPassword(dbprops.getProperty("Password"));
		dbds.setDriverType(4);
		dbds.setServerName(dbprops.getProperty("ServerName"));
		dbds.setPortNumber(Integer.parseInt(dbprops.getProperty("PortNumber")));
		
		if (3 > args.length) {
			System.err.println("supply schema, table name and SQL clause to output DELETE statements; specific 'execute' as 4th param to run them");
		}
		String schema = args[0];
		String table = args[1];
		String tableClause = args[2];
		String command = (args.length > 3 ? args[3] : "print");
		
		ConstraintData rootData = new ConstraintData(schema, table, null, null, "root_id", null);
		TreeNode<ConstraintData> root = new TreeNode<ConstraintData>(rootData, null, 0);
		ConstraintTreeBuilder builder = new ConstraintTreeBuilder(new DB2ForeignKeyQueryRun(dbds), root);
		SqlStatementGenerator sqlGenerator = new SqlStatementGenerator();
		builder.addChildren(root, 0);
		ArrayList<OrderedStatement> orderedSQLstatments = new ArrayList<>();
		sqlGenerator.buildDeleteStatementList(orderedSQLstatments, tableClause, root);
		orderedSQLstatments.sort((OrderedStatement s1, OrderedStatement s2) -> s2.getOrder() - s1.getOrder());
		
		if ("print".equals(command)) {
			orderedSQLstatments.forEach( (statement) -> System.out.println(statement.getOrder() + ": " + statement.getStatement()) );
		} else if ("execute".equals(command)) {
			QueryRunner q = new QueryRunner(dbds);
			dbds.setAutoCommit(true);
			orderedSQLstatments.forEach( (e) -> { 
				int done = 0;
				try {
					done = q.update(e.getStatement());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("deleted " + done + ": " + e.getStatement());
			});
			
		}
		
	}
	
	
	
	

}
