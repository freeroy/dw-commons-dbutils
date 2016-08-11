package org.developerworld.commons.dbutils.sql.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.developerworld.commons.dbutils.sql.command.condition.SimpleWhereCondition;
import org.developerworld.commons.dbutils.sql.command.condition.WhereCondition;

/**
 * 普通条件指令
 * 
 * @author Roy Huang
 *
 */
public class SimpleWhereCommand extends AbstractWhereCommand<SimpleWhereCommand, SimpleWhereCondition> {

	public SimpleWhereCommand() {

	}

	public SimpleWhereCommand(SimpleWhereCondition condition) {
		super(condition);
	}

	public SimpleWhereCommand(SimpleWhereCommand command) {
		super(command);
	}

	public Object[] getParameterValues() {
		List<Object> rst = new ArrayList<Object>();
		for (int i = 0; i < conditionList.size(); i++) {
			Object condition = conditionList.get(i);
			if (condition instanceof WhereCondition) {
				Object conditionValueObject = ((WhereCondition) condition).getValues();
				if (conditionValueObject instanceof Object[])
					rst.addAll(Arrays.asList((Object[]) conditionValueObject));
				else if (conditionValueObject instanceof Collection)
					rst.addAll((Collection) conditionValueObject);
			} else if (condition instanceof WhereCommand) {
				WhereCommand _command = ((WhereCommand) condition);
				if (_command.hasWhere())
					rst.addAll(Arrays.asList(_command.getParameterValues()));
			}
		}
		return rst.toArray(new Object[rst.size()]);
	}

	public Map<String, Object> getParameterNameValues() {
		Map<String, Object> rst = new LinkedHashMap<String, Object>();
		Object[] values = getParameterValues();
		for (int i = 0; i < values.length; i++)
			rst.put((i + 1) + "", values[i]);
		return rst;
	}

	@Override
	public String toString() {
		return "SimpleWhereCommand [conditionMap=" + conditionMap + ", conditionList=" + conditionList + "]";
	}

}
