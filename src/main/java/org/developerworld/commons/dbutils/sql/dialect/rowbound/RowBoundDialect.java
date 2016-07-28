package org.developerworld.commons.dbutils.sql.dialect.rowbound;

/**
 * 
 * 分页方言
 * @author Roy Huang
 *
 */
public interface RowBoundDialect {

	/**
	 * 构造分页语句
	 * @param sql
	 * @param limit
	 * @param offset
	 * @return
	 */
	public String buildRowBoundSql(String sql,Integer limit, Integer offset);
	
	/**
	 * 是否支持指定开始位置
	 * @return
	 */
	public boolean supportOffset();
	
}
