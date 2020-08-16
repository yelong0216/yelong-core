package org.yelong.core.model.pojo.annotation;

import java.util.List;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.annotation.support.ModelClassAnnotation;
import org.yelong.core.model.annotation.support.ModelClassAnnotations;
import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.ModelAndTable;
import org.yelong.core.model.manage.support.DefaultModelAndTable;
import org.yelong.core.model.pojo.AbstractPOJOModelResolver;
import org.yelong.core.model.resolve.ModelResolveException;
import org.yelong.core.model.resolve.ModelResolveScope;

/**
 * 注解普通java类模型解析器。解析范围：所有模型{@link Modelable}
 * 
 * @since 2.0
 */
@ModelResolveScope(Modelable.class)
public class AnnotationPOJOModelResolver extends AbstractPOJOModelResolver {

	@Override
	public ModelAndTable resolve(Class<? extends Modelable> modelClass) throws ModelResolveException {
		ModelClassAnnotation modelClassAnnotation = new ModelClassAnnotation(modelClass);
		DefaultModelAndTable modelAndTable = ModelClassAnnotations.createModelAndTable(modelClassAnnotation,
				DefaultModelAndTable::new);
		ModelClassAnnotations.setProperty(modelAndTable, modelClassAnnotation);
		List<FieldAndColumn> fieldAndColumns = getFieldAndColumns(modelClass);
		modelAndTable.initPossessFieldAndColumns(fieldAndColumns);
		return modelAndTable;
	}

}
