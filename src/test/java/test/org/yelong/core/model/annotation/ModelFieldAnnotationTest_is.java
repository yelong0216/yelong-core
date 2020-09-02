/**
 * 
 */
package test.org.yelong.core.model.annotation;

import java.lang.reflect.Field;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.annotation.ExtendColumns;
import org.yelong.core.model.annotation.PrimaryKeys;
import org.yelong.core.model.annotation.support.ModelFieldAnnotation;

/**
 *
 */
public class ModelFieldAnnotationTest_is {

	@Test
	public void isId() {
		Field field = FieldUtils.getField(Us.class, "id", true);
		ModelFieldAnnotation modelFieldAnnotation = new ModelFieldAnnotation(UsE.class, field);
		System.out.println(modelFieldAnnotation.isPrimaryKey());
	}

	@Test
	public void isTran() {
		Field field = FieldUtils.getField(Us.class, "username", true);
		ModelFieldAnnotation modelFieldAnnotation = new ModelFieldAnnotation(UsE.class, field);
		System.out.println(modelFieldAnnotation.isTransient());
	}
	
	@Test
	public void isExtend() {
		Field field = FieldUtils.getField(UsE.class, "username", true);
		ModelFieldAnnotation modelFieldAnnotation = new ModelFieldAnnotation(UsE.class, field);
		System.out.println(modelFieldAnnotation.isExtendColumn());
	}

	static class Us implements Modelable {

		private static final long serialVersionUID = 297473363286009207L;

		protected String id;

		protected String username;

	}

	@ExtendColumns("username")
	@PrimaryKeys("id")
//	@Transients("username")
//	@ExtendTable
	static class UsE extends Us {

		private static final long serialVersionUID = 3288061605780483163L;

	}

}
