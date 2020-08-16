/**
 * 
 */
package org.yelong.core.model.resolve;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.model.ModelException;
import org.yelong.core.model.Modelable;

/**
 * model解析异常
 * 
 * @since 2.0
 */
public class ModelResolveException extends ModelException {

	private static final long serialVersionUID = 7157528523515342273L;

	@Nullable
	private final ModelResolver modelResolver;

	public ModelResolveException(ModelResolver modelResolver, Class<? extends Modelable> modelClass, String message,
			Throwable t) {
		super(modelClass, message, t);
		this.modelResolver = modelResolver;
	}

	public ModelResolveException(ModelResolver modelResolver, Class<? extends Modelable> modelClass, String message) {
		super(modelClass, message);
		this.modelResolver = modelResolver;
	}

	public ModelResolveException(ModelResolver modelResolver, Class<? extends Modelable> modelClass, Throwable t) {
		super(modelClass, t);
		this.modelResolver = modelResolver;
	}

	public ModelResolveException(ModelResolver modelResolver, Class<? extends Modelable> modelClass) {
		super(modelClass);
		this.modelResolver = modelResolver;
	}

	public ModelResolver getModelResolver() {
		return modelResolver;
	}

}
