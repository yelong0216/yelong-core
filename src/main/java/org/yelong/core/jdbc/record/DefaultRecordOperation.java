/**
 * 
 */
package org.yelong.core.jdbc.record;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yelong.core.jdbc.BaseDataBaseOperation;
import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.BoundSql;
import org.yelong.core.jdbc.sql.attribute.AttributeSqlFragment;
import org.yelong.core.jdbc.sql.ddl.Table;
import org.yelong.core.jdbc.sql.executable.UpdateSqlFragment;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;

/**
 * 默认的记录操作
 * 
 * @since 1.1
 */
public class DefaultRecordOperation implements RecordOperation {

	protected final BaseDataBaseOperation baseDataBaseOperation;

	protected final Dialect dialect;

	public DefaultRecordOperation(BaseDataBaseOperation baseDataBaseOperation, Dialect dialect) {
		this.baseDataBaseOperation = baseDataBaseOperation;
		this.dialect = dialect;
	}

	@Override
	public Integer insert(Table table, Record record) {
		SqlFragmentFactory sqlFragmentFactory = dialect.getSqlFragmentFactory();
		AttributeSqlFragment attributeSqlFragment = sqlFragmentFactory.createAttributeSqlFragment();
		record.forEach(attributeSqlFragment::addAttrByValueNotNull);
		UpdateSqlFragment updateSqlFragment = sqlFragmentFactory.createUpdateSqlFragment(table.getName(),
				attributeSqlFragment);
		BoundSql boundSql = updateSqlFragment.getBoundSql();
		return baseDataBaseOperation.insert(boundSql.getSql(), boundSql.getParams());
	}

	@Override
	public List<Record> select(String sql, Object... params) {
		List<Record> records = new ArrayList<Record>();
		List<Map<String, Object>> result = baseDataBaseOperation.select(sql, params);
		result.forEach(x -> {
			records.add(new Record().setColumns(x));
		});
		return records;
	}

}
