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
import org.yelong.core.jdbc.sql.attribute.AbstractAttributeSqlFragment;

/**
 * @author 彭飞
 * @date 2019年9月17日下午2:47:35
 * @version 1.2
 */
public class DefaultAttributeSqlFragment extends AbstractAttributeSqlFragment{

	private static final String EMPTY = StringUtils.EMPTY;
	
	private static final String COMMA = ",";
	
	private static final String EQUALS = "=";
	
	//默认参数占位符
	public static final String DEFAULT_PARAM_PLACEHOLDER = "?";


	public DefaultAttributeSqlFragment(DataBaseOperationType dataBaseOperationType) {
		setDataBaseOperationType(dataBaseOperationType);
	}

	public DefaultAttributeSqlFragment() {
		
	}
	
	@Override
	public String getSqlFragment() {
		switch(getDataBaseOperationType()) {
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
	 * @author 彭飞
	 * @date 2019年9月11日下午6:41:00
	 * @version 1.2
	 * @return
	 */
	public String getInsertAttrSqlFragment() {
		if(isEmpty()) {
			return EMPTY;
		}
		List<String> sqlFragment = new ArrayList<String>();
		Set<String> attrNames = getAllAttrName();
		sqlFragment.add("(");
		attrNames.forEach(x->{
			sqlFragment.add(x);
			sqlFragment.add(COMMA);
		});
		sqlFragment.remove(sqlFragment.lastIndexOf(COMMA));
		sqlFragment.add(")");
		sqlFragment.add("VALUES");
		sqlFragment.add("(");
		attrNames.forEach(x->{
			sqlFragment.add("?");
			sqlFragment.add(COMMA);
		});
		sqlFragment.remove(sqlFragment.lastIndexOf(COMMA));
		sqlFragment.add(")");
		return spliceSqlFragment(sqlFragment.toArray(new String[] {})) ;
	}

	/**
	 * 修改的sql片段
	 * @author 彭飞
	 * @date 2019年9月11日下午6:40:50
	 * @version 1.2
	 * @return
	 */
	public String getUpdateAttrSqlFragment() {
		if(isEmpty()) {
			return EMPTY;
		}
		List<String> sqlfragment = new ArrayList<String>();
		getAllAttrName().forEach(x->{
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
