package org.developerworld.commons.dbutils.sql.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.developerworld.commons.dbutils.sql.command.condition.NamedParameterWhereCondition;
import org.developerworld.commons.dbutils.sql.command.condition.WhereCondition;

/**
 * 命名条件指令
 * 
 * @author Roy Huang
 *
 */
public class NamedParameterWhereCommand
		extends AbstractWhereCommand<NamedParameterWhereCommand, NamedParameterWhereCondition> {

	public NamedParameterWhereCommand() {

	}

	public NamedParameterWhereCommand(NamedParameterWhereCondition condition) {
		and(condition);
	}

	public NamedParameterWhereCommand(NamedParameterWhereCommand command) {
		and(command);
	}

	public Object[] getParameterValues() {
		List<Object> rst = new ArrayList<Object>();
		for (int i = 0; i < conditionList.size(); i++) {
			Object condition = conditionList.get(i);
			if (condition instanceof WhereCondition) {
				Object conditionValueObject = ((WhereCondition) condition).getValue();
				rst.add(conditionValueObject);
			} else if (condition instanceof WhereCommand) {
				WhereCommand _command = ((WhereCommand) condition);
				if (_command.hasWhere())
					rst.addAll(Arrays.asList(_command.getParameterValues()));
			}
		}
		return rst.toArray(new Object[rst.size()]);
	}

	public Map<String, Object> getParameterNameValues() {
		Map<String, Object> rst = new HashMap<String, Object>();
		for (int i = 0; i < conditionList.size(); i++) {
			Object condition = conditionList.get(i);
			if (condition instanceof NamedParameterWhereCondition) {
				NamedParameterWhereCondition _condition = ((NamedParameterWhereCondition) condition);
				if (_condition.getParamNames() != null) {
					for (int j = 0; j < _condition.getParamNames().length; j++)
						rst.put(_condition.getParamNames()[j],
								_condition.getValues() != null && _condition.getValues().length > j
										? _condition.getValues()[j] : null);
				}
			} else if (condition instanceof WhereCommand) {
				WhereCommand _command = ((WhereCommand) condition);
				if (_command.hasWhere())
					rst.putAll(_command.getParameterNameValues());
			}
		}
		return rst;
	}

	@Override
	public String toString() {
		return "NamedParameterWhereCommand [conditionMap=" + conditionMap + ", conditionList=" + conditionList + "]";
	}
	
}
