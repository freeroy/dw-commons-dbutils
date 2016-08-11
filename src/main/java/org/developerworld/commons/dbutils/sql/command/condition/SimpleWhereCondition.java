package org.developerworld.commons.dbutils.sql.command.condition;

import java.util.Collection;

/**
 * 普通条件
 * 
 * @author Roy Huang
 *
 */
public class SimpleWhereCondition implements WhereCondition {

	private String column;
	private Object[] values;
	private SQLWhereLogicOpt opt;

	public String getColumn() {
		return column;
	}

	public Object[] getValues() {
		return values;
	}

	public Object getValue() {
		return getValues() == null || getValues().length <= 0 ? null : getValues()[0];
	}

	public SQLWhereLogicOpt getOpt() {
		return opt;
	}

	public SimpleWhereCondition(String column, Object value, SQLWhereLogicOpt opt) {
		this.column = column;
		this.values = new Object[] { value };
		this.opt = opt;
	}
	
	public SimpleWhereCondition(String column, Object[] values, SQLWhereLogicOpt opt) {
		this.column = column;
		this.values = values;
		this.opt = opt;
	}

	public SimpleWhereCondition(String column, SQLWhereLogicOpt opt) {
		this.column = column;
		this.opt = opt;
	}

	public SimpleWhereCondition(Object value, SQLWhereLogicOpt opt) {
		this.opt = opt;
		this.values = new Object[] { value };
	}

	public static SimpleWhereCondition neq(String column, Object value) {
		return new SimpleWhereCondition(column, value, SQLWhereLogicOpt.NEQ);
	}

	public static SimpleWhereCondition eq(String column, Object value) {
		return new SimpleWhereCondition(column, value, SQLWhereLogicOpt.EQ);
	}

	public static SimpleWhereCondition lt(String column, Object value) {
		return new SimpleWhereCondition(column, value, SQLWhereLogicOpt.LT);
	}

	public static SimpleWhereCondition le(String column, Object value) {
		return new SimpleWhereCondition(column, value, SQLWhereLogicOpt.LE);
	}

	public static SimpleWhereCondition gt(String column, Object value) {
		return new SimpleWhereCondition(column, value, SQLWhereLogicOpt.GT);
	}

	public static SimpleWhereCondition ge(String column, Object value) {
		return new SimpleWhereCondition(column, value, SQLWhereLogicOpt.GE);
	}

	public static SimpleWhereCondition isNull(String column) {
		return new SimpleWhereCondition(column, SQLWhereLogicOpt.IS_NULL);
	}

	public static SimpleWhereCondition isNotNull(String column) {
		return new SimpleWhereCondition(column, SQLWhereLogicOpt.IS_NOT_NULL);
	}

	public static SimpleWhereCondition in(String column, String sql) {
		return new SimpleWhereCondition(column, sql, SQLWhereLogicOpt.IN);
	}

	public static SimpleWhereCondition in(String column, Object[] values) {
		return new SimpleWhereCondition(column, values, SQLWhereLogicOpt.IN);
	}

	public static SimpleWhereCondition in(String column, Collection values) {
		return new SimpleWhereCondition(column, values, SQLWhereLogicOpt.IN);
	}

	public static SimpleWhereCondition notIn(String column, String sql) {
		return new SimpleWhereCondition(column, sql, SQLWhereLogicOpt.NOT_IN);
	}

	public static SimpleWhereCondition notIn(String column, Object[] values) {
		return new SimpleWhereCondition(column, values, SQLWhereLogicOpt.NOT_IN);
	}

	public static SimpleWhereCondition notIn(String column, Collection values) {
		return new SimpleWhereCondition(column, values, SQLWhereLogicOpt.NOT_IN);
	}

	public static SimpleWhereCondition exists(String sql) {
		return new SimpleWhereCondition((Object) sql, SQLWhereLogicOpt.EXISTS);
	}

	public static SimpleWhereCondition notExists(String sql) {
		return new SimpleWhereCondition((Object) sql, SQLWhereLogicOpt.NOT_EXISTS);
	}

	public static SimpleWhereCondition like(String column, String value) {
		return new SimpleWhereCondition(column, value, SQLWhereLogicOpt.LIKE);
	}

	public static SimpleWhereCondition notLike(String column, String value) {
		return new SimpleWhereCondition(column, value, SQLWhereLogicOpt.NOT_LIKE);
	}

	public static SimpleWhereCondition between(String column, Object value1, Object value2) {
		return new SimpleWhereCondition(column, new Object[] { value1, value2 }, SQLWhereLogicOpt.BETWEEN);
	}

	public String buildSQLExpression() {
		String rst = null;
		if ((SQLWhereLogicOpt.IS_NULL.equals(getOpt()) || SQLWhereLogicOpt.IS_NOT_NULL.equals(getOpt()))
				&& getColumn() != null)
			rst = getColumn() + " " + getOpt().value();
		else if ((SQLWhereLogicOpt.EXISTS.equals(getOpt()) || SQLWhereLogicOpt.NOT_EXISTS.equals(getOpt()))
				&& getValues() != null)
			rst = getOpt().value() + " (?)";
		else if (getValues() != null) {
			if (SQLWhereLogicOpt.BETWEEN.equals(getOpt()) && getValues() instanceof Object[]
					&& ((Object[]) getValues()).length == 2)
				rst = getColumn() + " " + getOpt().value() + " ? and ?";
			else if (SQLWhereLogicOpt.LIKE.equals(getOpt()) || SQLWhereLogicOpt.NOT_LIKE.equals(getOpt()))
				rst = getColumn() + " " + getOpt().value() + " ?";
			else if (SQLWhereLogicOpt.IN.equals(getOpt()) || SQLWhereLogicOpt.NOT_IN.equals(getOpt())) {
				int len = 0;
				if (getValue() instanceof String)
					rst = getColumn() + " " + getOpt().value() + " (?)";
				else {
					if (getValue() instanceof Object[])
						len = ((Object[]) getValues()).length;
					else if (getValue() instanceof Collection)
						len = ((Collection) getValue()).size();
					if (len > 0) {
						rst = getColumn() + " " + getOpt().value() + " (";
						for (int i = 0; i < len; i++)
							rst += "?,";
						rst = rst.substring(0, rst.length() - 1) + ")";
					}
				}
			} else
				rst = getColumn() + " " + getOpt().value() + " ?";
		}
		return rst;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		result = prime * result + ((opt == null) ? 0 : opt.hashCode());
		result = prime * result + ((values == null) ? 0 : values.hashCode());
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
		SimpleWhereCondition other = (SimpleWhereCondition) obj;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column))
			return false;
		if (opt != other.opt)
			return false;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SimpleWhereCondition [column=" + column + ", value=" + values + ", opt=" + opt + "]";
	}

}
