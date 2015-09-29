package org.developerworld.commons.dbutils.info;

/**
 * 数据表过滤器
 * @author Roy Huang
 * @version 20130805
 *
 */
public interface TablenameFilter {

	/**
	 * 判断是否需要提取该表数据
	 * @param tableName
	 * @return
	 */
	public boolean accept(String tableName);
}
