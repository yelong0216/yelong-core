/**
 * 
 */
package org.yelong.core.jdbc.sql.condition.simple;

import org.yelong.core.jdbc.sql.SqlFragment;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;

/**
 * 简单的条件
 * 直接由条件sql和参数组成。这和{@link SqlFragment}中的方法相匹配。只不过这是条件的片段
 * @author PengFei
 */
public interface SimpleConditionSqlFragment extends ConditionSqlFragment{
	
}
