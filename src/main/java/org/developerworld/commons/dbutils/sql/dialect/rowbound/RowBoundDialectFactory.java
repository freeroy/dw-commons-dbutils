package org.developerworld.commons.dbutils.sql.dialect.rowbound;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.developerworld.commons.dbutils.sql.dialect.rowbound.impl.DB2RowBoundDialect;
import org.developerworld.commons.dbutils.sql.dialect.rowbound.impl.DerbyRowBoundDialect;
import org.developerworld.commons.dbutils.sql.dialect.rowbound.impl.H2RowBoundDialect;
import org.developerworld.commons.dbutils.sql.dialect.rowbound.impl.HypersonicSQLRowBoundDialect;
import org.developerworld.commons.dbutils.sql.dialect.rowbound.impl.InformixRowBoundDialect;
import org.developerworld.commons.dbutils.sql.dialect.rowbound.impl.IngresRowBoundDialect;
import org.developerworld.commons.dbutils.sql.dialect.rowbound.impl.Ingres_L9RowBoundDialect;
import org.developerworld.commons.dbutils.sql.dialect.rowbound.impl.MySqlRowBoundDialect;
import org.developerworld.commons.dbutils.sql.dialect.rowbound.impl.OracleRowBoundDialect;
import org.developerworld.commons.dbutils.sql.dialect.rowbound.impl.PostgreSQLRowBoundDialect;
import org.developerworld.commons.dbutils.sql.dialect.rowbound.impl.SQLServerRowBoundDialect;
import org.developerworld.commons.dbutils.sql.dialect.rowbound.impl.SQLServer_LE2000RowBoundDialect;
import org.developerworld.commons.dbutils.sql.dialect.rowbound.impl.SybaseRowBoundDialect;

/**
 * rowBound工厂类
 * 
 * @author Roy Huang
 *
 */
public class RowBoundDialectFactory {

	/**
	 * 根据数据库链接，获取分页方言实例
	 * 
	 * @param connection
	 * @return
	 * @throws SQLException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static RowBoundDialect buildRowBoundDialect(Connection connection)
			throws SQLException, InstantiationException, IllegalAccessException {
		return buildRowBoundDialect(connection.getMetaData());
	}

	/**
	 * 根据数据库元数据信息，获取分页方言实例
	 * 
	 * @param databaseMetaData
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static RowBoundDialect buildRowBoundDialect(DatabaseMetaData databaseMetaData)
			throws SQLException, InstantiationException, IllegalAccessException {
		Class<? extends RowBoundDialect> rowBoundDialectClass = getRowBoundDialectClass(databaseMetaData);
		if (rowBoundDialectClass != null)
			return rowBoundDialectClass.newInstance();
		else
			return null;
	}

	/**
	 * 根据数据库链接，获取分页方言类
	 * 
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	public static Class<? extends RowBoundDialect> getRowBoundDialectClass(Connection connection) throws SQLException {
		return getRowBoundDialectClass(connection.getMetaData());
	}

	/**
	 * 根据数据库元数据信息，获取分页方言类
	 * 
	 * @param databaseMetaData
	 * @return
	 * @throws SQLException
	 */
	public static Class<? extends RowBoundDialect> getRowBoundDialectClass(DatabaseMetaData databaseMetaData)
			throws SQLException {
		String databaseName = null;
		Integer databaseMajorVersion = null;
		try {
			databaseName = databaseMetaData.getDatabaseProductName();
			databaseMajorVersion = databaseMetaData.getDatabaseMajorVersion();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		// if ( "CUBRID".equalsIgnoreCase( databaseName ) ) {
		// return new CUBRIDDialect();
		// }
		if ("HSQL Database Engine".equals(databaseName))
			return HypersonicSQLRowBoundDialect.class;
		if ("H2".equals(databaseName))
			return H2RowBoundDialect.class;
		if ("MySQL".equals(databaseName))
			return MySqlRowBoundDialect.class;
		if ("PostgreSQL".equals(databaseName))
			return PostgreSQLRowBoundDialect.class;
		if ("Apache Derby".equals(databaseName))
			return DerbyRowBoundDialect.class;
		if ("ingres".equalsIgnoreCase(databaseName)) {
			if (databaseMajorVersion != null && databaseMajorVersion >= 9) {
				Integer databaseMinorVersion = null;
				try {
					databaseMinorVersion = databaseMetaData.getDatabaseMinorVersion();
				} catch (Throwable e) {
					e.printStackTrace();
				}
				if (databaseMinorVersion == null || databaseMinorVersion <= 2)
					return Ingres_L9RowBoundDialect.class;
				else
					return IngresRowBoundDialect.class;
			} else
				return Ingres_L9RowBoundDialect.class;
		}
		if (databaseName.startsWith("Microsoft SQL Server")) {
			if (databaseMajorVersion == null || databaseMajorVersion <= 8)
				return SQLServer_LE2000RowBoundDialect.class;
			else
				return SQLServerRowBoundDialect.class;
		}
		if ("Sybase SQL Server".equals(databaseName) || "Adaptive Server Enterprise".equals(databaseName))
			return SybaseRowBoundDialect.class;
		if (databaseName.startsWith("Adaptive Server Anywhere"))
			return SybaseRowBoundDialect.class;
		if ("Informix Dynamic Server".equals(databaseName))
			return InformixRowBoundDialect.class;
		if (databaseName.startsWith("DB2/"))
			return DB2RowBoundDialect.class;
		if ("Oracle".equals(databaseName))
			return OracleRowBoundDialect.class;
		return null;
	}
}
