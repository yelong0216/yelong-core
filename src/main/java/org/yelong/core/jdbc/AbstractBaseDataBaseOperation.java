/**
 * 
 */
package org.yelong.core.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.yelong.commons.util.CollectionUtilsE;
import org.yelong.commons.util.ListUtilsE;
import org.yelong.core.annotation.Nullable;

/**
 * 抽象的数据库操作实现
 */
public abstract class AbstractBaseDataBaseOperation implements BaseDataBaseOperation {

	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> select(String sql, Object... params) {
		return (List<Map<String, Object>>) execute(sql, params, DataBaseOperationType.SELECT);
	}

	@Override
	public Map<String, Object> selectRow(String sql, Object... params) {
		return ListUtilsE.get(select(sql, params), 0, Collections.emptyMap());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> selectColumn(String sql, Object... params) {
		Object result = execute(sql, params, DataBaseOperationType.SELECT);
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) result;
		if (CollectionUtils.isEmpty(resultList)) {
			return Collections.emptyList();
		}
		return (List<T>) resultList.stream().map(x -> CollectionUtilsE.get(x.values(), 0)).collect(Collectors.toList());
	}

	@Override
	public <T> T selectSingleObject(String sql, Object... params) {
		return ListUtilsE.get(selectColumn(sql, params), 0);
	}

	@Override
	public Long count(String sql, Object... params) {
		Object result = selectSingleObject(sql, params);
		if (null == result) {
			return null;
		}
		if (result instanceof BigDecimal) {
			return ((BigDecimal) result).longValue();
		}
		return (long) result;
	}

	@Override
	public Integer delete(String sql, Object... params) {
		return (Integer) execute(sql, params, DataBaseOperationType.DELETE);
	}

	@Override
	public Integer update(String sql, Object... params) {
		return (Integer) execute(sql, params, DataBaseOperationType.UPDATE);
	}

	@Override
	public Integer insert(String sql, Object... params) {
		return (Integer) execute(sql, params, DataBaseOperationType.INSERT);
	}

	/**
	 * 创建PreparedStatement 并将占位符替换
	 * 
	 * @param conn   连接对象
	 * @param sql    sql语句
	 * @param params 参数
	 * @return {@link PreparedStatement}
	 * @throws SQLException
	 */
	protected PreparedStatement createPreparedStatement(Connection conn, String sql, Object... params)
			throws SQLException {
		PreparedStatement ps = conn.prepareStatement(sql);
		if (null == params || params.length == 0) {
			return ps;
		}
		for (int i = 0; i < params.length; i++) {
			// PreparedStatement索引从1开始
			ps.setObject(i + 1, params[i]);
		}
		return ps;
	}

	/**
	 * 执行数据库操作之后
	 * 
	 * @param conn          连接对象
	 * @param result        执行结果
	 * @param operationType 操作类型
	 * @throws SQLException
	 */
	public abstract void afterExecute(Connection conn, Object result, DataBaseOperationType operationType)
			throws SQLException;

	/**
	 * 执行SQL
	 * 
	 * @param sql           sql
	 * @param params        参数 需要的参数在sql中应该用占位符"?"来代替
	 * @param operationType 操作类型
	 * @return 1、如果operationType为SELECT则返回一个List&lt;Object&gt;<br/>
	 *         如果返回行数为多列则将返回一个List&lt;Map&lt;String,Object&gt;&gt;<br/>
	 *         ，其map中键值为列名称，value为其值 2、如果operationType不为SELECT则返回一个Integer类型对象
	 */
	public Object execute(String sql, @Nullable Object[] params, DataBaseOperationType operationType) {
		try {
			Connection conn = getConnection();
			PreparedStatement ps = createPreparedStatement(conn, sql, params);
			Object result = null;
			if (operationType == DataBaseOperationType.SELECT) {
				ResultSet rs = ps.executeQuery();
				result = JdbcUtils.resolveResultSet(rs);
				close(rs);
			} else {
				result = ps.executeUpdate();
			}
			close(ps);
			afterExecute(conn, result, operationType);
			return result;
		} catch (SQLException e) {
			throw new DataBaseOperationException(e);
		}
	}

	protected void close(ResultSet rs) throws SQLException {
		if (null != rs) {
			rs.close();
		}
	}

	protected void close(PreparedStatement ps) throws SQLException {
		if (null != ps) {
			ps.close();
		}
	}

}
