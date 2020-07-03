/**
 * 
 */
package org.yelong.core.model.sql;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.ClassUtils;

/**
 * 自定义model 的 sql。 定义的sql在{@link ModelSqlFragmentFactory}中进行使用
 * 定制在不同数据库方言使用不同的sql
 * 
 * 自定义的sql遵循以下规范： 1、同model、同方言、同操作类型在第二次添加时将会覆盖之前的
 * 
 * 一般在Model中的静态块中进行加载。当在类静态代码进行注册时，在调用{@link #getModelSql(Class, OperationType, String)}时要注意该
 * modelClass 已经被初始化了。
 * 只有class对象时，初始化类的方法：{@link ClassUtils#getClass(String, boolean)}的第二个参数为true即可
 * 
 * 
 * 目前只支持定制查询sql
 * 
 * @author PengFei
 * @since 1.0.7
 */
public final class CustomModelSql implements Comparable<CustomModelSql> {

	private static final Map<Class<?>, Set<CustomModelSql>> CUSTOM_MODEL_SQL_MAP = new HashMap<>();

	/**
	 * 注册查询sql且不指定方言
	 * 
	 * @param modelClass
	 * @param sql
	 */
	public static boolean registerSelect(Class<?> modelClass, String sql) {
		return registerSelect(modelClass, null, sql);
	}

	/**
	 * 注册指定方言的model sql
	 * 
	 * @param modelClass
	 * @param dialect    方言
	 * @param sql
	 */
	public static boolean registerSelect(Class<?> modelClass, String dialect, String sql) {
		return register(modelClass, OperationType.SELECT, dialect, sql);
	}

	private static synchronized boolean register(Class<?> modelClass, OperationType operationType, String dialect,
			String sql) {
		Set<CustomModelSql> set = CUSTOM_MODEL_SQL_MAP.get(modelClass);
		if (null == set) {
			set = new TreeSet<>();
			CUSTOM_MODEL_SQL_MAP.put(modelClass, set);
		}
		return set.add(new CustomModelSql(modelClass, operationType, dialect, sql));
	}

	/**
	 * 根据model类型、操作类型、方言获取注册的sql。 如果没有找到指定方言的sql，将使用没有注册方言的sql。均没有找到返回
	 * <code>null</code>
	 * 
	 * @param modelClass    model class
	 * @param operationType 操作类型
	 * @param dialect       方言
	 * @return 注册的sql
	 */
	public static String getModelSql(Class<?> modelClass, OperationType operationType, String dialect) {
		Set<CustomModelSql> set = CUSTOM_MODEL_SQL_MAP.get(modelClass);
		if (null == set) {
			return null;
		}
		CustomModelSql customModelSql = set.stream().filter(x -> {
			return (x.operationType == operationType) && (x.dialect == null || x.dialect.equals(dialect));
		}).findFirst().get();
		return customModelSql == null ? null : customModelSql.getSql();
	}

	private final Class<?> modelClass;

	private final OperationType operationType;

	private final String dialect;

	private final String sql;

	private CustomModelSql(Class<?> modelClass, OperationType operationType, String dialect, String sql) {
		this.modelClass = Objects.requireNonNull(modelClass);
		this.operationType = Objects.requireNonNull(operationType);
		this.dialect = dialect;
		this.sql = sql;
	}

	public Class<?> getModelClass() {
		return modelClass;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public String getDialect() {
		return dialect;
	}

	public String getSql() {
		return sql;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CustomModelSql)) {
			return false;
		}
		// 当model类型、操作类型、方言相同时，表示相同
		CustomModelSql cms = (CustomModelSql) obj;
		if (this.modelClass != cms.modelClass) {
			return false;
		}
		if (this.operationType != cms.operationType) {
			return false;
		}
		if (this.dialect != null && this.dialect.equals(cms.dialect)) {
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(CustomModelSql o) {
		// 方言为null小于方言不为null
		if (this.dialect != null & o.dialect != null) {
			return 0;
		}
		if (this.dialect == null & this.dialect == o.dialect) {
			return 0;
		}
		if (this.dialect == null) {
			return 1;
		} else {
			return -1;
		}
	}

	@Override
	public String toString() {
		return "CustomModelSql [modelClass=" + modelClass + ", operationType=" + operationType + ", dialect=" + dialect
				+ ", sql=" + sql + "]";
	}

	public static enum OperationType {

		INSERT, DELETE, UPDATE, SELECT

	}

}
