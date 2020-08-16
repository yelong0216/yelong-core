/**
 * 
 */
package org.yelong.core.model.annotation.support;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.yelong.core.model.Modelable;

/**
 * @since
 */
public class ModelFieldAnnotationFactory {

	private static final Map<ClassFieldEntry, ModelFieldAnnotation> modelFieldAnnotationMap = new HashMap<>();

	@SuppressWarnings("unchecked")
	public static final ModelFieldAnnotation get(Field field) {
		return get((Class<Modelable>) field.getDeclaringClass(), field);
	}

	public static final ModelFieldAnnotation get(Class<? extends Modelable> modelClass, Field field) {
		ClassFieldEntry classFieldEntry = new ClassFieldEntry(modelClass, field);
		ModelFieldAnnotation modelFieldAnnotation = modelFieldAnnotationMap.get(classFieldEntry);
		if (null == modelFieldAnnotation) {
			synchronized (ModelFieldAnnotationFactory.class) {
				modelFieldAnnotation = modelFieldAnnotationMap.get(new ClassFieldEntry(modelClass, field));
				if (null == modelFieldAnnotation) {
					modelFieldAnnotation = new ModelFieldAnnotation(modelClass, field);
					modelFieldAnnotationMap.put(classFieldEntry, modelFieldAnnotation);
				}
			}
		}
		return modelFieldAnnotation;
	}

	private static final class ClassFieldEntry {

		public final Class<?> modelClass;

		public final Field field;

		public ClassFieldEntry(Class<?> modelClass, Field field) {
			this.modelClass = modelClass;
			this.field = field;
		}

		@Override
		public int hashCode() {
			return modelClass.getName().hashCode() ^ field.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof ClassFieldEntry) {
				ClassFieldEntry o = (ClassFieldEntry) obj;
				return modelClass.equals(o.modelClass) && field.equals(o.field);
			}
			return false;
		}

		@Override
		public String toString() {
			return modelClass + "." + field;
		}

	}

}
