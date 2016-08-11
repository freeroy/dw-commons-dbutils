package org.developerworld.commons.dbutils.sql.command.condition;

import java.util.Arrays;
import java.util.Collection;

/**
 * 命名动态参数条件
 * 
 * @author Roy Huang
 *
 */
public class NamedParameterWhereCondition implements WhereCondition {

	private String column;
	private Object[] values;
	private SQLWhereLogicOpt opt;
	private String[] paramNames;

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

	public String[] getParamNames() {
		return paramNames;
	}

	public String getParamName() {
		return getParamNames() == null || getParamNames().length <= 0 ? null : getParamNames()[0];
	}

	public NamedParameterWhereCondition(String column, SQLWhereLogicOpt opt) {
		this.column = column;
		this.opt = opt;
	}

	public NamedParameterWhereCondition(String column, String paramName, Object value, SQLWhereLogicOpt opt) {
		this.column = column;
		this.opt = opt;
		this.paramNames = new String[] { paramName };
		this.values = new Object[] { value };
	}

	public NamedParameterWhereCondition(String paramName, Object value, SQLWhereLogicOpt opt) {
		this.opt = opt;
		this.paramNames = new String[] { paramName };
		this.values = new Object[] { value };
	}

	public NamedParameterWhereCondition(String column, String[] paramName, Object[] values, SQLWhereLogicOpt opt) {
		this.column = column;
		this.opt = opt;
		this.paramNames = paramName;
		this.values = values;
	}

	public static NamedParameterWhereCondition isNull(String column) {
		return new NamedParameterWhereCondition(column, SQLWhereLogicOpt.IS_NULL);
	}

	public static NamedParameterWhereCondition isNotNull(String column) {
		return new NamedParameterWhereCondition(column, SQLWhereLogicOpt.IS_NOT_NULL);
	}

	public static NamedParameterWhereCondition ne(String column, String paramName, Object value) {
		return new NamedParameterWhereCondition(column, paramName, value, SQLWhereLogicOpt.NEQ);
	}

	public static NamedParameterWhereCondition eq(String column, String paramName, Object value) {
		return new NamedParameterWhereCondition(column, paramName, value, SQLWhereLogicOpt.EQ);
	}

	public static NamedParameterWhereCondition lt(String column, String paramName, Object value) {
		return new NamedParameterWhereCondition(column, paramName, value, SQLWhereLogicOpt.LT);
	}

	public static NamedParameterWhereCondition le(String column, String paramName, Object value) {
		return new NamedParameterWhereCondition(column, paramName, value, SQLWhereLogicOpt.LE);
	}

	public static NamedParameterWhereCondition gt(String column, String paramName, Object value) {
		return new NamedParameterWhereCondition(column, paramName, value, SQLWhereLogicOpt.GT);
	}

	public static NamedParameterWhereCondition ge(String column, String paramName, Object value) {
		return new NamedParameterWhereCondition(column, paramName, value, SQLWhereLogicOpt.GE);
	}

	public static NamedParameterWhereCondition in(String column, String paramName, String sql) {
		return new NamedParameterWhereCondition(column, paramName, sql, SQLWhereLogicOpt.IN);
	}

	public static NamedParameterWhereCondition in(String column, String paramName, Object[] values) {
		return new NamedParameterWhereCondition(column, paramName, values, SQLWhereLogicOpt.IN);
	}

	public static NamedParameterWhereCondition in(String column, String paramName, Collection values) {
		return new NamedParameterWhereCondition(column, paramName, values, SQLWhereLogicOpt.IN);
	}

	public static NamedParameterWhereCondition notIn(String column, String paramName, String sql) {
		return new NamedParameterWhereCondition(column, paramName, sql, SQLWhereLogicOpt.NOT_IN);
	}

	public static NamedParameterWhereCondition notIn(String column, String paramName, Object[] values) {
		return new NamedParameterWhereCondition(column, paramName, values, SQLWhereLogicOpt.NOT_IN);
	}

	public static NamedParameterWhereCondition notIn(String column, String paramName, Collection values) {
		return new NamedParameterWhereCondition(column, paramName, values, SQLWhereLogicOpt.NOT_IN);
	}

	public static NamedParameterWhereCondition exists(String paramName, String sql) {
		return new NamedParameterWhereCondition(paramName, (Object) sql, SQLWhereLogicOpt.EXISTS);
	}

	public static NamedParameterWhereCondition notExists(String paramName, String sql) {
		return new NamedParameterWhereCondition(paramName, (Object) sql, SQLWhereLogicOpt.NOT_EXISTS);
	}

	public static NamedParameterWhereCondition like(String column, String paramName, String value) {
		return new NamedParameterWhereCondition(column, paramName, value, SQLWhereLogicOpt.LIKE);
	}

	public static NamedParameterWhereCondition notLike(String column, String paramName, String value) {
		return new NamedParameterWhereCondition(column, paramName, value, SQLWhereLogicOpt.NOT_LIKE);
	}

	public static NamedParameterWhereCondition between(String column, String paramName1, Object value1,
			String paramName2, Object value2) {
		return new NamedParameterWhereCondition(column, new String[] { paramName1, paramName2 },
				new Object[] { value1, value2 }, SQLWhereLogicOpt.BETWEEN);
	}

	public String buildSQLExpression() {
		String rst = null;
		if ((SQLWhereLogicOpt.IS_NULL.equals(getOpt()) || SQLWhereLogicOpt.IS_NOT_NULL.equals(getOpt()))
				&& getColumn() != null)
			rst = getColumn() + " " + getOpt().value();
		else {
			String[] paramNames = getParamNames();
			if (paramNames != null && paramNames.length > 0) {
				if ((SQLWhereLogicOpt.EXISTS.equals(getOpt()) || SQLWhereLogicOpt.NOT_EXISTS.equals(getOpt()))
						&& getValue() != null)
					rst = getOpt().value() + " (" + paramNames[0] + ")";
				else if (getColumn() != null && getValues() != null) {
					if (SQLWhereLogicOpt.BETWEEN.equals(getOpt()) && paramNames.length == 2 && getValues().length == 2)
						rst = getColumn() + " " + getOpt().value() + " " + paramNames[0] + " and " + paramNames[1];
					else if (SQLWhereLogicOpt.LIKE.equals(getOpt()) || SQLWhereLogicOpt.NOT_LIKE.equals(getOpt()))
						rst = getColumn() + " " + getOpt().value() + " " + paramNames[0];
					else if (SQLWhereLogicOpt.IN.equals(getOpt()) || SQLWhereLogicOpt.NOT_IN.equals(getOpt()))
						rst = getColumn() + " " + getOpt().value() + " (" + paramNames[0] + ")";
					else
						rst = getColumn() + " " + getOpt().value() + " " + paramNames[0];
				}
			}
		}
		return rst;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		result = prime * result + ((opt == null) ? 0 : opt.hashCode());
		result = prime * result + Arrays.hashCode(paramNames);
		result = prime * result + Arrays.hashCode(values);
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
		NamedParameterWhereCondition other = (NamedParameterWhereCondition) obj;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column))
			return false;
		if (opt != other.opt)
			return false;
		if (!Arrays.equals(paramNames, other.paramNames))
			return false;
		if (!Arrays.equals(values, other.values))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NamedParameterWhereCondition [column=" + column + ", values=" + Arrays.toString(values) + ", opt=" + opt
				+ ", paramNames=" + Arrays.toString(paramNames) + "]";
	}

}
