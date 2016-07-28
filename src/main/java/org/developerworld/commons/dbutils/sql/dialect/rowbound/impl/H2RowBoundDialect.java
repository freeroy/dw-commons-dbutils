package org.developerworld.commons.dbutils.sql.dialect.rowbound.impl;

public class H2RowBoundDialect extends AbstractRowBoundDialect {

	public String buildRowBoundSql(String sql, Integer limit, Integer offset) {
		if (isErrorArgs(sql, limit, offset))
			return sql;
		return new StringBuffer().append(sql)
				.append(offset != null
						? " limit " + limit + " offset " + offset
						: " limit " + limit)
				.toString();
	}

	public boolean supportOffset() {
		return true;
	}

}
