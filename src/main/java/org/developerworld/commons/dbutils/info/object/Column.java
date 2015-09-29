package org.developerworld.commons.dbutils.info.object;

/**
 * 数据库表字段对象
 * 
 * @author Roy Huang
 * @version 20130508
 * 
 */
public class Column {

	// 字段名
	private String name;
	// 字段类型
	private int type;
	//类型名称
	private String typeName;
	// 字段长度
	private int length;
	// 小数点后的位数
	private int decimalDigits;
	// 是否自增值字段
	private boolean isAutoIncrement;
	// 是否能为空
	private boolean isNullable;
	// 默认值
	private String defaultValue;
	// 备注
	private String remarks;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isAutoIncrement() {
		return isAutoIncrement;
	}

	public void setAutoIncrement(boolean isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}

	public boolean isNullable() {
		return isNullable;
	}

	public void setNullable(boolean isNullable) {
		this.isNullable = isNullable;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public int getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setTypeName(String typeName) {
		this.typeName=typeName;
	}
	
	public String getTypeName(){
		return typeName;
	}

	@Override
	public String toString() {
		return "Column [name=" + name + ", type=" + type+ ", typeName=" + typeName + ", length=" + length
				+ ", decimalDigits=" + decimalDigits + ", isAutoIncrement="
				+ isAutoIncrement + ", isNullable=" + isNullable
				+ ", defaultValue=" + defaultValue + ", remarks=" + remarks
				+ "]";
	}

}
