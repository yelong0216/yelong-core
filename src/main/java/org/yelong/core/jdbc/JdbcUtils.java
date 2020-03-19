/**
 * 
 */
package org.yelong.core.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author PengFei
 * @date 2019年7月16日下午2:26:43
 * @since 1.0
 */
public class JdbcUtils {

	/**
	 * 获取结果集中查询的所有列名
	 * 
	 * @author 彭飞
	 * @date 2019年4月30日上午10:47:13
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static String[] getColumnNames(ResultSet rs) throws SQLException {
		// ResultSet数据都存放在ResultSetMetaData中
		ResultSetMetaData rsmd = rs.getMetaData();
		// 获取查询列的数量
		int count = rsmd.getColumnCount();
		// 然后通过遍历索引查询所有列的名称
		String[] columnNames = new String[count];
		for (int i = 0; i < count; i++) {
			// ResultSetMetaData中列索引从1开始
			columnNames[i] = rsmd.getColumnName(i + 1);
		}
		return columnNames;
	}

	/**
	 * 解析结果集
	 * 
	 * @param rs 结果集
	 * @return 结果集映射 一条记录为一个Map map中的key为列名称。value为列值
	 * @throws SQLException
	 */
	public static List<Map<String, Object>> resolveResultSet(ResultSet rs) throws SQLException {
		List<Map<String, Object>> result = new ArrayList<>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		String[] labelNames = new String[columnCount + 1];
		int[] types = new int[columnCount + 1];
		buildLabelNamesAndTypes(rsmd, labelNames, types);
		while (rs.next()) {
			Map<String, Object> attrs = new LinkedHashMap<>();
			for (int i = 1; i <= columnCount; i++) {
				Object value;
				if (types[i] < Types.BLOB) {
					value = rs.getObject(i);
				} else {
					if (types[i] == Types.CLOB) {
						value = handleClob(rs.getClob(i));
					} else if (types[i] == Types.NCLOB) {
						value = handleClob(rs.getNClob(i));
					} else if (types[i] == Types.BLOB) {
						value = handleBlob(rs.getBlob(i));
					} else {
						value = rs.getObject(i);
					}
				}

				attrs.put(labelNames[i], value);
			}
			result.add(attrs);
		}
		return result;
	}

	private static void buildLabelNamesAndTypes(ResultSetMetaData rsmd, String[] labelNames, int[] types)
			throws SQLException {
		for (int i = 1; i < labelNames.length; i++) {
			labelNames[i] = rsmd.getColumnLabel(i);
			types[i] = rsmd.getColumnType(i);
		}
	}

	/**
	 * 处理blob类型数据
	 * 
	 * @param blob
	 * @return
	 * @throws SQLException
	 */
	public static byte[] handleBlob(Blob blob) throws SQLException {
		if (blob == null)
			return null;

		InputStream is = null;
		try {
			is = blob.getBinaryStream();
			if (is == null)
				return null;
			byte[] data = new byte[(int) blob.length()];
			if (data.length == 0)
				return null;
			is.read(data);
			return data;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}
	}

	/**
	 * 处理clob类型数据
	 * 
	 * @param clob
	 * @return
	 * @throws SQLException
	 */
	public static String handleClob(Clob clob) throws SQLException {
		if (clob == null)
			return null;

		Reader reader = null;
		try {
			reader = clob.getCharacterStream();
			if (reader == null)
				return null;
			char[] buffer = new char[(int) clob.length()];
			if (buffer.length == 0)
				return null;
			reader.read(buffer);
			return new String(buffer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}
	}

}
