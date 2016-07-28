package org.developerworld.commons.dbutils.sql.dialect.rowbound.impl;

import org.developerworld.commons.dbutils.sql.dialect.rowbound.RowBoundDialect;

public abstract class AbstractRowBoundDialect implements RowBoundDialect {

	/**
	 * 检查参数
	 * 
	 * @param sql
	 * @param limit
	 * @param offset
	 * @return
	 */
	protected boolean isErrorArgs(String sql, Integer limit, Integer offset) {
		return (sql == null || sql.length() <= 0 || sql.indexOf("select") == -1 || limit == null || limit <= 0
				|| (offset != null && offset < 0));
	}
}
