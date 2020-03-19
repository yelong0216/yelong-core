/**
 * 
 */
package org.yelong.core.jdbc.record;

import java.util.Map;

/**
 * 记录工厂
 * @author PengFei
 */
public interface RecordFactory {
	
	/**
	 * 创建记录
	 * @param columns 数据库列映射
	 * @return 记录
	 */
	Record create(Map<String,Object> columns);

}
