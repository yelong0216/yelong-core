/**
 * 
 */
package org.yelong.core.jdbc.record;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yelong.core.jdbc.BaseDataBaseOperation;
import org.yelong.core.jdbc.sql.splice.SpliceSql;

/**
 * 数据库记录操作
 * 
 * @author PengFei
 * @date 2019年12月22日上午1:59:24
 * @since 1.0
 */
public class DataBaseRecordOperation {
	
	private final BaseDataBaseOperation db;
	
	private final SpliceSql spliceSql;
	
	private RecordFactory recordFactory;
	
	/**
	 * @param db 数据库操作对象
	 * @param spliceSql 数据库方言
	 */
	public DataBaseRecordOperation(BaseDataBaseOperation db,SpliceSql spliceSql) {
		this(db,spliceSql,new DefaultRecordFactory());
	}
	
	/**
	 * @param db 数据库操作对象
	 * @param spliceSql 数据库方言
	 * @param recordFactory 记录工厂
	 */
	public DataBaseRecordOperation(BaseDataBaseOperation db, SpliceSql spliceSql, RecordFactory recordFactory) {
		this.db = db;
		this.spliceSql = spliceSql;
		this.recordFactory = recordFactory;
	}

	/**
	 * 查询多条记录
	 * @param sql sql
	 * @param params 参数
	 * @return 记录集合
	 */
	public List<Record> select(String sql , Object ... params){
		List<Record> recordList = new ArrayList<>();
		List<Map<String, Object>> result = db.select(sql, params);
		for (Map<String, Object> map : result) {
			recordList.add(recordFactory.create(map));
		}
		return recordList;
	}
	
	/**
	 * 查询一条记录
	 * 
	 * @param sql sql
	 * @param params 参数
	 * @return 记录
	 */
	public Record selectRow(String sql , Object ... params) {
		return recordFactory.create(db.selectRow(sql, params));
	}

	/**
	 * 添加一条记录
	 * @param tableName 表名称
	 * @param record 记录
	 * @return 是否添加成功
	 */
	public boolean insert(String tableName , Record record) {
		String insertSql = spliceSql.insertSql(tableName, record.getColumnNames());
		return db.insert(insertSql, record.getColumnValues()) > 0;
	}
	
	public RecordFactory getRecordFactory() {
		return recordFactory;
	}

	public void setRecordFactory(RecordFactory recordFactory) {
		this.recordFactory = recordFactory;
	}

	public BaseDataBaseOperation getBaseDataBaseOperation() {
		return db;
	}

	public SpliceSql getSpliceSql() {
		return spliceSql;
	}
	
}
