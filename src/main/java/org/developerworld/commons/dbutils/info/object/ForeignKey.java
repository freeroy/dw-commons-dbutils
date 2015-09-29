package org.developerworld.commons.dbutils.info.object;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 外键数据对象
 * 
 * @author Roy Huang
 * @version 20130509
 * 
 */
public class ForeignKey {

	// 主键名称
	private String name;
	// 单签表的关联列信息
	private Set<Column> columns = new LinkedHashSet<Column>();
	//关联的主表键
	private Set<Column> primaryColumns=new LinkedHashSet<Column>();
	// 关联的主表信息
	private Table primaryTable;

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

	public Table getPrimaryTable() {
		return primaryTable;
	}

	public void setPrimaryTable(Table primaryTable) {
		this.primaryTable = primaryTable;
	}

	public Set<Column> getPrimaryColumns() {
		return primaryColumns;
	}

	public void setPrimaryColumns(Set<Column> primaryColumns) {
		this.primaryColumns = primaryColumns;
	}

	@Override
	public String toString() {
		return "ForeignKey [name=" + name + "]";
	}

}
