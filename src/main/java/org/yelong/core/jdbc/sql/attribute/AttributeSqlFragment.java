/**
 * 
 */
package org.yelong.core.jdbc.sql.attribute;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.DataBaseOperationType;
import org.yelong.core.jdbc.sql.SqlFragment;

/**
 * 属性片段<br/>
 * 该属性为sql中insert和update的属性片段
 */
public interface AttributeSqlFragment extends SqlFragment {

	// ==================================================添加属性==================================================

	/**
	 * 添加属性<br/>
	 * 注意：如果attrName属性已存在，则之前的会被替换
	 * 
	 * @param attrName 属性名称（这应该是列名称，将以此名称生成sql片段）
	 * @param value    属性值
	 */
	void addAttr(String attrName, @Nullable Object value);

	/**
	 * 添加一组属性
	 * 
	 * @param attrs 属性映射
	 */
	void addAttrs(Map<String, Object> attrs);

	/**
	 * 添加属性，仅当value不为null时添加
	 * 
	 * @param attrName 属性名称
	 * @param value    值
	 * @return <tt>true</tt> value != null
	 */
	boolean addAttrByValueNotNull(String attrName, @Nullable Object value);

	/**
	 * 添加一组属性，仅当value不为null时添加
	 * 
	 * @param attrs 属性映射
	 */
	default void addAttrsByValueNotNull(Map<String, Object> attrs) {
		attrs.forEach(this::addAttrByValueNotNull);
	}

	// ==================================================删除属性==================================================

	/**
	 * 移除一条属性
	 * 
	 * @param attrName 属性名称
	 * @return <tt>true</tt> 属性被移除（如果属性不存在则是返回false）
	 */
	boolean removeAttr(String attrName);

	// ==================================================添获取性==================================================

	/**
	 * 获取属性的值
	 * 
	 * @param attrName 属性名称
	 * @return 属性值
	 * @since 2.1.6
	 */
	@Nullable
	Object getAttr(String attrName);

	/**
	 * 获取所有的属性名称
	 * 
	 * @return 所有的属性名称
	 */
	Set<String> getAllAttrName();

	/**
	 * 获取所有的值
	 * 
	 * @return 所有的属性对应的值
	 */
	Collection<Object> getAllValue();

	/**
	 * 获取所有属性
	 * 
	 * @return 所有属性映射
	 */
	Map<String, Object> getAllAttribute();

	/**
	 * 是否存在属性
	 * 
	 * @return <tt>true</tt> 不存在属性
	 */
	boolean isEmpty();

	/**
	 * @return sql操作类型 （仅为Insert和update）
	 */
	DataBaseOperationType getDataBaseOperationType();

	/**
	 * 设置sql的操作类型 注：仅限制为Insert和update
	 * 
	 * @param dataBaseOperationType 操作类型
	 */
	void setDataBaseOperationType(DataBaseOperationType dataBaseOperationType);

}
