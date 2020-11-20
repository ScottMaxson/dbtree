/**
 * bean model of foreign key constraint data
 */
package net.zebrasoftware.dbtree;

/**
 * @author scott
 *
 */
public class ForeignKeyConstraintBean {
	
	private String constraintName; //CONSTNAME              
	private String constraintSchema; //TABSCHEMA              
	private String tableName; //TABNAME                
	private String owner; //OWNER                  
	private String ownerType; //OWNERTYPE              
	private String referenceKeyName; //REFKEYNAME             
	private String referenceSchema; //REFTABSCHEMA           
	private String referenceTableName; //REFTABNAME             
	private int    colCount; //COLCOUNT                  
	private String deleteRule; //DELETERULE             
	private String updateRule; //UPDATERULE             
	private java.sql.Timestamp createTime; //CREATE_TIME
	private String foreignKeyColumns; //FK_COLNAMES            
	private String primaryKeyColumns; //PK_COLNAMES            
	private String definer; //DEFINER       
	
	public ForeignKeyConstraintBean(String constraintName, String constraintSchema, String tableName,
			String referenceTableName, String foreignKeyColumns, String primaryKeyColumns) {
		this.constraintName = constraintName;
		this.constraintSchema = constraintSchema;
		this.tableName = tableName;
		this.referenceTableName = referenceTableName;
		this.foreignKeyColumns = foreignKeyColumns;
		this.primaryKeyColumns = primaryKeyColumns;
	}
	
	public ForeignKeyConstraintBean() {
	}
	
	public String getConstraintName() {
		return constraintName;
	}
	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}
	public String getConstraintSchema() {
		return constraintSchema;
	}
	public void setConstraintSchema(String constraintSchema) {
		this.constraintSchema = constraintSchema;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getOwnerType() {
		return ownerType;
	}
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}
	public String getReferenceKeyName() {
		return referenceKeyName;
	}
	public void setReferenceKeyName(String referenceKeyName) {
		this.referenceKeyName = referenceKeyName;
	}
	public String getReferenceSchema() {
		return referenceSchema;
	}
	public void setReferenceSchema(String referenceSchema) {
		this.referenceSchema = referenceSchema;
	}
	public String getReferenceTableName() {
		return referenceTableName;
	}
	public void setReferenceTableName(String referenceTableName) {
		this.referenceTableName = referenceTableName;
	}
	public int getColCount() {
		return colCount;
	}
	public void setColCount(int colCount) {
		this.colCount = colCount;
	}
	public String getDeleteRule() {
		return deleteRule;
	}
	public void setDeleteRule(String deleteRule) {
		this.deleteRule = deleteRule;
	}
	public String getUpdateRule() {
		return updateRule;
	}
	public void setUpdateRule(String updateRule) {
		this.updateRule = updateRule;
	}
	public java.sql.Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.sql.Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getForeignKeyColumns() {
		return foreignKeyColumns;
	}
	public void setForeignKeyColumns(String foreignKeyColumns) {
		this.foreignKeyColumns = foreignKeyColumns;
	}
	public String getPrimaryKeyColumns() {
		return primaryKeyColumns;
	}
	public void setPrimaryKeyColumns(String primaryKeyColumns) {
		this.primaryKeyColumns = primaryKeyColumns;
	}
	public String getDefiner() {
		return definer;
	}
	public void setDefiner(String definer) {
		this.definer = definer;
	}


}
