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
 * @author 彭飞
 * @date 2019年9月17日下午2:34:02
 * @version 1.2
 */
public interface AttributeSqlFragment extends SqlFragment{

	/**
	 * 添加属性<br/>
	 * 注意：如果attrName属性已存在，则之前的会被替换
	 * @author 彭飞
	 * @date 2019年9月17日下午2:36:11
	 * @version 1.2
	 * @param attrName 属性名称（这应该是是列名称，将以此名称生成sql片段）
	 * @param value 属性值
	 */
	void addAttr(String attrName,@Nullable Object value);
	
	/**
	 * 添加属性，仅当value不为null时添加
	 * @author 彭飞
	 * @date 2019年9月17日下午2:37:35
	 * @version 1.2
	 * @param attrName 属性名称
	 * @param value 值
	 * @return <tt>true</tt> value != null
	 */
	boolean addAttrByValueNotNull(String attrName ,@Nullable  Object value);
	
	/**
	 * 移除一条属性
	 * @author 彭飞
	 * @date 2019年9月17日下午2:38:23
	 * @version 1.2
	 * @param attrName 属性名称
	 * @return <tt>true</tt> 属性被移除（如果属性不存在则是返回false）
	 */
	boolean removeAttr(String attrName);
	
	/**
	 * 获取所有的属性名称
	 * @author 彭飞
	 * @date 2019年9月17日下午2:40:15
	 * @version 1.2
	 * @return 所有的属性名称
	 */
	Set<String> getAllAttrName();
	
	/**
	 * 获取所有的值
	 * @author 彭飞
	 * @date 2019年9月17日下午2:40:46
	 * @version 1.2
	 * @return 所有的属性对应的值
	 */
	Collection<Object> getAllValue();
	
	/**
	 * 获取所有属性
	 * @author 彭飞
	 * @date 2019年9月17日下午2:49:48
	 * @version 1.2
	 * @return 所有属性映射
	 */
	Map<String,Object> getAllAttribute();
	
	/**
	 * 属性是否为空
	 * @author 彭飞
	 * @date 2019年9月17日下午2:42:20
	 * @version 1.2
	 * @return <tt>true</tt> 不存在属性
	 */
	boolean isEmpty();
	
	/**
	 * 获取sql操作类型（仅为Insert和update）
	 * @author 彭飞
	 * @date 2019年9月17日下午2:43:41
	 * @version 1.2
	 * @return sql操作类型
	 */
	DataBaseOperationType getDataBaseOperationType();
	
	/**
	 * 设置sql的操作类型
	 * 注：仅限制为Insert和update
	 * @author 彭飞
	 * @date 2019年10月24日上午11:08:07
	 * @version 1.2
	 * @param dataBaseOperationType 操作类型
	 */
	void setDataBaseOperationType(DataBaseOperationType dataBaseOperationType);
	
}
