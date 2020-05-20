/**
 * 
 */
package org.yelong.core.model.map;

import org.yelong.commons.lang.Strings;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.resolve.AnnotationModelResolver;
import org.yelong.core.model.resolve.ModelAndTable;
import org.yelong.core.model.resolve.ModelClassAnnotation;
import org.yelong.core.model.resolve.ModelResolveException;
import org.yelong.core.model.resolve.ModelResolver;

/**
 * @author PengFei
 */
public class AnnotationMapModelResolver extends AnnotationModelResolver implements ModelResolver{

	private MapModelFieldAndColumnGetStrategy mapModelFieldAndColumnGetStrategy;

	public AnnotationMapModelResolver(MapModelFieldAndColumnGetStrategy mapModelFieldAndColumnGetStrategy) {
		this.mapModelFieldAndColumnGetStrategy = mapModelFieldAndColumnGetStrategy;
	}
	
	@Override
	public <M extends Modelable> ModelAndTable resolve(Class<M> modelClass) throws ModelResolveException {
		if(!MapModel.class.isAssignableFrom(modelClass)) {
			return super.resolve(modelClass);
		}
		ModelClassAnnotation modelClassAnnotation = new ModelClassAnnotation(modelClass);
		if( null == modelClassAnnotation.getTable()) {
			throw new ModelResolveException("["+modelClass.getName()+"]及其父类均未标有Table注解，无法进行解析！");
		}
		DefaultMapModelAndTable mapModelAndTable = new DefaultMapModelAndTable(modelClass,Strings.requireNonBlank(modelClassAnnotation.getTableName(), "["+modelClass+"]声明的表名不允许为空!") , mapModelFieldAndColumnGetStrategy);
		abstractModelAndTableSetProperty(mapModelAndTable, modelClassAnnotation);
		return mapModelAndTable;
	}

}
