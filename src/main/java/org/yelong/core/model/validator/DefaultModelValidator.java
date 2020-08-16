/**
 * 
 */
package org.yelong.core.model.validator;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.ModelAndTable;
import org.yelong.core.model.manage.ModelManager;
import org.yelong.core.model.property.ModelProperty;
import org.yelong.core.model.validator.exception.FieldValueBlankException;
import org.yelong.core.model.validator.exception.FieldValueLengthException;
import org.yelong.core.model.validator.exception.FieldValueNullException;
import org.yelong.core.model.validator.exception.FieldAndColumnValidateExcpetion;
import org.yelong.core.model.validator.exception.ModelValidateExcpetion;

/**
 * 模型字段列验证器的默认实现
 * 
 * @since 2.0
 */
public class DefaultModelValidator implements ModelValidator {

	private final ModelManager modelManager;

	private final ModelProperty modelProperty;

	public DefaultModelValidator(ModelManager modelManager, ModelProperty modelProperty) {
		this.modelManager = modelManager;
		this.modelProperty = modelProperty;
	}

	@Override
	public void validateModel(Modelable model) throws ModelValidateExcpetion {
		ModelAndTable modelAndTable = modelManager.getModelAndTable(model.getClass());
		List<FieldAndColumn> fieldAndColumns = modelAndTable.getNormalFieldAndColumns();
		for (FieldAndColumn fieldAndColumn : fieldAndColumns) {
			if (fieldAndColumn.isExtend()) {// 不对拓展字段进行新增、修改操作
				continue;
			}
			try {
				Object value = modelProperty.get(model, fieldAndColumn.getFieldName());
				validateFieldAndColumn(fieldAndColumn, value);
			} catch (FieldAndColumnValidateExcpetion e) {
				throw new ModelValidateExcpetion(model, e);
			}
		}
	}

	@Override
	public void validateFieldAndColumn(Class<? extends Modelable> modelClass, String fieldName, Object value)
			throws FieldAndColumnValidateExcpetion {
		ModelAndTable modelAndTable = modelManager.getModelAndTable(modelClass);
		FieldAndColumn fieldAndColumn = modelAndTable.getFieldAndColumn(fieldName);
		validateFieldAndColumn(fieldAndColumn, value);
	}

	@Override
	public void validateFieldAndColumn(FieldAndColumn fieldAndColumn, Object value)
			throws FieldAndColumnValidateExcpetion {
		Class<? extends Modelable> modelClass = fieldAndColumn.getModelAndTable().getModelClass();
		if (null == value) {
			if (!fieldAndColumn.isAllowNull()) {
				throw new FieldValueNullException(fieldAndColumn, value,
						"(" + modelClass + ")" + "列:" + fieldAndColumn.getColumn() + "不支持为null");
			} else {
				return;
			}
		} else {
			Class<?> fieldType = fieldAndColumn.getFieldType();
			if (CharSequence.class.isAssignableFrom(fieldType)) {
				if (!fieldAndColumn.isAllowBlank()) {
					if (StringUtils.isBlank((CharSequence) value)) {
						throw new FieldValueBlankException(fieldAndColumn, value,
								"(" + modelClass + ")" + "列:" + fieldAndColumn.getColumn() + "不支持为空白字符");
					}
				}
			}
		}
		Long minLength = fieldAndColumn.getMinLength();
		Long maxLength = fieldAndColumn.getMaxLength();
		int valueLength = 0;
		if (value instanceof Number) {
			valueLength = (value + "").length();
		} else if (value instanceof CharSequence) {
			valueLength = ((CharSequence) value).length();
		} else {
			valueLength = value.toString().length();
		}
		if (null != minLength && valueLength < minLength) {
			throw new FieldValueLengthException(fieldAndColumn, value,
					"(" + modelClass + ")" + "列\"" + fieldAndColumn.getColumn() + "\"的值(" + value + ")太小(实际值: "
							+ valueLength + ", 最小值: " + minLength + ")");
		}
		if (null != maxLength && valueLength > maxLength) {
			throw new FieldValueLengthException(fieldAndColumn, value,
					"(" + modelClass + ")" + "列\"" + fieldAndColumn.getColumn() + "\"的值(" + value + ")太大 (实际值: "
							+ valueLength + ", 最大值: " + maxLength + ")");
		}
	}

	@Override
	public ModelManager getModelManager() {
		return modelManager;
	}

	@Override
	public ModelProperty getModelProperty() {
		return modelProperty;
	}

}
