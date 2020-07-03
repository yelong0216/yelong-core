/**
 * 
 */
package org.yelong.core.jdbc.sql.defaults;

import static org.yelong.core.jdbc.sql.SpliceSqlUtils.spliceSqlFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.yelong.core.jdbc.DataBaseOperationType;
import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.attribute.AbstractAttributeSqlFragment;

/**
 * 默认的属性sql片段实现
 * 
 * @author PengFei
 */
public class DefaultAttributeSqlFragment extends AbstractAttributeSqlFragment {

	private static final String EMPTY = StringUtils.EMPTY;

	private static final String COMMA = ",";

	private static final String EQUALS = "=";

	// 默认参数占位符
	public static final String DEFAULT_PARAM_PLACEHOLDER = "?";

	public DefaultAttributeSqlFragment(Dialect dialect) {
		super(dialect);
	}

	public DefaultAttributeSqlFragment(Dialect dialect, DataBaseOperationType dataBaseOperationType) {
		super(dialect);
		setDataBaseOperationType(dataBaseOperationType);
	}

	@Override
	public String getSqlFragment() {
		switch (getDataBaseOperationType()) {
		case INSERT:
			return getInsertAttrSqlFragment();
		case UPDATE:
			return getUpdateAttrSqlFragment();
		default:
			return null;
		}
	}

	/**
	 * 新增属性的sql片段
	 * 
	 * @return 新增的属性sql片段 (columns...) VALUES (values...)
	 */
	public String getInsertAttrSqlFragment() {
		if (isEmpty()) {
			return EMPTY;
		}
		List<String> sqlFragment = new ArrayList<String>();
		Set<String> attrNames = getAllAttrName();
		sqlFragment.add("(");
		attrNames.forEach(x -> {
			sqlFragment.add(x);
			sqlFragment.add(COMMA);
		});
		sqlFragment.remove(sqlFragment.lastIndexOf(COMMA));
		sqlFragment.add(")");
		sqlFragment.add("VALUES");
		sqlFragment.add("(");
		attrNames.forEach(x -> {
			sqlFragment.add("?");
			sqlFragment.add(COMMA);
		});
		sqlFragment.remove(sqlFragment.lastIndexOf(COMMA));
		sqlFragment.add(")");
		return spliceSqlFragment(sqlFragment.toArray(new String[] {}));
	}

	/**
	 * 修改的sql片段
	 * 
	 * @return 修改的sql片段 column = value , ...
	 */
	public String getUpdateAttrSqlFragment() {
		if (isEmpty()) {
			return EMPTY;
		}
		List<String> sqlfragment = new ArrayList<String>();
		getAllAttrName().forEach(x -> {
			sqlfragment.add(x);
			sqlfragment.add(EQUALS);
			sqlfragment.add(DEFAULT_PARAM_PLACEHOLDER);
			sqlfragment.add(COMMA);
		});
		sqlfragment.remove(sqlfragment.lastIndexOf(COMMA));
		return spliceSqlFragment(sqlfragment.toArray(new String[] {}));
	}

	@Override
	public String toString() {
		return getSqlFragment();
	}

}
