/**
 * 
 */
package test.org.yelong.core.model.annotation.support;

import org.junit.jupiter.api.Test;
import org.yelong.core.model.annotation.support.ModelClassAnnotation;
import org.yelong.core.model.annotation.support.ModelClassAnnotationFactory;

import test.org.yelong.core.model.User;

/**
 * @since
 */
public class ModelClassAnnotationFactoryTest {

	@Test
	public void get() {
		ModelClassAnnotation modelClassAnnotation = ModelClassAnnotationFactory.get(User.class);
		System.out.println(modelClassAnnotation.getTableName());
		ModelClassAnnotation modelClassAnnotation2 = ModelClassAnnotationFactory.get(User.class);
		System.out.println(modelClassAnnotation == modelClassAnnotation2);
	}

}
