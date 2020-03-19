/**
 * 
 */
package org.yelong.core.jdbc.record;

import java.util.Map;

/**
 * @author PengFei
 * @date 2019年12月22日上午2:02:08
 * @since 1.0
 */
public interface RecordFactory {
	
	/**
	 * 创建记录
	 * @param columns 数据库列映射
	 * @return 记录
	 */
	Record create(Map<String,Object> columns);

}
