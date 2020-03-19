/**
 * 
 */
package org.yelong.core.jdbc.sql.executable;

import java.util.List;
import java.util.Map;

import org.yelong.core.jdbc.BaseDataBaseOperation;
import org.yelong.core.jdbc.sql.BoundSql;

/**
 * sql片段执行器抽象实现
 * @author PengFei
 */
public abstract class AbstractSqlFragmentExecutor implements SqlFragmentExecutor{

	@Override
	public <R> R execute(SqlFragmentExecutable sqlFragment) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Integer execute(InsertSqlFragment insertSqlFragment) {
		BoundSql boundSql = insertSqlFragment.getBoundSql();
		return getBaseDataBaseOperation().insert(boundSql.getSql(), boundSql.getParams());
	}

	@Override
	public Integer execute(DeleteSqlFragment deleteSqlFragment) {
		BoundSql boundSql = deleteSqlFragment.getBoundSql();
		return getBaseDataBaseOperation().delete(boundSql.getSql(), boundSql.getParams());
	}

	@Override
	public Integer execute(UpdateSqlFragment updateSqlFragment) {
		BoundSql boundSql = updateSqlFragment.getBoundSql();
		return getBaseDataBaseOperation().update(boundSql.getSql(), boundSql.getParams());
	}

	@Override
	public Long execute(CountSqlFragment countSqlFragment) {
		BoundSql boundSql = countSqlFragment.getBoundSql();
		return getBaseDataBaseOperation().count(boundSql.getSql(), boundSql.getParams());
	}
	
	@Override
	public List<Map<String, Object>> execute(SelectSqlFragment selectSqlFragment) {
		BoundSql boundSql = selectSqlFragment.getBoundSql();
		return getBaseDataBaseOperation().select(boundSql.getSql(), boundSql.getParams());
	}
	
	/**
	 * @return 基本数据库操作对象
	 */
	public abstract BaseDataBaseOperation getBaseDataBaseOperation();

}
