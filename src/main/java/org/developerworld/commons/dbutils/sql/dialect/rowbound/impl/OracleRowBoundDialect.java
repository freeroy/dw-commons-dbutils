package org.developerworld.commons.dbutils.sql.dialect.rowbound.impl;

public class OracleRowBoundDialect extends AbstractRowBoundDialect {

	public String buildRowBoundSql(String sql, Integer limit, Integer offset) {
		if (isErrorArgs(sql, limit, offset))
			return sql;
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}
		StringBuffer pagingSelect = new StringBuffer();
		if (offset != null)
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		else
			pagingSelect.append("select * from ( ");
		pagingSelect.append(sql);
		if (offset != null)
			pagingSelect.append(" ) row_ ) where rownum_ <= " + (limit + offset) + " and rownum_ > " + offset);
		else
			pagingSelect.append(" ) where rownum <= " + limit);
		if (isForUpdate)
			pagingSelect.append(" for update");
		return pagingSelect.toString();
	}

	public boolean supportOffset() {
		return true;
	}

}
