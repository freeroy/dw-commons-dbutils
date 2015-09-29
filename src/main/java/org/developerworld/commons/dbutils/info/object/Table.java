package org.developerworld.commons.dbutils.info.object;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 数据库表对象
 * 
 * @author Roy Huang
 * @version 20130508
 * 
 */
public class Table {

	public Table() {

	}

	public Table(String name) {
		this.name = name;
	}

	public Table(String catalog, String schema, String name) {
		this.catalog = catalog;
		this.schema = schema;
		this.name = name;
	}

	// 数据库
	private String catalog;
	// 空间
	private String schema;
	// 表命
	private String name;
	// 表类型
	private String type;
	//表备注
	private String remarks;
	// 字段信息
	private Set<Column> columns = new LinkedHashSet<Column>();
	// 主键信息
	private PrimaryKey primaryKey;
	// 外键信息
	private Set<ForeignKey> foreignKeys = new HashSet<ForeignKey>();
	// 索引信息
	private Set<Index> indexs = new HashSet<Index>();

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Set<Column> getColumns() {
		return columns;
	}

	public void setColumns(Set<Column> columns) {
		this.columns = columns;
	}

	public PrimaryKey getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(PrimaryKey primary) {
		this.primaryKey = primary;
	}

	public Set<ForeignKey> getForeignKeys() {
		return foreignKeys;
	}

	public void setForeignKeys(Set<ForeignKey> foreignKeys) {
		this.foreignKeys = foreignKeys;
	}

	public Set<Index> getIndexs() {
		return indexs;
	}

	public void setIndexs(Set<Index> indexs) {
		this.indexs = indexs;
	}

	@Override
	public String toString() {
		return "Table [catalog=" + catalog + ", schema=" + schema + ", name="
				+ name + ", type=" + type+ ", remarks=" + remarks + "]";
	}

}
