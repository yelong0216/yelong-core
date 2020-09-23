/**
 * 
 */
package org.yelong.core.model.manage;

import java.util.List;
import java.util.stream.Collectors;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.manage.exception.ModelAndTableException;
import org.yelong.core.model.manage.exception.PrimaryKeyException;
import org.yelong.core.model.resolve.ModelResolver;
import org.yelong.core.model.sql.SelectSqlColumnMode;

/**
 * 模型与表的映射信息
 * 
 * @see ModelResolver
 * @since 1.0
 */
public interface ModelAndTable {

	/**
	 * 获取模型的类型
	 * 
	 * @return model type
	 */
	Class<? extends Modelable> getModelClass();

	/**
	 * @return 模型类名称
	 */
	default String getModelName() {
		return getModelClass().getName();
	}

	/**
	 * 是否是一个视图
	 * 
	 * @return <code>true</code>是一个视图
	 */
	boolean isView();

	/**
	 * @return model 对应的表名
	 */
	@Nullable
	String getTableName();

	/**
	 * @return 表别名。默认为model类型首字母小写
	 */
	String getTableAlias();

	/**
	 * @return 表的描述
	 */
	@Nullable
	String getTableDesc();

	/**
	 * @return 查询SQL的列模式
	 * @since 2.0
	 */
	@Nullable
	SelectSqlColumnMode getSelectSqlColumnMode();

	// ==================================================SQL==================================================

	/**
	 * @return 指定查询该模型的SQL
	 * @since 2.1.4
	 */
	@Nullable
	String getSelectSql();

	/**
	 * @return 指定删除该模型的SQL
	 * @since 2.1.4
	 */
	@Nullable
	String getDeleteSql();

	/**
	 * @return 指定查询该模型记录数的SQL
	 * @since 2.1.4
	 */
	@Nullable
	String getCountSql();

	// ==================================================FieldAndColumn==================================================

	/**
	 * 获取指定类型的字段列。如果不指定类型则返回所有的字段列
	 * 
	 * @param fieldAndColumnTypes 字段列类型
	 * @return 指定类型的字段列集合
	 */
	List<FieldAndColumn> getFieldAndColumns(@Nullable FieldAndColumnType... fieldAndColumnTypes);

	/**
	 * 根据字段名称获取字段列 如果这个模型表中不存在这个字段的映射则返回 <code>null</code>
	 * 
	 * @param fieldName 字段名名称
	 * @return 字段映射的列信息
	 */
	default FieldAndColumn getFieldAndColumn(String fieldName) {
		return getFieldAndColumns().parallelStream().filter(x -> x.getFieldName().equals(fieldName)).findFirst()
				.orElse(null);
	}

	/**
	 * 获取普通列和主键列，及当前模型字段与表列一一对应的字段列
	 * 
	 * @return 普通列和主键列
	 */
	default List<FieldAndColumn> getNormalFieldAndColumns() {
		return getFieldAndColumns(FieldAndColumnType.ORDINARY, FieldAndColumnType.PRIMARYKEY);
	}

	/**
	 * 获取所有的主键。这个主键可能不是表中的主键，而是在model中进行标注的主键列。
	 * 
	 * @return 所有主键字段列。
	 */
	default List<FieldAndColumn> getPrimaryKeys() {
		return getFieldAndColumns(FieldAndColumnType.PRIMARYKEY);
	}

	/**
	 * 获取映射的唯一主键。
	 * 
	 * @return 映射的唯一的主键
	 * @throws PrimaryKeyException 映射的主键数量不等于1
	 * @since 1.3
	 */
	default FieldAndColumn getOnlyPrimaryKey() throws PrimaryKeyException {
		List<FieldAndColumn> primaryKeys = getPrimaryKeys();
		if (primaryKeys.isEmpty()) {
			throw new PrimaryKeyException(getModelClass(), getModelClass() + "不存在主键列!");
		} else if (primaryKeys.size() > 1) {
			throw new PrimaryKeyException(getModelClass(), getModelClass() + "存在" + primaryKeys.size() + "主键列["
					+ primaryKeys.stream().map(FieldAndColumn::getColumn).collect(Collectors.joining(",")) + "]");
		} else {
			return primaryKeys.get(0);
		}
	}

	/**
	 * 是否存在主键标识
	 * 
	 * @return <tt>true</tt> 存在主键标识
	 */
	default boolean existPrimaryKey() {
		return getPrimaryKeys().isEmpty();
	}

	/**
	 * 初始化所持有的所有字段列<br/>
	 * 这个方法只允许被调用一次，调用该方法一次之后每次调用均会抛出 {@link ModelAndTableException}<br/>
	 * 这个方法一般在 {@link ModelResolver}中被调用
	 * 
	 * @param fieldAndColumns 该模型表所有的字段与列的映射
	 * @since 2.0
	 * @throws ModelAndTableException 在被调用第二次时会抛出
	 */
	void initPossessFieldAndColumns(List<FieldAndColumn> fieldAndColumns) throws ModelAndTableException;

	/**
	 * 是否相同
	 * 
	 * @param modelAndTable modelAndTable
	 * @return <code>true</code> 相同
	 * @since 1.3
	 */
	boolean equals(ModelAndTable modelAndTable);

}
