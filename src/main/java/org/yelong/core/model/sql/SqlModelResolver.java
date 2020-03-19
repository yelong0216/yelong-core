/**
 * 
 */
package org.yelong.core.model.sql;

import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.combination.CombinationConditionSqlFragment;
import org.yelong.core.jdbc.sql.condition.support.Condition;
import org.yelong.core.jdbc.sql.condition.support.ConditionResolver;
import org.yelong.core.jdbc.sql.factory.SqlFragmentFactory;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;
import org.yelong.core.model.resolve.ModelAndTableManager;

/**
 * @author PengFei
 * @date 2020年3月2日上午10:20:53
 * @since 1.0
 */
public interface SqlModelResolver {
	
	@Nullable
	default ConditionSqlFragment resolveToCondition(SqlModel sqlModel) {
		return resolveToCondition(sqlModel, true);
	}
	
	/**
	 * 解析sqlModel为条件
	 * 
	 * 1、sqlModel中所有映射且非空的列均设置为条件且都已AND连接
	 * 2、条件默认已“=”为操作符。这个操作符可以通过 {@link SqlModel#addConditionOperator(String, String)}来设置
	 * 3、如果defaultTableAlias = true，将会在添加条件中默认添加SqlModel映射的表的别名
	 * 4、sqlModel的拓展属性同样遵循以上原则
	 * 5、sqlModel的拓展属性如果携带别名(存在“.”)将不会添加默认别名
	 * 6、拓展字段会覆盖与sqlModel中相同的名称的字段
	 * 7、{@link Condition}对象可以完成复杂的条件拼接。这个和{@link CombinationConditionSqlFragment}功能相似。具体详见{@link ConditionResolver}
	 * 
	 * @param sqlModel
	 * @param tableAlias
	 * @return
	 */
	@Nullable
	ConditionSqlFragment resolveToCondition(SqlModel sqlModel , boolean tableAlias);
	
	@Nullable
	default SortSqlFragment resolveToSort(SqlModel sqlModel) {
		return resolveToSort(sqlModel, true);
	}
	
	/**
	 * 解析{@link SqlModel#getSortFieldMap()}为排序sql
	 * 1、如果defaultTableAlias = true，将会在排序的列中默认添加SqlModel映射的表的别名。
	 * 2、如果列自带别名(存在“.”)将不会添加默认别名
	 * @param sqlModel
	 * @param tableAlias 是否在字段前添加表别名
	 * @return
	 */
	@Nullable
	SortSqlFragment resolveToSort(SqlModel sqlModel , boolean tableAlias);
	
	ModelAndTableManager getModelAndTableManager();
	
	SqlFragmentFactory getSqlFragmentFactory();
	
}
