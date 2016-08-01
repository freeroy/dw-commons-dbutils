package org.developerworld.commons.dbutils.sql.dialect.rowbound.impl;

public class Cache71RowBoundDialect extends AbstractRowBoundDialect {

	public String buildRowBoundSql(String sql, Integer limit, Integer offset) {
		if (isErrorArgs(sql, limit, offset))
			return sql;
		sql = sql.trim();
		int insertionPoint = sql.toLowerCase().indexOf("select distinct") != -1 ? 15 : 6;
		int count = limit;
		if (offset != null)
			count += offset;
		return new StringBuffer().append(sql).insert(insertionPoint, " TOP " + count + " ").toString();
	}

	public boolean supportOffset() {
		return false;
	}

}
