/**
 * 
 */
package org.yelong.core.model.map;

import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.yelong.commons.util.map.CaseInsensitiveMapUtils;
import org.yelong.commons.util.map.CaseInsensitiveMapUtils.KeyStoreMode;
import org.yelong.core.jdbc.record.Record;

/**
 * map model
 * 
 * key为列、字段 <br/>
 * value为列、字段对应的值
 * 
 * 默认使用 {@link CaseInsensitiveMap}，该map忽略大小写。且默认存储方式为大写。
 * 通过构造方法来设置忽略大小写的map存储的key是大写还是小写{@link KeyStoreMode}
 * 
 * @since 1.1
 */
public abstract class MapModel extends Record implements MapModelable {

	private static final long serialVersionUID = -8499485120187577971L;

	public MapModel() {
		this(KeyStoreMode.UPPER);
	}

	public MapModel(KeyStoreMode keyStoreMode) {
		super(CaseInsensitiveMapUtils.createCaseInsensitiveMap(keyStoreMode));
	}

	public MapModel(Supplier<Map<String, Object>> mapFactory) {
		super(mapFactory.get());
	}

}
