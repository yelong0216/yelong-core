/**
 * 
 */
package org.yelong.core.model.property;

import org.yelong.core.model.ModelException;
import org.yelong.core.model.Modelable;

/**
 * model 属性异常
 * 
 * @since 1.0
 */
public class ModelPropertyException extends ModelException {

	private static final long serialVersionUID = -7440960233579692479L;

	public ModelPropertyException(Class<? extends Modelable> modelClass, String message, Throwable t) {
		super(modelClass, message, t);
	}

	public ModelPropertyException(Class<? extends Modelable> modelClass, String message) {
		super(modelClass, message);
	}

	public ModelPropertyException(Class<? extends Modelable> modelClass, Throwable t) {
		super(modelClass, t);
	}

	public ModelPropertyException(Class<? extends Modelable> modelClass) {
		super(modelClass);
	}

}
