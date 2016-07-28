package org.developerworld.commons.dbutils.sql.dialect.rowbound.impl;

public class DB2RowBoundDialect extends AbstractRowBoundDialect {

	public String buildRowBoundSql(String sql, Integer limit, Integer offset) {
		// select * from (select rownumber() over([order by]) as
		// rownumber_,row_.* from table row_ [order by]) as tmp_ where
		// rownumber_ between ?+1 and ?
		if (isErrorArgs(sql, limit, offset))
			return sql;
		sql = sql.trim();
		int startOfSelect = sql.toLowerCase().indexOf("select");
		StringBuffer pagingSelect = new StringBuffer().append(sql.substring(0, startOfSelect))
				.append("select * from ( select ").append(getRowNumber(sql));
		if (hasDistinct(sql))
			pagingSelect.append(" row_.* from ( ").append(sql.substring(startOfSelect)).append(" ) as row_");
		else
			pagingSelect.append(sql.substring(startOfSelect + 6));
		pagingSelect.append(" ) as temp_ where rownumber_ ");
		if (offset != null)
			pagingSelect.append("between " + (offset + 1) + " and " + (offset + limit));
		else
			pagingSelect.append("<= " + limit);
		return pagingSelect.toString();
	}

	private String getRowNumber(String sql) {
		StringBuffer rownumber = new StringBuffer().append("rownumber() over(");
		int orderByIndex = sql.toLowerCase().indexOf("order by");
		if (orderByIndex > 0 && !hasDistinct(sql))
			rownumber.append(sql.substring(orderByIndex));
		rownumber.append(") as rownumber_,");
		return rownumber.toString();
	}

	private static boolean hasDistinct(String sql) {
		return sql.toLowerCase().indexOf("select distinct") >= 0;
	}

	public boolean supportOffset() {
		return true;
	}

}
