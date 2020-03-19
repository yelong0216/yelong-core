/**
 * 
 */
package org.yelong.core.jdbc.record;

import java.util.Map;

/**
 * @author PengFei
 * @date 2019年12月22日上午2:03:14
 * @since 1.0
 */
public class DefaultRecordFactory implements RecordFactory{

	@Override
	public Record create(Map<String, Object> columns) {
		return new DefaultRecord().setColumns(columns);
	}

}
