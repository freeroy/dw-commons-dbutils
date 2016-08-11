package org.developerworld.commons.dbutils.sql.command;

import java.util.Map;

import org.developerworld.commons.dbutils.sql.command.condition.WhereCondition;

/**
 * 条件指令
 * @author Roy Huang
 *
 * @param <T>
 * @param <E>
 */
public interface WhereCommand<T extends WhereCommand<T, E>, E extends WhereCondition> {

	/**
	 * 添加并条件
	 * 
	 * @param condition
	 * @return
	 */
	public T and(E condition);

	/**
	 * 添加并条件组
	 * 
	 * @param command
	 * @return
	 */
	public T and(T command);

	/**
	 * 添加并条件
	 * 
	 * @param condition
	 * @return
	 */
	public T or(E condition);

	/**
	 * 添加并条件组
	 * 
	 * @param command
	 * @return
	 */
	public T or(T command);

	/**
	 * 构建sql语句
	 * 
	 * @return
	 */
	public String buildSql();

	/**
	 * 构建不包含where的sql语句
	 * 
	 * @return
	 */
	public String buildSqlWithOutWhere();
	
	/**
	 * 获取参数值
	 * @return
	 */
	public Object[] getParameterValues();
	
	/**
	 * 获取参数表
	 * @return
	 */
	public Map<String,Object> getParameterNameValues();

	/**
	 * 是否有条件
	 * @return
	 */
	public boolean hasWhere();

}
