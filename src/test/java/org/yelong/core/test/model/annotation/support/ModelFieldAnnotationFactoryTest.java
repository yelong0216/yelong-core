/**
 * 
 */
package org.yelong.core.test.model.annotation.support;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;
import org.yelong.core.model.annotation.support.ModelFieldAnnotation;
import org.yelong.core.model.annotation.support.ModelFieldAnnotationFactory;
import org.yelong.core.test.model.User;

/**
 * @since
 */
public class ModelFieldAnnotationFactoryTest {

	@Test
	public void get() {
		ModelFieldAnnotation modelFieldAnnotation = ModelFieldAnnotationFactory.get(FieldUtils.getField(User.class, "id", true));
		System.out.println(modelFieldAnnotation.isPrimaryKey());
		ModelFieldAnnotation modelFieldAnnotation2 = ModelFieldAnnotationFactory.get(FieldUtils.getField(User.class, "id", true));
		System.out.println(modelFieldAnnotation == modelFieldAnnotation2);
		System.out.println(modelFieldAnnotation.equals(modelFieldAnnotation2));
	}
	
}
