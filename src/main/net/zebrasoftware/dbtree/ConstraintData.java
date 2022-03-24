/**
 * Simple database constraint model
 */
package net.zebrasoftware.dbtree;

/**
 * @author scott
 *
 */
public class ConstraintData {
	private String schemaName;
	private String tableName;
	private String constraintName;
	private String refersToTableName;
	private String columnName;
	private String refersToColumnName;
	private String sqlClause;
	
	public ConstraintData(String schema, String table, String constraint, String refTable, String column, String refColumn) {
		this.tableName = table;
		this.schemaName = schema;
		this.constraintName = constraint;
		this.refersToTableName = refTable;
		this.columnName = column;
		this.refersToColumnName = refColumn;
		this.sqlClause="";
	}
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getConstraintName() {
		return constraintName;
	}
	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}
	public String getRefersToTableName() {
		return refersToTableName;
	}
	public void setRefersToTableName(String refersToTableName) {
		this.refersToTableName = refersToTableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getRefersToColumnName() {
		return refersToColumnName;
	}
	public void setRefersToColumnName(String refersToColumnName) {
		this.refersToColumnName = refersToColumnName;
	}
	public String getSqlClause() {
		return sqlClause;
	}
	public void setSqlClause(String sqlClause) {
		this.sqlClause = sqlClause;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((constraintName == null) ? 0 : constraintName.hashCode());
		result = prime * result + ((schemaName == null) ? 0 : schemaName.hashCode());
		result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConstraintData other = (ConstraintData) obj;
		if (constraintName == null) {
			if (other.constraintName != null)
				return false;
		} else if (!constraintName.equals(other.constraintName))
			return false;
		if (schemaName == null) {
			if (other.schemaName != null)
				return false;
		} else if (!schemaName.equals(other.schemaName))
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ConstraintData [schemaName=" + schemaName + ", tableName=" + tableName + ", constraintName="
				+ constraintName + ", refersToTableName=" + refersToTableName + "]";
	}
	
	

}
