package org.developerworld.commons.dbutils.sql.command.condition;

/**
 * where 条件
 * @author Roy Huang
 *
 */
public interface WhereCondition {

	/**
	 * 构建sql表达式
	 * @return
	 */
	String buildSQLExpression();

	/**
	 * 返回参数值
	 * @return
	 */
	Object[] getValues();
	
	/**
	 * 返回参数值
	 * @return
	 */
	Object getValue();
	
	/**
	 * 返回对应字段
	 * @return
	 */
	String getColumn();
	
	/**
	 * 返回逻辑
	 * @return
	 */
	SQLWhereLogicOpt getOpt();

}
