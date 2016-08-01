package org.developerworld.commons.dbutils.sql.dialect.rowbound.impl;

public class InformixRowBoundDialect extends AbstractRowBoundDialect {

	public String buildRowBoundSql(String sql, Integer limit, Integer offset) {
		if (isErrorArgs(sql, limit, offset))
			return sql;
		return new StringBuffer().append(sql)
				.insert(sql.toLowerCase().indexOf("select") + 6, " first " + (offset != null ? offset + limit : limit))
				.toString();
	}

	public boolean supportOffset() {
		return false;
	}

}
