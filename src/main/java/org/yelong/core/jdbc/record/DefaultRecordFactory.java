/**
 * 
 */
package org.yelong.core.jdbc.record;

import java.util.Map;

/**
 * 默认的记录工厂
 * @author PengFei
 */
public class DefaultRecordFactory implements RecordFactory{

	@Override
	public Record create(Map<String, Object> columns) {
		return new DefaultRecord().setColumns(columns);
	}

}
