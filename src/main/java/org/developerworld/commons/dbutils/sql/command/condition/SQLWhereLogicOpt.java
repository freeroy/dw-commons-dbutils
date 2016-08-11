package org.developerworld.commons.dbutils.sql.command.condition;

public enum SQLWhereLogicOpt {

	IS_NULL("is null"), IS_NOT_NULL("is not null"), NEQ("<>"), EQ("="), LT("<"), LE("<="), GT(">"), GE(">="), IN(
			"in"), NOT_IN("not in"), EXISTS("exists"), NOT_EXISTS("not exists"), LIKE("like"), NOT_LIKE(
					"not like"), BETWEEN("between");

	private String value;

	private SQLWhereLogicOpt(String value) {
		this.value = value;
	}
	
	public String value(){
		return value;
	}

	@Override
	public String toString() {
		return this.value;
	}
}