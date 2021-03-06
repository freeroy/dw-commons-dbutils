package org.developerworld.commons.dbutils.sql.dialect.rowbound.impl;

public class RDMSOS2200RowBoundDialect extends AbstractRowBoundDialect {

	public String buildRowBoundSql(String sql, Integer limit, Integer offset) {
		if (isErrorArgs(sql, limit, offset))
			return sql;
		return new StringBuffer().append(sql).append(" fetch first ").append(offset != null ? offset + limit : limit)
				.append(" rows only ").toString();
	}

	public boolean supportOffset() {
		return false;
	}

}
