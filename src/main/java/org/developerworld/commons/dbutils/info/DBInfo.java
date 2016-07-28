package org.developerworld.commons.dbutils.info;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.developerworld.commons.dbutils.info.object.Column;
import org.developerworld.commons.dbutils.info.object.Database;
import org.developerworld.commons.dbutils.info.object.ForeignKey;
import org.developerworld.commons.dbutils.info.object.Index;
import org.developerworld.commons.dbutils.info.object.PrimaryKey;
import org.developerworld.commons.dbutils.info.object.Table;

/**
 * 数据库表信息对象
 * 
 * @author Roy Huang
 * @version 20130613
 * 
 */
public class DBInfo {

	private Connection connection;
	private Map<String, Table> tableInfoCaches = new HashMap<String, Table>();

	public DBInfo(Connection connection) {
		this.connection = connection;
	}
	
	/**
	 * 获取数据库信息
	 * @return
	 * @throws Exception
	 */
	public Database getDatabaseInfo() throws Exception{
		Database rst=new Database();
		DatabaseMetaData databaseMetaData=connection.getMetaData();
		rst.setProductName(databaseMetaData.getDatabaseProductName());
		rst.setProductVersion(databaseMetaData.getDatabaseProductVersion());
		rst.setMajorVersion(databaseMetaData.getDatabaseMajorVersion());
		rst.setMinorVersion(databaseMetaData.getDatabaseMinorVersion());
		return rst;
	}

	/**
	 * 获取表信息缓存
	 * 
	 * @param catalog
	 * @param schema
	 * @param table
	 * @return
	 */
	private Table getTableInfoCache(String catalog, String schema, String table) {
		return tableInfoCaches.get("catalog:" + catalog + ",schema:" + schema
				+ ",table:" + table);
	}

	/**
	 * 设置获取表信息缓存
	 * 
	 * @param table
	 */
	private void setTableInfoCache(Table table) {
		tableInfoCaches.put("catalog:" + table.getCatalog() + ",schema:"
				+ table.getSchema() + ",table:" + table.getName(), table);
	}

	/**
	 * 获取所有数据库表的对象信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public Set<Table> getAllTableInfos() throws Exception {
		return getAllTableInfos(null, null);
	}

	/**
	 * 获取所有数据库表的对象信息
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public Set<Table> getAllTableInfos(TablenameFilter filter) throws Exception {
		return getAllTableInfos(null, null, filter);
	}

	/**
	 * 获取指定catalog和schema的表信息
	 * 
	 * @param dbCataLog
	 * @param dbSchema
	 * @return
	 * @throws Exception
	 */
	public Set<Table> getAllTableInfos(String dbCataLog, String dbSchema)
			throws Exception {
		return getAllTableInfos(dbCataLog, dbSchema, null);
	}

	/**
	 * 获取指定catalog和schema的表信息
	 * 
	 * @param dbCataLog
	 * @param dbSchema
	 * @return
	 * @throws Exception
	 */
	public Set<Table> getAllTableInfos(String dbCataLog, String dbSchema,
			TablenameFilter filter) throws Exception {
		final Set<String> tableNames = new HashSet<String>();
		// 获取数据库描述信息
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		// 读取全部表信息
		ResultSet resultSet = databaseMetaData.getTables(dbCataLog, dbSchema,
				null, null);
		try {
			// 获取数据库表名
			while (resultSet.next()) {
				if (filter == null
						|| filter.accept(resultSet.getString("TABLE_NAME")))
					tableNames.add(resultSet.getString("TABLE_NAME"));
			}
		} finally {
			if (resultSet != null)
				resultSet.close();
		}
		return getTableInfos(dbCataLog, dbSchema, tableNames);
	}

	/**
	 * 获取指定数据库表的独享信息
	 * 
	 * @param tableNames
	 * @return
	 * @throws Exception
	 */
	public Set<Table> getTableInfos(final Set<String> tableNames)
			throws Exception {
		return getTableInfos(null, null, tableNames);
	}

	/**
	 * 获取指定dbCataLog和dbschema的表信息
	 * 
	 * @param dbCataLog
	 * @param dbSchema
	 * @param tableNames
	 * @return
	 * @throws Exception
	 */
	public Set<Table> getTableInfos(String dbCataLog, String dbSchema,
			Set<String> tableNames) throws Exception {
		final Set<Table> rst = new HashSet<Table>();
		for (String tableName : tableNames)
			rst.add(getTableInfo(dbCataLog, dbSchema, tableName));
		return rst;
	}

