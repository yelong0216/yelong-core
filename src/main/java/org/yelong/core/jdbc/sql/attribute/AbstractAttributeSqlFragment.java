/**
 * 
 */
package org.yelong.core.jdbc.sql.attribute;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.yelong.core.jdbc.DataBaseOperationType;
import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.AbstractSqlFragment;

/**
 * 抽象的属性sql片段实现
 */
public abstract class AbstractAttributeSqlFragment extends AbstractSqlFragment implements AttributeSqlFragment {

	private DataBaseOperationType dataBaseOperationType;

	private final Map<String, Object> attributes = new LinkedHashMap<>();

	public AbstractAttributeSqlFragment(Dialect dialect) {
		super(dialect);
	}

	@Override
	public void addAttr(String attrName, Object value) {
		attributes.put(attrName, value);
	}

	@Override
	public void addAttrs(Map<String, Object> attrs) {
		attributes.putAll(attrs);
	}

	@Override
	public boolean addAttrByValueNotNull(String attrName, Object value) {
		if (null == value) {
			return false;
		}
		addAttr(attrName, value);
		return true;
	}

	@Override
	public boolean removeAttr(String attrName) {
		return attributes.remove(attrName) != null;
	}

	@Override
	public Object getAttr(String attrName) {
		return attributes.get(attrName);
	}

	@Override
	public Set<String> getAllAttrName() {
		return attributes.keySet();
	}

	@Override
	public Collection<Object> getAllValue() {
		return attributes.values();
	}

	@Override
	public Map<String, Object> getAllAttribute() {
		return Collections.unmodifiableMap(attributes);
	}

	@Override
	public boolean isEmpty() {
		return attributes.isEmpty();
	}

	@Override
	public Object[] getParams() {
		return getAllValue().toArray();
	}

	@Override
	public void setDataBaseOperationType(DataBaseOperationType dataBaseOperationType) {
		if (dataBaseOperationType != DataBaseOperationType.INSERT
				|| dataBaseOperationType != DataBaseOperationType.UPDATE) {
			new IllegalStateException("attribute只支持新增和修改！");
		}
		this.dataBaseOperationType = dataBaseOperationType;
	}

	@Override
	public DataBaseOperationType getDataBaseOperationType() {
		if (null == dataBaseOperationType) {
			throw new IllegalStateException("未设置操作类型");
		}
		return this.dataBaseOperationType;
	}

}
