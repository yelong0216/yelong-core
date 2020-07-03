/**
 * 
 */
package org.yelong.core.model.map;

import java.lang.reflect.Field;
import java.util.List;

import org.yelong.core.model.resolve.ModelAndTable;

/**
 * @author PengFei
 * @since 1.0.7
 */
public interface MapModelAndTable extends ModelAndTable {

	@Override
	default List<Field> getFields() {
		throw new UnsupportedOperationException(" map model 中不存在“字段”，每一个key都是列'column'");
	}

	/**
	 * @return map model字段与列的获取策略
	 */
	MapModelFieldAndColumnGetStrategy getMapModelFieldAndColumnGetStrategy();

}
