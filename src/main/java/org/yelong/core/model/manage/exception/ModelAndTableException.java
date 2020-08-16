/**
 * 
 */
package org.yelong.core.model.manage.exception;

import java.util.Objects;

import org.yelong.core.model.ModelException;
import org.yelong.core.model.manage.ModelAndTable;

/**
 * 模型表异常
 * 
 * @since 2.0
 */
public class ModelAndTableException extends ModelException {

	private static final long serialVersionUID = -8553563105580433705L;

	/**
	 * 出现异常的模型表
	 */
	private final ModelAndTable modelAndTable;

	public ModelAndTableException(ModelAndTable modelAndTable) {
		super(Objects.requireNonNull(modelAndTable).getModelClass());
		this.modelAndTable = modelAndTable;
	}

	public ModelAndTableException(ModelAndTable modelAndTable, String message) {
		super(Objects.requireNonNull(modelAndTable).getModelClass(), message);
		this.modelAndTable = modelAndTable;
	}

	public ModelAndTableException(ModelAndTable modelAndTable, Throwable t) {
		super(Objects.requireNonNull(modelAndTable).getModelClass(), t);
		this.modelAndTable = modelAndTable;
	}

	public ModelAndTableException(ModelAndTable modelAndTable, String message, Throwable t) {
		super(Objects.requireNonNull(modelAndTable).getModelClass(), message, t);
		this.modelAndTable = modelAndTable;
	}

	public ModelAndTable getModelAndTable() {
		return modelAndTable;
	}

}
