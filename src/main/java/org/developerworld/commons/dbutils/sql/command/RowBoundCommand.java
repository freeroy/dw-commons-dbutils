package org.developerworld.commons.dbutils.sql.command;

public class RowBoundCommand {

	public RowBoundCommand() {

	}

	public RowBoundCommand(Integer offset, Integer limit) {
		setOffset(offset);
		setLimit(limit);
	}

	public RowBoundCommand(Integer offset) {
		this(offset, null);
	}

	private Integer offset;
	private Integer limit;

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
