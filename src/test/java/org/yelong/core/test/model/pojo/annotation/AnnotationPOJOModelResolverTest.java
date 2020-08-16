/**
 * 
 */
package org.yelong.core.test.model.pojo.annotation;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.ModelAndTable;
import org.yelong.core.model.map.MapModel;
import org.yelong.core.model.pojo.POJOModelResolver;
import org.yelong.core.model.pojo.annotation.AnnotationFieldResolver;
import org.yelong.core.model.pojo.annotation.AnnotationPOJOModelResolver;
import org.yelong.core.model.pojo.field.FieldResolver;
import org.yelong.core.model.resolve.ModelResolveException;
import org.yelong.core.test.model.User;
import org.yelong.core.test.model.UserView;
import org.yelong.test.ObjectTests;

public class AnnotationPOJOModelResolverTest {

	public static POJOModelResolver modelResolver = new AnnotationPOJOModelResolver();

	static {
		modelResolver.registerFieldResovler(new AnnotationFieldResolver());
	}

	@Test
	public void method() {
		System.out.println(modelResolver.existFieldResolver());
		Class<? extends Modelable>[] resolveScopes = modelResolver.getResolveScopes();
		System.out.println(Arrays.asList(resolveScopes));
		List<FieldResolver> fieldResolvers = modelResolver.getAllFieldResolver();
		System.out.println(fieldResolvers);
		Field field = FieldUtils.getField(MapModel.class, "sourceMap", true);
		System.out.println(field);
		FieldResolver fieldResolver = modelResolver.getOptimalFieldResolver(MapModel.class, field);
		System.out.println(fieldResolver);
	}

	@Test
	public void resolve() {
		try {
			ModelAndTable modelAndTable = modelResolver.resolve(User.class);
			System.out.println(modelAndTable);
			List<FieldAndColumn> fieldAndColumns = modelAndTable.getFieldAndColumns();
			System.out.println(fieldAndColumns);
			FieldAndColumn onlyPrimaryKey = modelAndTable.getOnlyPrimaryKey();
			System.out.println(onlyPrimaryKey);
		} catch (ModelResolveException e) {
			e.printStackTrace();
			System.out.println(e.getModelResolver());
			System.out.println(e.getModelClass());
			System.out.println(e.getMessage());
		}

	}
	
	@Test
	public void resolveView() {
		ModelAndTable modelAndTable = modelResolver.resolve(UserView.class);
		ObjectTests.printAllField(modelAndTable);
	}
	
}
