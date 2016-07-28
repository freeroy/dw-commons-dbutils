package org.developerworld.commons.dbutils.sql.dialect.rowbound.impl;

public class IngresRowBoundDialect extends AbstractRowBoundDialect {

	public String buildRowBoundSql(String sql, Integer limit, Integer offset) {
		if ((sql == null || sql.length() <= 0 || sql.indexOf("select") == -1 || (offset != null && offset < 0)))
			return sql;
		StringBuffer sb = new StringBuffer().append(sql);
		if (offset != null)
			sb.append(" offset " + offset);
		if (limit != null)
			sb.append(" fetch first " + limit + " rows only");
		return sb.toString();
	}

	public boolean supportOffset() {
		return true;
	}

}
