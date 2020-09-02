/**
 * 
 */
package test.org.yelong.core.jdbc;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.yelong.core.jdbc.BaseDataBaseOperation;
import org.yelong.core.jdbc.DataBaseOperationType;
import org.yelong.core.jdbc.DataSourceProperties;
import org.yelong.core.jdbc.DefaultBaseDataBaseOperation;

public class BaseDataBaseOperationTest {

	public static BaseDataBaseOperation mysqlDb = null;
	
	static {
		DataSourceProperties dataSourceProperties = new DataSourceProperties();
		dataSourceProperties.setUsername("test");
		dataSourceProperties.setPassword("test");
		dataSourceProperties.setUrl("jdbc:mysql://localhost:3306/test");
		dataSourceProperties.setDriverClassName("com.mysql.jdbc.Driver");
		try {
			mysqlDb = new DefaultBaseDataBaseOperation(dataSourceProperties) {
				@Override
				public Object execute(String sql, Object[] params, DataBaseOperationType operationType) {
					System.out.println("sql:" + sql);
					System.out.println("params:" + Arrays.asList(params));
					return super.execute(sql, params, operationType);
				}
			};
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void select() {
		String sql = "select u.* from CO_USER u";
		List<Map<String, Object>> result = mysqlDb.select(sql);
		System.out.println(result);
	}

	@Test
	public void selectRow() {
		String sql = "select u.* from CO_USER u";
		Map<String, Object> selectRow = mysqlDb.selectRow(sql);
		System.out.println(selectRow);
	}
	
	@Test
	public void selectColumn() {
		String sql = "select u.realname,u.id,u.username from CO_USER u";
		List<Object> selectColumn = mysqlDb.selectColumn(sql);
		System.out.println(selectColumn);
	}
	
	@Test
	public void selectSingleObject() {
		String sql = "select u.realname,u.id,u.username from CO_USER u";
		Object singleObject = mysqlDb.selectSingleObject(sql);
		System.out.println(singleObject);
	}
	
	@Test
	public void count() {
		String sql = "select count(0) from CO_USER u";
		Object singleObject = mysqlDb.count(sql);
		System.out.println(singleObject);
	}
	
}
