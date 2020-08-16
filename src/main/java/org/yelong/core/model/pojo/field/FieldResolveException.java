/**
 * 
 */
package org.yelong.core.model.pojo.field;

import java.lang.reflect.Field;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.model.ModelException;
import org.yelong.core.model.Modelable;

/**
 * 字段解析异常
 * 
 * @since 2.0
 */
public class FieldResolveException extends ModelException {

	private static final long serialVersionUID = 5969385424975135355L;
	
	@Nullable
	private final Field field;

	public FieldResolveException(Class<? extends Modelable> modelClass, Field field, String message, Throwable t) {
		super(modelClass, message, t);
		this.field = field;
	}

	public FieldResolveException(Class<? extends Modelable> modelClass, Field field, String message) {
		super(modelClass, message);
		this.field = field;
	}

	public FieldResolveException(Class<? extends Modelable> modelClass, Field field, Throwable t) {
		super(modelClass, t);
		this.field = field;
	}

	public FieldResolveException(Class<? extends Modelable> modelClass, Field field) {
		super(modelClass);
		this.field = field;
	}

	public Field getField() {
		return field;
	}
	
}
