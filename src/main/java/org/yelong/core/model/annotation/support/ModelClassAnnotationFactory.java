package org.yelong.core.model.annotation.support;

import java.util.HashMap;
import java.util.Map;

import org.yelong.core.model.Modelable;

public final class ModelClassAnnotationFactory {

	private static final Map<Class<? extends Modelable>, ModelClassAnnotation> modelClassAnnotationMap = new HashMap<>();

	private ModelClassAnnotationFactory() {
	}

	public static ModelClassAnnotation get(Class<? extends Modelable> modelClass) {
		ModelClassAnnotation modelClassAnnotation = modelClassAnnotationMap.get(modelClass);
		if (null == modelClassAnnotation) {
			synchronized (ModelClassAnnotationFactory.class) {
				modelClassAnnotation = modelClassAnnotationMap.get(modelClass);
				if (null == modelClassAnnotation) {
					modelClassAnnotation = new ModelClassAnnotation(modelClass);
					modelClassAnnotationMap.put(modelClass, modelClassAnnotation);
				}
			}
		}
		return modelClassAnnotation;
	}

}
