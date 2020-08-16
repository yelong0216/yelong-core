/**
 * 
 */
package org.yelong.core.jdbc.sql;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 拼接sql工具类
 */
public class SpliceSqlUtils {

	private static final String EMPTY = StringUtils.EMPTY;

	private static final String SPACE = " ";

	/**
	 * 拼接sql片段<br/>
	 * 所有sql片段均用" "进行拼接<br/>
	 * 拼接后的片段前后均不带空格
	 * 
	 * @param fragments 片段集合
	 * @return "fragment1 fragment2 ..."
	 */
	public static String spliceSqlFragment(List<String> fragments) {
		if (CollectionUtils.isEmpty(fragments)) {
			return EMPTY;
		}
		return spliceSqlFragment(fragments.toArray(new String[] {}));
	}

	/**
	 * 拼接sql片段<br/>
	 * 所有sql片段均用" "进行拼接<br/>
	 * 拼接后的片段前后均不带空格
	 * 
	 * @param fragment 片段数组
	 * @return "fragment1 fragment2 ..."
	 */
	public static String spliceSqlFragment(String... fragment) {
		if (ArrayUtils.isEmpty(fragment)) {
			return EMPTY;
		}
		if (fragment.length == 1) {
			return fragment[0];
		}
		StringBuilder sqlFragment = new StringBuilder();
		for (String string : fragment) {
			sqlFragment.append(SPACE);
			sqlFragment.append(string);
		}
		sqlFragment.delete(0, SPACE.length());
		return sqlFragment.toString();
	}

}
