/**
 * 
 */
package org.yelong.core.model.validator;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.ModelManager;
import org.yelong.core.model.property.ModelProperty;
import org.yelong.core.model.validator.exception.FieldAndColumnValidateExcpetion;
import org.yelong.core.model.validator.exception.ModelValidateExcpetion;

/**
 * 模型字段列验证器<br/>
 * 
 * 验证字段值的是否符合模型定义的规则（非空、长度等）
 * 
 * @since 2.0
 */
public interface ModelValidator {

	/**
	 * 验证模型对象中所有映射的列（不包含拓展列）是否符合规则
	 * 
	 * @param model model
	 * @throws ModelValidateExcpetion 至少存在一个列验证失败
	 * @see #validateFieldAndColumn(FieldAndColumn, Object)
	 */
	void validateModel(Modelable model) throws ModelValidateExcpetion;

	/**
	 * 验证字段值是否符合字段列定义的规则
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param fieldName  字段名称
	 * @param value      字段值
	 * @throws FieldAndColumnValidateExcpetion 字段列验证异常
	 * @see #validateFieldAndColumn(FieldAndColumn, Object)
	 */
	void validateFieldAndColumn(Class<? extends Modelable> modelClass, String fieldName, Object value)
			throws FieldAndColumnValidateExcpetion;

	/**
	 * 验证字段值是否符合字段列定义的规则
	 * 
	 * @param fieldAndColumn 字段列
	 * @param value          字段值
	 * @throws FieldAndColumnValidateExcpetion 字段列验证异常
	 */
	void validateFieldAndColumn(FieldAndColumn fieldAndColumn, Object value) throws FieldAndColumnValidateExcpetion;

	/**
	 * @return 模型表管理器
	 */
	ModelManager getModelManager();

	/**
	 * @return 模型属性操作
	 */
	ModelProperty getModelProperty();

}
