package org.developerworld.commons.dbutils.info.object;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 主键对象
 * 
 * @author Roy Huang
 * @version 20130508
 * 
 */
public class PrimaryKey {

	// 主键名称
	private String name;
	// 主键关联列信息
	private Set<Column> columns = new LinkedHashSet<Column>();
	// 关联该主键的所有外表信息
	private List<Table> foreignTables = new ArrayList<Table>();
	private Set<Set<Column>> foreignColumns= new LinkedHashSet<Set<Column>>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Column> getColumns() {
		return columns;
	}

	public void setColumns(Set<Column> columns) {
		this.columns = columns;
	}

	public List<Table> getForeignTables() {
		return foreignTables;
	}

	public void setForeignTables(List<Table> foreignTables) {
		this.foreignTables = foreignTables;
	}

	public Set<Set<Column>> getForeignColumns() {
		return foreignColumns;
	}

	public void setForeignColumns(Set<Set<Column>> foreignColumns) {
		this.foreignColumns = foreignColumns;
	}

	@Override
	public String toString() {
		return "PrimaryKey [name=" + name + "]";
	}

}
