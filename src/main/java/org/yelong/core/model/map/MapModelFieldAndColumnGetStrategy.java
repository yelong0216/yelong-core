/**
 * 
 */
package org.yelong.core.model.map;

import java.util.List;

/**
 * map model的字段与列的获取策略。
 * 
 * 指定这个动态MapModel的列如何去获取
 * 一般通过数据库查询该表来获取
 * 
 * @author PengFei
 * @since 1.0.7
 */
public interface MapModelFieldAndColumnGetStrategy {

	/**
	 * 或者 map model 的所有映射列 
	 * @param mapModelAndTable 模型与表
	 * @return 模型与表映射的字段
	 */
	List<MapModelFieldAndColumn> get(MapModelAndTable mapModelAndTable);
	
}