	/**
	 * 获取数据库表对象信息
	 * 
	 * @param catalog
	 * @param schema
	 * @param table
	 * 
	 * @return
	 * @throws Exception
	 */
	public Table getTableInfo(String catalog, String schema, String table)
			throws Exception {
		// 若存在表信息缓存，直接返回
		Table _table = getTableInfoCache(catalog, schema, table);
		if (_table != null)
			return _table;
		// 创建新的表信息对象，并预先放入缓存
		final Table rst = new Table(catalog, schema, table);
		setTableInfoCache(rst);
		try {
			// 填充表的基础数据
			fillTableBaseData(connection, rst);
			// 填充表的字段信息
			fillTableColumnData(connection, rst);
			// 填充表主键信息
			fillTablePrimaryKeyData(connection, rst);
			// 填充表外键信息
			fillTableForeignKeyData(connection, rst);
			// 填充索引信息
			fillIndexData(connection, rst);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rst;
	}

	/**
	 * 填充表的基础数据
	 * 
	 * @param connection
	 * @param table
	 * @throws SQLException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void fillTableBaseData(Connection connection, Table table)
			throws SQLException, IllegalAccessException,
			InvocationTargetException {
		// 获取数据库描述信息
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet resultSet = databaseMetaData.getTables(table.getCatalog(),
				table.getSchema(), table.getName(), null);
		try {
			if (resultSet.next()) {
				table.setSchema(resultSet.getString("TABLE_SCHEM"));
				table.setCatalog(resultSet.getString("TABLE_CAT"));
				table.setName(resultSet.getString("TABLE_NAME"));
				table.setType(resultSet.getString("TABLE_TYPE"));
				table.setRemarks(resultSet.getString("REMARKS"));
			}
		} finally {
			if (resultSet != null)
				resultSet.close();
		}
	}

	/**
	 * 填充表字段信息
	 * 
	 * @param connection
	 * @param table
	 * 
	 * @throws SQLException
	 */
	private void fillTableColumnData(Connection connection, Table table)
			throws SQLException {
		// 获取数据库描述信息
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet resultSet = databaseMetaData.getColumns(table.getCatalog(),
				table.getSchema(), table.getName(), null);
		try {
			while (resultSet.next()) {
				Column column = new Column();
				column.setName(resultSet.getString("COLUMN_NAME"));
				column.setType(resultSet.getInt("DATA_TYPE"));
				column.setTypeName(resultSet.getString("TYPE_NAME"));
				column.setLength(resultSet.getInt("COLUMN_SIZE"));
				column.setRemarks(resultSet.getString("REMARKS"));
				column.setNullable(resultSet.getInt("NULLABLE") == 1);
				// 部分数据库不支持以下字段
				try {
					column.setAutoIncrement(resultSet
							.getString("IS_AUTOINCREMENT").trim()
							.equalsIgnoreCase("YES"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				column.setDefaultValue(resultSet.getString("COLUMN_DEF"));
				column.setDecimalDigits(resultSet.getInt("DECIMAL_DIGITS"));
				table.getColumns().add(column);
			}
		} finally {
			if (resultSet != null)
				resultSet.close();
		}
	}

	/**
	 * 设置表的主键信息
	 * 
	 * @param connection
	 * @param table
	 * @throws Exception
	 */
	private void fillTablePrimaryKeyData(Connection connection, Table table)
			throws Exception {
		// 获取数据库描述信息
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		PrimaryKey primaryKey = new PrimaryKey();
		Map<String, String> primaryKeyMap = new HashMap<String, String>();
		{
			ResultSet resultSet = databaseMetaData.getPrimaryKeys(
					table.getCatalog(), table.getSchema(), table.getName());
			try {
				while (resultSet.next()) {
					primaryKey.setName(resultSet.getString("PK_NAME"));
					String columnName = resultSet.getString("COLUMN_NAME");
					short order = resultSet.getShort("KEY_SEQ");
					primaryKeyMap.put(order + "", columnName);
				}
			} finally {
				if (resultSet != null)
					resultSet.close();
			}
		}
		// 若大于0，代表有主键信息
		if (primaryKeyMap.size() > 0) {
			for (int i = 0; i <= primaryKeyMap.size(); i++) {
				if (!primaryKeyMap.containsKey(i + ""))
					continue;
				String primaryKeyColumn = primaryKeyMap.get(i + "");
				for (Column column : table.getColumns()) {
					if (column.getName().equalsIgnoreCase(primaryKeyColumn)) {
						primaryKey.getColumns().add(column);
						break;
					}
				}
			}
			// 获取关联了该主键的外表信息
			ResultSet resultSet = databaseMetaData.getExportedKeys(
					table.getCatalog(), table.getSchema(), table.getName());
			try {
				Map<String, Map<String, String>> fkTableMaps = new HashMap<String, Map<String, String>>();
				while (resultSet.next()) {
					String fkCatalog = resultSet.getString("FKTABLE_CAT");
					String fkSchema = resultSet.getString("FKTABLE_SCHEM");
					String fkTable = resultSet.getString("FKTABLE_NAME");
					String fkColumn = resultSet.getString("FKCOLUMN_NAME");
					short fkSeq = resultSet.getShort("KEY_SEQ");
					if (!fkTableMaps.containsKey(fkCatalog + "$_$" + fkSchema
							+ "$_$" + fkTable)) {
						fkTableMaps.put(fkCatalog + "$_$" + fkSchema + "$_$"
								+ fkTable, new HashMap<String, String>());
					}
					fkTableMaps.get(
							fkCatalog + "$_$" + fkSchema + "$_$" + fkTable)
							.put(fkSeq + "", fkColumn);
				}
				if (fkTableMaps.size() > 0) {
					Iterator<Entry<String, Map<String, String>>> iterator = fkTableMaps
							.entrySet().iterator();
					while (iterator.hasNext()) {
						Entry<String, Map<String, String>> e = iterator.next();
//						String fkCatalog = e.getKey().split("\\$_\\$")[0];
//						String fkSchema = e.getKey().split("\\$_\\$")[1];
						String fkTable = e.getKey().split("\\$_\\$")[2];
						// 获取外键表信息
						// XXX
						// 该处由于部分数据库支持问题，不能使用catalog、schema限定条件，否则返回不了正确数据（原因未知，具体呈现见DB2数据获取）
						// Table _table = getTableInfo(fkCatalog,
						// fkSchema,fkTable);
						Table _table = getTableInfo(null, null, fkTable);
						// 为主键对象，添加关联外键表属性
						if (_table != null) {
							primaryKey.getForeignTables().add(_table);
							Set<Column> foreignColumns = new LinkedHashSet<Column>();
							Map<String, String> fkTableColumns = e.getValue();
							// 填充关联外表键
							for (int i = 0; i <= fkTableColumns.size(); i++) {
								if (!fkTableColumns.containsKey(i + ""))
									continue;
								for (Column column : _table.getColumns()) {
									if (column.getName().equals(
											fkTableColumns.get(i + ""))) {
										foreignColumns.add(column);
										break;
									}
								}
							}
							// 添加至记录集合
							primaryKey.getForeignColumns().add(foreignColumns);
						}
					}
				}
			} finally {
				if (resultSet != null)
					resultSet.close();
			}
			table.setPrimaryKey(primaryKey);
		}
	}

	/**
	 * 这只表的外键信息
	 * 
	 * @param connection
	 * @param table
	 * @throws Exception
	 */
	private void fillTableForeignKeyData(Connection connection, Table table)
			throws Exception {
		Map<String, Map<String, String[]>> foreignKeysMap = new HashMap<String, Map<String, String[]>>();
		// 获取数据库描述信息
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		// 获取所有外键信息
		ResultSet resultSet = databaseMetaData.getImportedKeys(
				table.getCatalog(), table.getSchema(), table.getName());
		try {
			while (resultSet.next()) {
				String fkName = resultSet.getString("FK_NAME");
				String catalog = resultSet.getString("PKTABLE_CAT");
				String schema = resultSet.getString("PKTABLE_SCHEM");
				String tableName = resultSet.getString("PKTABLE_NAME");
				String fkColumn = resultSet.getString("FKCOLUMN_NAME");
				String pkColumn = resultSet.getString("PKCOLUMN_NAME");
				short seq = resultSet.getShort("KEY_SEQ");
				// 若没设置则先添加基础数据
				if (!foreignKeysMap.containsKey(fkName)) {
					foreignKeysMap.put(fkName, new HashMap<String, String[]>());
					foreignKeysMap.get(fkName)
							.put("info",
									new String[] {
											(catalog == null ? "" : catalog),
											(schema == null ? "" : schema),
											tableName });
				}
				foreignKeysMap.get(fkName).put(seq + "",
						new String[] { fkColumn, pkColumn });
			}
		} finally {
			if (resultSet != null)
				resultSet.close();
		}
		// 存在外键信息
		if (foreignKeysMap.size() > 0) {
			// 迭代每一个外键
			Iterator<Entry<String, Map<String, String[]>>> iterator = foreignKeysMap
					.entrySet().iterator();
			while (iterator.hasNext()) {
				ForeignKey foreignKey = new ForeignKey();
				Entry<String, Map<String, String[]>> e = iterator.next();
				// 获取单个外键信息
				String fkName = e.getKey();
				// 设置键名
				foreignKey.setName(fkName);
				Map<String, String[]> foreignKeyMap = e.getValue();
//				String catalog = foreignKeyMap.get("info")[0];
//				String schema = foreignKeyMap.get("info")[1];
				String tableName = foreignKeyMap.get("info")[2];
				// 获取外键关联数据库表信息
				// XXX
				// 该处由于部分数据库支持问题，不能使用catalog、schema限定条件，否则返回不了正确数据（原因未知，具体呈现见DB2数据获取）
				// Table _table = getTableInfo(catalog, schema, tableName);
				Table _table = getTableInfo(null, null, tableName);
				foreignKey.setPrimaryTable(_table);
				// 对字段进行重新排序
				for (int i = 0; i <= foreignKeyMap.size(); i++) {
					if (!foreignKeyMap.containsKey(i + ""))
						continue;
					String fkColumn = foreignKeyMap.get(i + "")[0];
					String pkColumn = foreignKeyMap.get(i + "")[1];
					// 添加本表的键列
					for (Column column : table.getColumns()) {
						if (column.getName().equalsIgnoreCase(fkColumn)) {
							foreignKey.getColumns().add(column);
							break;
						}
					}
					// 添加外键表的键列
					for (Column column : _table.getColumns()) {
						if (column.getName().equalsIgnoreCase(pkColumn)) {
							foreignKey.getPrimaryColumns().add(column);
							break;
						}
					}
				}
				table.getForeignKeys().add(foreignKey);
			}
		}
	}

	/**
	 * 填充唯一键信息
	 * 
	 * @param connection
	 * @param rst
	 * @throws SQLException
	 */
	private void fillIndexData(Connection connection, Table table)
			throws SQLException {
		Map<String, Map<String, String[]>> indexsMap = new HashMap<String, Map<String, String[]>>();
		// 获取数据库描述信息
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		// 获取所有外键信息
		ResultSet resultSet = databaseMetaData.getIndexInfo(table.getCatalog(),
				table.getSchema(), table.getName(), false, false);
		try {
			while (resultSet.next()) {
				String indexName = resultSet.getString("INDEX_NAME");
				// if(StringUtils.isBlank(indexName))
				// continue;
				// 若索引名称等于主键名称，则跳过
				if (table.getPrimaryKey() != null
						&& table.getPrimaryKey().getName()
								.equalsIgnoreCase(indexName))
					continue;
				boolean isUnique = !resultSet.getBoolean("NON_UNIQUE");
				String sortords = resultSet.getString("ASC_OR_DESC");
				String columnName = resultSet.getString("COLUMN_NAME");
				// 部分数据库（如sqlserver2008），会获取到不包含任何字段的索引信息，暂时原因未明，因此这里判断如果不含字段名信息，则跳过）
				if (columnName == null || columnName.trim().length() <= 0)
					continue;
				short seq = resultSet.getShort("ORDINAL_POSITION");
				if (!indexsMap.containsKey(indexName)) {
					indexsMap.put(indexName, new HashMap<String, String[]>());
					indexsMap.get(indexName).put("isUnique",
							new String[] { isUnique + "" });
				}
				indexsMap.get(indexName).put(seq + "",
						new String[] { columnName, sortords });
			}
		} finally {
			if (resultSet != null)
				resultSet.close();
		}
		// 存在索引
		if (indexsMap.size() > 0) {
			// 迭代每一个索引
			Iterator<Entry<String, Map<String, String[]>>> iterator = indexsMap
					.entrySet().iterator();
			while (iterator.hasNext()) {
				Index index = new Index();
				// 获取索引信息
				Entry<String, Map<String, String[]>> e = iterator.next();
				String indexName = e.getKey();
				index.setName(indexName);
				Map<String, String[]> indexMap = e.getValue();
				boolean isUnique = Boolean.parseBoolean(indexMap
						.get("isUnique")[0]);
				index.setUnique(isUnique);
				for (int i = 0; i <= indexMap.size(); i++) {
					if (!indexMap.containsKey(i + ""))
						continue;
					String[] datas = indexMap.get(i + "");
					String columnName = datas[0];
					String sortords = datas[1];
					for (Column column : table.getColumns()) {
						if (column.getName().equalsIgnoreCase(columnName)) {
							index.getColumns().add(column);
							index.getColumnSortords().add(
									sortords.equalsIgnoreCase("A") ? "ASC"
											: "DESC");
							break;
						}
					}
				}
				// 记录索引信息到表对象
				table.getIndexs().add(index);
			}
		}
	}
}
