/**
 * 
 */
package org.yelong.core.model;

import java.util.Objects;

/**
 * 模型类信息异常
 * 
 * @since 1.0
 */
public class ModelException extends RuntimeException {

	private static final long serialVersionUID = 8180022466141924432L;

	private final Class<? extends Modelable> modelClass;

	public ModelException(final Class<? extends Modelable> modelClass) {
		this.modelClass = Objects.requireNonNull(modelClass);
	}

	public ModelException(final Class<? extends Modelable> modelClass, String message) {
		super(message);
		this.modelClass = Objects.requireNonNull(modelClass);
	}

	public ModelException(final Class<? extends Modelable> modelClass, Throwable t) {
		super(t);
		this.modelClass = Objects.requireNonNull(modelClass);
	}

	public ModelException(final Class<? extends Modelable> modelClass, String message, Throwable t) {
		super(message, t);
		this.modelClass = Objects.requireNonNull(modelClass);
	}

	public Class<? extends Modelable> getModelClass() {
		return modelClass;
	}

}
