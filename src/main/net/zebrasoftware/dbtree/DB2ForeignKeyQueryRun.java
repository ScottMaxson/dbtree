/**
 * foreign key queries 
 */
package net.zebrasoftware.dbtree;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * @author scott
 *
 */
public class DB2ForeignKeyQueryRun implements ForeignKeyDBQueryRun {
	public static final String QUERY = "SELECT "
			+ "CONSTNAME     AS  constraintName, "		//name of the constraint on the child table's foreign key; should be unique
			+ "TABSCHEMA     AS  constraintSchema, "
			+ "TABNAME       AS  tableName, "			//child table holding this FK constraint on a parent [primary] key
			+ "OWNER         AS  owner, "
			+ "OWNERTYPE     AS  ownerType, "
			+ "REFKEYNAME    AS  referenceKeyName, "
			+ "REFTABSCHEMA  AS  referenceSchema, "		//schema of the "parent" table
			+ "REFTABNAME    AS  referenceTableName, "  //name of the parent table
			+ "COLCOUNT      AS  colCount, "
			+ "DELETERULE    AS  deleteRule, "
			+ "UPDATERULE    AS  updateRule, "
			+ "CREATE_TIME   AS  createTime, "
			+ "FK_COLNAMES   AS  foreignKeyColumns, "
			+ "PK_COLNAMES   AS  primaryKeyColumns, " //column name(s) of the parent table, holding the key values
			+ "DEFINER       AS  definer "
			+ " FROM SYSCAT.References ";
	
	private DataSource dataSource;
	
	public DB2ForeignKeyQueryRun( DataSource dbds) {
		this.dataSource = dbds;
	}
	
	@Override
	public List<ForeignKeyConstraintBean> queryForeignKeyDependents(String schema, String table) {
		String query = QUERY + " WHERE " +
				" TABSCHEMA = '" + schema.toUpperCase() + "' AND " +
				" REFTABNAME = '" + table.toUpperCase() + "' ";
		BeanListHandler<ForeignKeyConstraintBean> h = new BeanListHandler<>(ForeignKeyConstraintBean.class);
		QueryRunner q = new QueryRunner(this.dataSource);
		try {
			List<ForeignKeyConstraintBean> result = q.query(query, h);
			return result;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			if (null != e.getCause()) {
				System.err.println("Caused by");
				e.getCause().printStackTrace();
			}
			return new ArrayList<ForeignKeyConstraintBean>();
		}
	}

}
