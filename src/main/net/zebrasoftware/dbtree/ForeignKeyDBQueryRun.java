package net.zebrasoftware.dbtree;

import java.util.List;

public interface ForeignKeyDBQueryRun {

	List<ForeignKeyConstraintBean> queryForeignKeyDependents(String schema, String table);

}