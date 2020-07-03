/**
 * 
 */
package org.yelong.core.model.resolve;

import org.apache.commons.lang3.StringUtils;
import org.yelong.commons.annotation.AnnotationUtils;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.annotation.Count;
import org.yelong.core.model.annotation.Select;
import org.yelong.core.model.annotation.Table;

/**
 * model 类所支持的注解以及可以获取注解的值。
 * 
 * @author PengFei
 */
public class ModelClassAnnotation {

	private final Class<? extends Modelable> modelClass;

	private final Table table;

	private final Select select;

	private final Count count;

	/**
	 * @param modelClass model class
	 */
	public ModelClassAnnotation(final Class<? extends Modelable> modelClass) {
		this.modelClass = modelClass;
		this.table = AnnotationUtils.getAnnotation(modelClass, Table.class, true);
		this.select = AnnotationUtils.getAnnotation(modelClass, Select.class, true);
		this.count = AnnotationUtils.getAnnotation(modelClass, Count.class, true);
	}

	/**
	 * @return 表名
	 */
	public String getTableName() {
		return null == table ? null : table.value();
	}

	/**
	 * 别名
	 * 
	 * @return
	 */
	public String getTableAlias() {
		if (null == table) {
			return null;
		}
		String alias = table.alias();
		if (StringUtils.isBlank(alias)) {
			alias = modelClass.getSimpleName().substring(0, 1).toLowerCase() + modelClass.getSimpleName().substring(1);
		}
		return alias;
	}

	/**
	 * @return 表说明
	 */
	public String getTableDese() {
		return null == table ? null : table.desc();
	}

	/**
	 * @return count sql
	 */
	public String getCountSql() {
		return null == count ? null : count.value();
	}

	/**
	 * @return select sql
	 */
	public String getSelectSql() {
		return null == select ? null : select.value();
	}

	/**
	 * @return model class
	 */
	public Class<? extends Modelable> getModelClass() {
		return modelClass;
	}

	/**
	 * @return table annotation
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * @return select annotation
	 */
	public Select getSelect() {
		return select;
	}

	/**
	 * @return count annotation
	 */
	public Count getCount() {
		return count;
	}

}
