package org.developerworld.commons.dbutils.sql.dialect.rowbound.impl;

public class SQLServerRowBoundDialect extends AbstractRowBoundDialect {

	public String buildRowBoundSql(String sql, Integer limit, Integer offset) {
		if (isErrorArgs(sql, limit, offset))
			return sql;
		StringBuilder sb = new StringBuilder(sql.trim().toLowerCase());
		int orderByIndex = sb.indexOf("order by");
		CharSequence orderby = orderByIndex > 0 ? sb.subSequence(orderByIndex, sb.length())
				: "ORDER BY CURRENT_TIMESTAMP";
		// Delete the order by clause at the end of the query
		if (orderByIndex > 0)
			sb.delete(orderByIndex, orderByIndex + orderby.length());
		// HHH-5715 bug fix
		replaceDistinctWithGroupBy(sb);
		insertRowNumberFunction(sb, orderby);
		// Wrap the query within a with statement:
		sb.insert(0, "WITH query AS (").append(") SELECT * FROM query ");
		if (offset != null)
			sb.append("WHERE __row_nr__ BETWEEN " + (offset + 1) + " AND " + (offset + limit));
		else
			sb.append("WHERE __row_nr__ <= " + limit);
		return sb.toString();
	}

	private static void replaceDistinctWithGroupBy(StringBuilder sql) {
		int distinctIndex = sql.indexOf("distinct");
		if (distinctIndex > 0) {
			sql.delete(distinctIndex, distinctIndex + 9);
			sql.append(" group by").append(getSelectFieldsWithoutAliases(sql));
		}
	}

	private static CharSequence getSelectFieldsWithoutAliases(StringBuilder sql) {
		String select = sql.substring(sql.indexOf("select") + 6, sql.indexOf("from"));
		// Strip the as clauses
		return stripAliases(select);
	}

	private static String stripAliases(String str) {
		return str.replaceAll("\\sas[^,]+(,?)", "$1");
	}

	private static void insertRowNumberFunction(StringBuilder sql, CharSequence orderby) {
		// Find the end of the select statement
		int selectEndIndex = sql.indexOf("select") + 6;
		// Insert after the select statement the row_number() function:
		sql.insert(selectEndIndex, " ROW_NUMBER() OVER (" + orderby + ") as __row_nr__,");
	}

	public boolean supportOffset() {
		return true;
	}

}
