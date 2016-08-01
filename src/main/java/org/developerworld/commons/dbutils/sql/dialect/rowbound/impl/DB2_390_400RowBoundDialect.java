package org.developerworld.commons.dbutils.sql.dialect.rowbound.impl;

public class DB2_390_400RowBoundDialect extends AbstractRowBoundDialect {

	public String buildRowBoundSql(String sql, Integer limit, Integer offset) {
		if(isErrorArgs(sql, limit, offset))
			return sql;
		int count = limit;
		if (offset != null)
			count += offset;
		return new StringBuffer().append(sql).append(" fetch first ").append(count)
				.append(" rows only").toString();
	}

	public boolean supportOffset() {
		return false;
	}

}
