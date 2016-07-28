package org.developerworld.commons.dbutils.sql.dialect.rowbound.impl;

public class MySqlRowBoundDialect extends AbstractRowBoundDialect {

	public String buildRowBoundSql(String sql, Integer limit, Integer offset) {
		//select * from table limit ?,?
		if (isErrorArgs(sql, limit, offset))
			return sql;
		return new StringBuffer().append(sql)
				.append(offset != null
						? " limit " + offset + "," + limit
						: " limit " + limit)
				.toString();
	}

	public boolean supportOffset() {
		return true;
	}

}
