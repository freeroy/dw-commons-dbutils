package org.developerworld.commons.dbutils.sql.command;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.developerworld.commons.dbutils.sql.command.condition.WhereCondition;

/**
 * 条件指令抽象类
 * 
 * @author Roy Huang
 *
 */
public abstract class AbstractWhereCommand<T extends WhereCommand<T, E>, E extends WhereCondition>
		implements WhereCommand<T, E> {

	private final static Byte AND = 0;
	private final static Byte OR = 1;

	protected Map<Integer, Byte> conditionMap = new LinkedHashMap<Integer, Byte>();
	protected List<Object> conditionList = new ArrayList<Object>();

	public AbstractWhereCommand() {

	}

	public AbstractWhereCommand(E condition) {
		and(condition);
	}

	public AbstractWhereCommand(T command) {
		and(command);
	}

	public T and(E condition) {
		conditionMap.put(conditionList.size(), AND);
		conditionList.add(condition);
		return (T) this;
	}

	public T and(T command) {
		conditionMap.put(conditionList.size(), AND);
		conditionList.add(command);
		return (T) this;
	}

	public T or(E condition) {
		conditionMap.put(conditionList.size(), OR);
		conditionList.add(condition);
		return (T) this;
	}

	public T or(T command) {
		conditionMap.put(conditionList.size(), OR);
		conditionList.add(command);
		return (T) this;
	}

	/**
	 * 构建sql语句
	 * 
	 * @return
	 */
	public String buildSql() {
		String rst = buildSqlWithOutWhere();
		if (rst.length() > 0)
			rst = " where " + rst;
		return rst;
	}

	/**
	 * 构建不包含where的sql语句
	 * 
	 * @return
	 */
	public String buildSqlWithOutWhere() {
		StringBuilder rst = new StringBuilder();
		for (int i = 0; i < conditionList.size(); i++) {
			Object condition = conditionList.get(i);
			Byte type = conditionMap.get(i);
			if (type.equals(AND))
				rst.append(" and ");
			else
				rst.append(" or ");
			if (condition instanceof WhereCondition) {
				rst.append(((WhereCondition) condition).buildSQLExpression());
			} else if (condition instanceof WhereCommand) {
				WhereCommand _command = ((WhereCommand) condition);
				if (_command.hasWhere())
					rst.append("(").append(_command.buildSqlWithOutWhere()).append(")");
			}
		}
		// 删除前缀
		if (conditionMap.get(0).equals(AND))
			rst = rst.delete(0, 5);
		else
			rst = rst.delete(0, 4);
		return rst.toString();
	}

	/**
	 * 是否有条件
	 * 
	 * @return
	 */
	public boolean hasWhere() {
		return conditionList.size() > 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conditionList == null) ? 0 : conditionList.hashCode());
		result = prime * result + ((conditionMap == null) ? 0 : conditionMap.hashCode());
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
		AbstractWhereCommand other = (AbstractWhereCommand) obj;
		if (conditionList == null) {
			if (other.conditionList != null)
				return false;
		} else if (!conditionList.equals(other.conditionList))
			return false;
		if (conditionMap == null) {
			if (other.conditionMap != null)
				return false;
		} else if (!conditionMap.equals(other.conditionMap))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AbstractWhereCommand [conditionMap=" + conditionMap + ", conditionList=" + conditionList + "]";
	}

}
