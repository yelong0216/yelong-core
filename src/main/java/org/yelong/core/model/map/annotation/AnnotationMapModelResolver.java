/**
 * 
 */
package org.yelong.core.model.map.annotation;

import java.util.ArrayList;
import java.util.List;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.annotation.support.ModelClassAnnotation;
import org.yelong.core.model.annotation.support.ModelClassAnnotations;
import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.ModelAndTable;
import org.yelong.core.model.map.MapModelFieldAndColumn;
import org.yelong.core.model.map.MapModelFieldAndColumnGetStrategy;
import org.yelong.core.model.map.MapModelResolver;
import org.yelong.core.model.map.MapModelable;
import org.yelong.core.model.map.support.AbstractMapModelFieldAndColumn;
import org.yelong.core.model.map.support.DefaultMapModelAndTable;
import org.yelong.core.model.map.wrapper.MapModelFieldAndColumnWrapper;
import org.yelong.core.model.map.wrapper.TransientMapModelFieldAndColumnWrapper;
import org.yelong.core.model.resolve.ModelResolveException;
import org.yelong.core.model.resolve.ModelResolveScope;

/**
 * 注解Map模型解析器。解析范围{@link MapModelable}
 * 
 * @since 1.1
 */
@ModelResolveScope(MapModelable.class)
public class AnnotationMapModelResolver implements MapModelResolver {

	private MapModelFieldAndColumnGetStrategy mapModelFieldAndColumnGetStrategy;

	public AnnotationMapModelResolver(MapModelFieldAndColumnGetStrategy mapModelFieldAndColumnGetStrategy) {
		this.mapModelFieldAndColumnGetStrategy = mapModelFieldAndColumnGetStrategy;
	}

	@Override
	public ModelAndTable resolve(Class<? extends Modelable> modelClass) throws ModelResolveException {
		if (!MapModelable.class.isAssignableFrom(modelClass)) {
			throw new UnsupportedOperationException();
		}
		ModelClassAnnotation modelClassAnnotation = new ModelClassAnnotation(modelClass);
		DefaultMapModelAndTable mapModelAndTable = ModelClassAnnotations.createModelAndTable(modelClassAnnotation,
				DefaultMapModelAndTable::new);
		ModelClassAnnotations.setProperty(mapModelAndTable, modelClassAnnotation);
		List<MapModelFieldAndColumn> fieldAndColumns = mapModelFieldAndColumnGetStrategy.get(mapModelAndTable);
		List<FieldAndColumn> finalFieldAndColumns = new ArrayList<>(fieldAndColumns.size());
		for (MapModelFieldAndColumn fieldAndColumn : fieldAndColumns) {
			// 临时字段列
			if (modelClassAnnotation.isTransient(fieldAndColumn.getFieldName())) {
				if (fieldAndColumn instanceof AbstractMapModelFieldAndColumn) {
					((AbstractMapModelFieldAndColumn) fieldAndColumn).setTransient(true);
				} else {
					fieldAndColumn = new TransientMapModelFieldAndColumnWrapper(fieldAndColumn);
				}
			}

			String fieldName = fieldAndColumn.getFieldName();
			FieldAndColumn finalFieldAndColumn = null;
			@SuppressWarnings("unchecked")
			Class<? extends MapModelFieldAndColumnWrapper> fieldAndColumnWrapperClass = (Class<? extends MapModelFieldAndColumnWrapper>) modelClassAnnotation
					.getFieldAndColumnWrapperClass(fieldAndColumn.getFieldName());
			if (null != fieldAndColumnWrapperClass) {
				try {
					finalFieldAndColumn = MapModelFieldAndColumnWrapper.newInstance(fieldAndColumnWrapperClass,
							fieldAndColumn);
				} catch (Exception e) {
					throw new ModelResolveException(this, modelClass, fieldName + "包装失败，实例化"
							+ fieldAndColumnWrapperClass + "错误，包装器类必须存在单一参数类型[" + FieldAndColumn.class + "]的构造方法！", e);
				}
			} else {
				finalFieldAndColumn = fieldAndColumn;
			}
			finalFieldAndColumns.add(finalFieldAndColumn);
		}

		mapModelAndTable.initPossessFieldAndColumns(finalFieldAndColumns);
		ModelClassAnnotations.setProperty(mapModelAndTable, modelClassAnnotation);
		return mapModelAndTable;
	}

}
