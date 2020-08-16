/**
 * 
 */
package org.yelong.core.model.manage.wrapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.yelong.core.model.manage.ExtendColumnAttribute;
import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.ModelAndTable;
import org.yelong.core.model.manage.SelectColumnCondition;

/**
 * 字段列包装器
 * 
 * @since 2.0
 */
public class FieldAndColumnWrapper implements FieldAndColumn {

	private final FieldAndColumn fieldAndColumn;

	public FieldAndColumnWrapper(FieldAndColumn fieldAndColumn) {
		this.fieldAndColumn = fieldAndColumn;
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
	public static FieldAndColumnWrapper newInstance(Class<? extends FieldAndColumnWrapper> fieldAndColumnWrapperClass,
			FieldAndColumn fieldAndColumn) throws NoSuchMethodException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Constructor<? extends FieldAndColumnWrapper> constructor = fieldAndColumnWrapperClass
				.getConstructor(FieldAndColumn.class);
		FieldAndColumnWrapper fieldAndColumnWrapper = constructor.newInstance(fieldAndColumn);
		return fieldAndColumnWrapper;
	}

	@Override
	public ModelAndTable getModelAndTable() {
		return getFieldAndColumn().getModelAndTable();
	}

	@Override
	public Field getField() {
		return getFieldAndColumn().getField();
	}

	@Override
	public String getFieldName() {
		return getFieldAndColumn().getFieldName();
	}

	@Override
	public Class<?> getFieldType() {
		return getFieldAndColumn().getFieldType();
	}

	@Override
	public String getColumn() {
		return getFieldAndColumn().getColumn();
	}

	@Override
	public String getSelectColumn() {
		return getFieldAndColumn().getSelectColumn();
	}

	@Override
	public SelectColumnCondition getSelectColumnCondition() {
		return getFieldAndColumn().getSelectColumnCondition();
	}

	@Override
	public boolean isSelect() {
		return getFieldAndColumn().isSelect();
	}

	@Override
	public boolean isExtend() {
		return getFieldAndColumn().isExtend();
	}

	@Override
	public ExtendColumnAttribute getExtendColumnAttribute() {
		return getFieldAndColumn().getExtendColumnAttribute();
	}

	@Override
	public boolean isPrimaryKey() {
		return getFieldAndColumn().isPrimaryKey();
	}

	@Override
	public Long getMinLength() {
		return getFieldAndColumn().getMinLength();
	}

	@Override
	public Long getMaxLength() {
		return getFieldAndColumn().getMaxLength();
	}

	@Override
	public boolean isAllowBlank() {
		return getFieldAndColumn().isAllowBlank();
	}

	@Override
	public boolean isAllowNull() {
		return getFieldAndColumn().isAllowNull();
	}

	@Override
	public String getJdbcType() {
		return getFieldAndColumn().getJdbcType();
	}

	@Override
	public String getDesc() {
		return getFieldAndColumn().getDesc();
	}

	@Override
	public String getColumnName() {
		return getFieldAndColumn().getColumnName();
	}

	@Override
	public boolean equals(FieldAndColumn fieldAndColumn) {
		return getFieldAndColumn().equals(fieldAndColumn);
	}

	@Override
	public void initBelongModelAndTable(ModelAndTable modelAndTable) {
		getFieldAndColumn().initBelongModelAndTable(modelAndTable);
	}

	@Override
	public boolean isTransient() {
		return getFieldAndColumn().isTransient();
	}

	@Override
	public boolean equals(Object obj) {
		return getFieldAndColumn().equals(obj);
	}

	@Override
	public String toString() {
		return getFieldName() + ":" + getColumn();
	}

	public FieldAndColumn getFieldAndColumn() {
		return fieldAndColumn;
	}

}
