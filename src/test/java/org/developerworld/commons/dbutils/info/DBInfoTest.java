package org.developerworld.commons.dbutils.info;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.developerworld.commons.dbutils.info.object.Table;
import org.junit.Assert;

/**
 * DBInfo单元测试类
 * 
 * @author Roy Huang
 * @version 20130728
 * 
 */
public class DBInfoTest {

	private static DBInfo dbInfo = null;
	private static Connection connection = null;

	//@BeforeClass
	public static void before() throws ClassNotFoundException, SQLException {
		 Class.forName("com.mysql.jdbc.Driver");
		 Connection connection = DriverManager
		 .getConnection(
		 "jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=UTF-8",
		 "root", "root");
//		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//		 Connection connection = DriverManager
//		 .getConnection(
//		 "jdbc:sqlserver://localhost:1433;DatabaseName=gagc3161",
//		 "gagc3161", "gagc3161");
//		Class.forName("org.postgresql.Driver");
//		Connection connection = DriverManager.getConnection(
//				"jdbc:postgresql://localhost:5432/gagc3161", "gagc3161",
//				"gagc3161");
		dbInfo = new DBInfo(connection);

	}

	//@AfterClass
	public static void after() throws SQLException {
		if (connection != null && !connection.isClosed())
			connection.close();
	}

	// @Test
	public void testGetAllTableInfos() throws Exception {
		Set<Table> tables = dbInfo.getAllTableInfos();
		Assert.assertNotNull(tables);
	}

	//@Test
	public void testGetAllTableInfosTablenameFilter() throws Exception {
		Set<Table> tables = dbInfo.getAllTableInfos(new TablenameFilter() {

			public boolean accept(String tableName) {
				tableName = tableName.toLowerCase();
				return tableName.startsWith("cs_")
						|| tableName.startsWith("ess_")
						|| tableName.startsWith("pub_")
						|| tableName.startsWith("tcs_")
						|| tableName.startsWith("ucs_")
						|| tableName.startsWith("ums_")
						|| tableName.startsWith("prj_");
			}

		});
		Assert.assertNotNull(tables);
	}

	// @Test
	public void testGetAllTableInfosStringString() throws Exception {
		Set<Table> tables = dbInfo.getAllTableInfos("wms", "wms");
		Assert.assertNotNull(tables);
	}

//	@Test
	public void testGetTableInfosSetOfString() throws Exception {
		Set<Table> tables = dbInfo.getTableInfos(new HashSet<String>());
		Assert.assertNotNull(tables);
	}

	// @Test
	public void testGetTableInfosStringStringSetOfString() throws Exception {
		Set<Table> tables = dbInfo.getTableInfos("wms", "wms",
				new HashSet<String>());
		Assert.assertNotNull(tables);
	}

	// @Test
	public void testGetTableInfo() throws Exception {
		Table tables = dbInfo.getTableInfo(null, null, "CS_COURSE_ANSWER_SHEET");
		Assert.assertNotNull(tables);
	}

}
