package org.developerworld.commons.dbutils.sql.dialect.rowbound.impl;

public class SQLServer_LE2000RowBoundDialect extends AbstractRowBoundDialect {

	public String buildRowBoundSql(String sql, Integer limit, Integer offset) {
		if (isErrorArgs(sql, limit, offset))
			return sql;
		return new StringBuffer().append(sql)
				.insert(getAfterSelectInsertPoint(sql), " top " + limit).toString();
	}

	private int getAfterSelectInsertPoint(String sql) {
		int selectIndex = sql.toLowerCase().indexOf("select");
		int selectDistinctIndex = sql.toLowerCase().indexOf("select distinct");
		return selectIndex + (selectDistinctIndex == selectIndex ? 15 : 6);
	}

	public boolean supportOffset() {
		return false;
	}

}
