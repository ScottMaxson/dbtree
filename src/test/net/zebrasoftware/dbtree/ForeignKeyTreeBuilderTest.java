package net.zebrasoftware.dbtree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ForeignKeyTreeBuilderTest {
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * first test method for {@link com.didactix.common.util.FileRecordReader#parseData(com.didactix.dsug.dao.UserInfo, java.io.BufferedReader, java.lang.String)}.
	 */
	@Test
	public void testConstraintTreeBuilder() throws Exception {
		ConstraintData rootData = new ConstraintData("test", "root", null, null, null, null);
		TreeNode<ConstraintData> testRootNode = new TreeNode<ConstraintData>(rootData, null, 0);
		ConstraintTreeBuilder testBuilder = new ConstraintTreeBuilder(new mockFKQueryRunner(), testRootNode);
		testBuilder.addChildren(testRootNode,0);
	
		assertNotNull(rootData);
		Set<TreeNode<ConstraintData>> rootChildren = testRootNode.getChildren();
		assertEquals("correct root child length", 3, rootChildren.size());
		boolean foundExpectedGChild = false;
		for (TreeNode<ConstraintData> treeNode : rootChildren) {
			assertTrue("correct root child name", treeNode.getNode().getTableName().startsWith("root-child-"));
			assertEquals("correct root child level", 1, treeNode.getLevel());
			if ("root-child-1".equals(treeNode.getNode().getTableName())) {
				foundExpectedGChild = true;
				Set<TreeNode<ConstraintData>> grandChildren = treeNode.getChildren();
				assertEquals("correct grand child length", 3, grandChildren.size());
				for (TreeNode<ConstraintData> gNode : grandChildren) {
					if ("gchild-1-1".equals(gNode.getNode().getTableName())) {
						assertEquals("correct gchild-1-1 great-grand with one", 1, gNode.getChildren().size());
						gNode.getChildren().forEach(
								(o) -> assertEquals("matched the gchild-1-1 self constraint", "selfconst7", o.getNode().getConstraintName())
						);
					} else {
						assertEquals("correct gchild-1-2,3 great-grand with zero", 0, gNode.getChildren().size());
					}
				}
			}
		}
		if (! foundExpectedGChild) org.junit.Assert.fail("expected root-child-1 never found");
		
	}
	
	
	class mockFKQueryRunner implements ForeignKeyDBQueryRun {
		@Override
		public List<ForeignKeyConstraintBean> queryForeignKeyDependents(String schema, String table) {

			Map<String, List<ForeignKeyConstraintBean>> relationships = new HashMap<>();
			
			List<ForeignKeyConstraintBean> rootList = new ArrayList<>();
			rootList.add(new ForeignKeyConstraintBean("const1", "test", "root-child-1", "root", "root-child-1.mycolumn1", "root.primarycol"));
			rootList.add(new ForeignKeyConstraintBean("const2", "test", "root-child-2", "root", "root-child-2.mycolumn1", "root.primarycol"));
			rootList.add(new ForeignKeyConstraintBean("const3", "test", "root-child-3", "root", "root-child-3.mycolumn1", "root.primarycol"));
			relationships.put("root", rootList);
			
			List<ForeignKeyConstraintBean> child1List = new ArrayList<>();
			child1List.add(new ForeignKeyConstraintBean("const4", "test", "gchild-1-1", "root-child-1", "gchild-1-1.mycolumn1", "root-child-1.primarycol"));
			child1List.add(new ForeignKeyConstraintBean("const5", "test", "gchild-1-2", "root-child-1", "gchild-1-2.mycolumn1", "root-child-1.primarycol"));
			child1List.add(new ForeignKeyConstraintBean("const6", "test", "gchild-1-3", "root-child-1", "gchild-1-3.mycolumn1", "root-child-1.primarycol"));
			relationships.put("root-child-1", child1List);
			
			List<ForeignKeyConstraintBean> grandchild1List = new ArrayList<>();
			grandchild1List.add(new ForeignKeyConstraintBean("selfconst7", "test", "gchild-1-1", "gchild-1-1", "gchild-1-1.internalcol", "gchild-1-1.primarycol"));
			relationships.put("gchild-1-1", grandchild1List);
			
			return relationships.get(table);
		}
	}
}
