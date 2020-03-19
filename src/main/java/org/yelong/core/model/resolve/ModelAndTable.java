/**
 * 
 */
package org.yelong.core.model.resolve;

import java.lang.reflect.Field;
import java.util.List;

import org.yelong.core.model.Model;

/**模型与表的映射信息
 * 
 * @author PengFei
 * @date 2019年12月26日下午9:20:01
 */
public interface ModelAndTable {
	
	/**
	 * 获取模型的类型
	 * @param <M> 模型
	 * @return model.getClass()
	 */
	<M extends Model> Class<M> getModelClass();
	
	/**
	 * @return model 对应的表名
	 */
	String getTableName();
	
	/**
	 * @return 表别名。默认为model类型首字母小写
	 */
	String getTableAlias();
	
	/**
	 * @return 表的描述
	 */
	String getTableDesc();
	
	/**
	 * 获取所有的主键。这个主键可能不是表中的主键，而是在model中进行标注的主键列。
	 * @return 所有主键字段列。
	 */
	List<FieldAndColumn> getPrimaryKey();
	
	/**
	 * 是否存在主键标识
	 * @return <tt>true</tt> 存在主键标识
	 */
	boolean existPrimaryKey();
	
	/**
	 * 获取所有的字段列（只包含进行了字段与列映射的字段）
	 * @return
	 */
	List<FieldAndColumn> getFieldAndColumns();
	
	/**
	 * 根据字段名称获取字段列
	 * 如果这个模型表中不存在这个字段的映射则返回 null
	 * @param fieldName 字段名名称
	 * @return 字段映射的列信息
	 */
	FieldAndColumn getFieldAndColumn(String fieldName);
	
	/**
	 * 获取所有的映射的字段
	 * @return 所有映射的字段
	 */
	List<Field> getFields();
	
	/**
	 * 获取所有映射的字段名称
	 * @return 所有映射的字段名称
	 */
	List<String> getFieldNames();
	
}
