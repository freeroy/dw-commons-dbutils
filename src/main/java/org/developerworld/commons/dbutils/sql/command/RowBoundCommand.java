package org.developerworld.commons.dbutils.sql.command;

import org.developerworld.commons.dbutils.sql.dialect.rowbound.RowBoundDialect;

public class RowBoundCommand {

	private Integer offset;
	private Integer limit;

	public RowBoundCommand() {

	}

	public RowBoundCommand(Integer offset, Integer limit) {
		setOffset(offset);
		setLimit(limit);
	}

	public RowBoundCommand(Integer offset) {
		this(offset, null);
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public RowBoundCommand offset(Integer offset) {
		setOffset(offset);
		return this;
	}

	public RowBoundCommand limit(Integer limit) {
		setLimit(limit);
		return this;
	}

	/**
	 * 构建分页语句
	 * 
	 * @param sql
	 * @param rowBoundDialect
	 * @return
	 */
	public String buildSql(String sql, RowBoundDialect rowBoundDialect) {
		if (offset != null && !rowBoundDialect.supportOffset())
			throw new RuntimeException("the dialect unsupport offset!");
		return rowBoundDialect.buildRowBoundSql(sql, limit, offset);
	}

	public boolean hasOffset() {
		return getOffset() != null;
	}

	public boolean hasLimit() {
		return getLimit() != null;
	}

	/**
	 * 是否设置完整分页信息
	 * 
	 * @return
	 */
	public boolean hasRowBound() {
		return getOffset() != null && getLimit() != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((limit == null) ? 0 : limit.hashCode());
		result = prime * result + ((offset == null) ? 0 : offset.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RowBoundCommand other = (RowBoundCommand) obj;
		if (limit == null) {
			if (other.limit != null)
				return false;
		} else if (!limit.equals(other.limit))
			return false;
		if (offset == null) {
			if (other.offset != null)
				return false;
		} else if (!offset.equals(other.offset))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RowBoundCommand [offset=" + offset + ", limit=" + limit + "]";
	}

}
