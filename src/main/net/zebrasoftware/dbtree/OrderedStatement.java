/**
 * String statement with an int order.
 */
package net.zebrasoftware.dbtree;

/**
 * @author scott
 *
 */
public class OrderedStatement {
	private final String statement;
	private final int order;
	public OrderedStatement(int num, String stmt) {
		this.statement = stmt;
		this.order = num;
	}
	public String getStatement() {
		return statement;
	}
	public int getOrder() {
		return order;
	}
}
