/**
 * 
 */
package org.yelong.core.model.service;

import org.yelong.core.model.ModelException;
import org.yelong.core.model.Modelable;

/**
 * 模型业务处理异常
 * 
 * @since 1.0
 */
public class ModelServiceException extends ModelException {

	private static final long serialVersionUID = 500079897249254712L;

	public ModelServiceException(Class<? extends Modelable> modelClass, String message, Throwable t) {
		super(modelClass, message, t);
	}

	public ModelServiceException(Class<? extends Modelable> modelClass, String message) {
		super(modelClass, message);
	}

	public ModelServiceException(Class<? extends Modelable> modelClass, Throwable t) {
		super(modelClass, t);
	}

	public ModelServiceException(Class<? extends Modelable> modelClass) {
		super(modelClass);
	}

}
