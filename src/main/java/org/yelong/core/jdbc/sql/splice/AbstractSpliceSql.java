/**
 * 
 */
package org.yelong.core.jdbc.sql.splice;

import static org.yelong.core.jdbc.sql.SpliceSqlUtils.spliceSqlFragment;

import java.util.LinkedList;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 彭飞
 * @date 2019年9月12日下午4:51:49
 * @version 1.2
 */
public abstract class AbstractSpliceSql implements SpliceSql{

	public static final String COMMA = ",";

	public static final String QUESTION_MARK = "?";


	@Override
	public String insertSql(String tableName, String[] columns) {
		LinkedList<String> fragment = new LinkedList<String>();
		fragment.add("insert into");
		fragment.add(tableName);
		fragment.add("(");
		for (String column : columns) {
			fragment.add(column);
			fragment.add(COMMA);
		}
		fragment.removeLast();
		fragment.add(")");
		fragment.add("values");
		fragment.add("(");
		int columnLength = columns.length;
		for (int i = 0; i < columnLength; i++) {
			fragment.add(QUESTION_MARK);
			fragment.add(COMMA);
		}
		fragment.removeLast();
		fragment.add(")");
		return spliceSqlFragment(fragment.toArray(new String[] {}));
	}

	@Override
	public String insertSql(String tableName, String attributesSqlFragment) {
		return spliceSqlFragment("insert into",tableName,attributesSqlFragment);
	}

	@Override
	public String deleteSql(String tableName) {
		return spliceSqlFragment("delete from",tableName);
	}
	
	@Override
	public String deleteSql(String tableName, String tableAlias) {
		return spliceSqlFragment("delete from",tableName,tableAlias);
	}

	@Override
	public String updateSql(String tableName, String[] updateColumn) {
		return updateSql(tableName, null, updateColumn);	
	}

	@Override
	public String updateSql(String tableName, String tableAlias, String[] updateColumn) {
		LinkedList<String> fragment = new LinkedList<>();
		fragment.add("update");
		fragment.add(tableName);
		if(StringUtils.isNotBlank(tableAlias)) {
			fragment.add(tableAlias);
		}
		fragment.add("set");
		for (String column : updateColumn) {
			fragment.add(column +" = ? ," );
			fragment.add("=");
			fragment.add(QUESTION_MARK);
			fragment.add(COMMA);
		}
		fragment.removeLast();
		return spliceSqlFragment(fragment.toArray(new String[] {}));
	}

	@Override
	public String updateSql(String tableName, String attributeSqlFragment) {
		return spliceSqlFragment("update",tableName,"set",attributeSqlFragment);
	}

	@Override
	public String updateSql(String tableName, String tableAlias, String attributeSqlFragment) {
		return spliceSqlFragment("update",tableName,tableAlias,"set",attributeSqlFragment);
	}
	
	@Override
	public String countSql(String tableName, String tableAlias) {
		return spliceSqlFragment("select count(0) from",tableName,tableAlias);
	}

	@Override
	public String selectSql(String tableName,String tableAlias) {
		return spliceSqlFragment("select",tableAlias+".*","from",tableName,tableAlias);
	}

	@Override
	public String spliceCondition(String sql, String condition) {
		return spliceSqlFragment(sql,"WHERE",condition);
	}

	@Override
	public String spliceSort(String sql, String sort) {
		return spliceSqlFragment(sql,"ORDER BY",sort);
	}

}
