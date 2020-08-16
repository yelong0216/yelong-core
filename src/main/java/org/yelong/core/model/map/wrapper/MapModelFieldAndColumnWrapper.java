/**
 * 
 */
package org.yelong.core.model.map.wrapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.wrapper.FieldAndColumnWrapper;
import org.yelong.core.model.map.MapModelFieldAndColumn;

/**
 * Map模型字段列包装器
 * 
 * @since 2.0
 */
public class MapModelFieldAndColumnWrapper extends FieldAndColumnWrapper implements MapModelFieldAndColumn {

	public MapModelFieldAndColumnWrapper(MapModelFieldAndColumn fieldAndColumn) {
		super(fieldAndColumn);
	}

	/**
	 * 实例化 {@link FieldAndColumnWrapper}对象，且根据这个类的唯一类型参数 {@link FieldAndColumn}的构造器
	 * 
	 * @param fieldAndColumnWrapperClass 包装器类型
	 * @param fieldAndColumn             字段列对象
	 * @return 包装器对象
	 * @throws NoSuchMethodException     {@link Class#getConstructor(Class...)}
	 * @throws InstantiationException    {@link Constructor#newInstance(Object...)}
	 * @throws IllegalAccessException    {@link Constructor#newInstance(Object...)}
	 * @throws IllegalArgumentException  {@link Constructor#newInstance(Object...)}
	 * @throws InvocationTargetException {@link Constructor#newInstance(Object...)}
	 */
	public static MapModelFieldAndColumnWrapper newInstance(
			Class<? extends MapModelFieldAndColumnWrapper> fieldAndColumnWrapperClass,
			MapModelFieldAndColumn fieldAndColumn) throws NoSuchMethodException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<? extends MapModelFieldAndColumnWrapper> constructor = fieldAndColumnWrapperClass
				.getConstructor(MapModelFieldAndColumn.class);
		MapModelFieldAndColumnWrapper fieldAndColumnWrapper = constructor.newInstance(fieldAndColumn);
		return fieldAndColumnWrapper;
	}

}
