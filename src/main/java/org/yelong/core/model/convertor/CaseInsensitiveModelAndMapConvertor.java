/**
 * 
 */
package org.yelong.core.model.convertor;

import java.util.Map;

import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.yelong.commons.util.map.CaseInsensitiveMapUtils;
import org.yelong.commons.util.map.CaseInsensitiveMapUtils.KeyStoreMode;
import org.yelong.core.model.Modelable;

/**
 * 在将Map转换为模型是忽略Map键值的大小写
 * 
 * @see DefaultModelAndMapConvertor
 * @see CaseInsensitiveMap
 * @since 2.0
 */
public class CaseInsensitiveModelAndMapConvertor extends DefaultModelAndMapConvertor {

	public static final ModelAndMapConvertor INSTANCE = new CaseInsensitiveModelAndMapConvertor();

	@Override
	public <M extends Modelable> M toModel(Map<String, Object> map, M model) {
		CaseInsensitiveMap<String, Object> caseInsensitiveMap = CaseInsensitiveMapUtils.createCaseInsensitiveMap(map,
				KeyStoreMode.UPPER);
		return super.toModel(caseInsensitiveMap, model);
	}

}
