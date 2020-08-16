/**
 * 
 */
package org.yelong.core.model.manage.exception;

import org.yelong.core.model.ModelException;
import org.yelong.core.model.Modelable;

/**
 * 主键异常
 * 
 * @since 2.0
 */
public class PrimaryKeyException extends ModelException {

	private static final long serialVersionUID = 4686004552688583074L;

	public PrimaryKeyException(Class<? extends Modelable> modelClass, String message, Throwable t) {
		super(modelClass, message, t);
	}

	public PrimaryKeyException(Class<? extends Modelable> modelClass, String message) {
		super(modelClass, message);
	}

	public PrimaryKeyException(Class<? extends Modelable> modelClass, Throwable t) {
		super(modelClass, t);
	}

	public PrimaryKeyException(Class<? extends Modelable> modelClass) {
		super(modelClass);
	}

}
