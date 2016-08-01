package org.developerworld.commons.dbutils.sql.dialect.rowbound.impl;

public class DerbyRowBoundDialect extends AbstractRowBoundDialect {

	public String buildRowBoundSql(String sql, Integer limit, Integer offset) {
		if (isErrorArgs(sql, limit, offset))
			return sql;
		StringBuffer sb = new StringBuffer();
		String normalizedSelect = sql.toLowerCase().trim();
		int forUpdateIndex = normalizedSelect.lastIndexOf("for update");
		if (hasForUpdateClause(forUpdateIndex))
			sb.append(sql.substring(0, forUpdateIndex - 1));
		else if (hasWithClause(normalizedSelect))
			sb.append(sql.substring(0, getWithIndex(sql) - 1));
		else
			sb.append(sql);
		if (offset == null || offset <= 0)
			sb.append(" fetch first ").append(limit).append(" rows only");
		else
			sb.append(" offset ").append(offset).append(" rows fetch next ").append(limit).append(" rows only");
		if (hasForUpdateClause(forUpdateIndex)) {
			sb.append(" ");
			sb.append(sql.substring(forUpdateIndex));
		} else if (hasWithClause(normalizedSelect))
			sb.append(" ").append(sql.substring(getWithIndex(sql)));
		return sb.toString();
	}

	private boolean hasForUpdateClause(int forUpdateIndex) {
		return forUpdateIndex >= 0;
	}

	private boolean hasWithClause(String normalizedSelect) {
		return normalizedSelect.toLowerCase().startsWith("with ", normalizedSelect.length() - 7);
	}

	private int getWithIndex(String querySelect) {
		return querySelect.toLowerCase().lastIndexOf("with ");
	}

	public boolean supportOffset() {
		return true;
	}

}
