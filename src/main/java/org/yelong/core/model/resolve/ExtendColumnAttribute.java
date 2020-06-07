/**
 * 
 */
package org.yelong.core.model.resolve;

import java.util.Objects;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.model.Modelable;

/**
 * 拓展列属性
 * 
 * @author PengFei
 * @since 1.2.0
 */
public class ExtendColumnAttribute {

	private final FieldAndColumn fieldAndColumn;
	
	/**
	 * 拓展列所属的 model
	 */
	@Nullable
	private final Class<? extends Modelable> ofModelClass;
	
	/**
	 * 拓展列所属的表名
	 */
	@Nullable
	private final String ofTableName;
	
	/**
	 * 拓展列所属的表别名
	 */
	@Nullable
	private final String ofTableAlias;

	public ExtendColumnAttribute(FieldAndColumn fieldAndColumn,@Nullable Class<? extends Modelable> ofModelClass,
			@Nullable String ofTableName,@Nullable String ofTableAlias) {
		this.fieldAndColumn = Objects.requireNonNull(fieldAndColumn);
		this.ofModelClass = ofModelClass;
		this.ofTableName = ofTableName;
		this.ofTableAlias = ofTableAlias;
	}

	/**
	 * @return field and column
	 */
	public FieldAndColumn getFieldAndColumn() {
		return fieldAndColumn;
	}
	
	/**
	 * @return 该拓展列所属的 model 
	 */
	public Class<? extends Modelable> getOfModelClass() {
		return ofModelClass;
	}

	/**
	 * @return 该拓展列所属的表名
	 */
	public String getOfTableName() {
		return ofTableName;
	}

	/**
	 * @return 该拓展列所属的表的别名
	 */
	public String getOfTableAlias() {
		return ofTableAlias;
	}

}
