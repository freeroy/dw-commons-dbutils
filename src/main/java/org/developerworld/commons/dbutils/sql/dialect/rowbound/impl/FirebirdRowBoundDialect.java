package org.developerworld.commons.dbutils.sql.dialect.rowbound.impl;

public class FirebirdRowBoundDialect extends AbstractRowBoundDialect {

	public String buildRowBoundSql(String sql, Integer limit, Integer offset) {
		if (isErrorArgs(sql, limit, offset))
			return sql;
		return new StringBuffer().append(sql)
				.insert(sql.toLowerCase().indexOf("select")+6,
						offset != null
								? " first " + limit + " skip " + offset
								: " first " + limit)
				.toString();
	}

	public boolean supportOffset() {
		return true;
	}

}
