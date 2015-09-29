package org.developerworld.commons.dbutils.info.object;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 索引对象
 * 
 * @author Roy Huang
 * @version 20130606
 * 
 */

public class Index {

	private String name;
	// 关联列信息
	private Set<Column> columns = new LinkedHashSet<Column>();
	// 关联列的排序方式
	private List<String> columnSortords = new ArrayList<String>();
	// 是否唯一
	private boolean isUnique;

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

	public List<String> getColumnSortords() {
		return columnSortords;
	}

	public void setColumnSortords(List<String> columnSortords) {
		this.columnSortords = columnSortords;
	}

	public boolean isUnique() {
		return isUnique;
	}

	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}

	@Override
	public String toString() {
		return "Index [name=" + name + ", isUnique=" + isUnique + "]";
	}

}
