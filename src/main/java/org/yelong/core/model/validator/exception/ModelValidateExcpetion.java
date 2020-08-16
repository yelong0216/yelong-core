/**
 * 
 */
package org.yelong.core.model.validator.exception;

import org.yelong.core.model.Modelable;

/**
 * 模型验证异常
 * 
 * @since 2.0
 */
public class ModelValidateExcpetion extends FieldAndColumnValidateExcpetion {

	private static final long serialVersionUID = -3673671344587284530L;

	private final Modelable model;

	private final FieldAndColumnValidateExcpetion fieldAndColumnValidateExcpetion;

	/**
	 * @param model 验证的模型对象
	 * @param fieldAndColumnValidateExcpetion 抛出的字段列异常
	 */
	public ModelValidateExcpetion(Modelable model, FieldAndColumnValidateExcpetion fieldAndColumnValidateExcpetion) {
		super(fieldAndColumnValidateExcpetion.getFieldAndColumn(), fieldAndColumnValidateExcpetion.getFieldValue(),
				fieldAndColumnValidateExcpetion.getMessage());
		this.model = model;
		this.fieldAndColumnValidateExcpetion = fieldAndColumnValidateExcpetion;
	}

	public ModelValidateExcpetion(Modelable model, FieldAndColumnValidateExcpetion fieldAndColumnValidateExcpetion,
			String message) {
		super(fieldAndColumnValidateExcpetion.getFieldAndColumn(), fieldAndColumnValidateExcpetion.getFieldValue(),
				message);
		this.model = model;
		this.fieldAndColumnValidateExcpetion = fieldAndColumnValidateExcpetion;
	}

	public Modelable getModel() {
		return model;
	}

	public FieldAndColumnValidateExcpetion getFieldAndColumnValidateExcpetion() {
		return fieldAndColumnValidateExcpetion;
	}

}
