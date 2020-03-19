/**
 * 
 */
package org.yelong.core.jdbc.sql;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 彭飞
 * @date 2019年8月9日下午4:01:31
 * @version 1.0
 */
public class SpliceSqlUtils {
	
	private static final String EMPTY = StringUtils.EMPTY;
	
	private static final String SPACE = " ";
	
	/**
	 * 拼接sql片段<br/>
	 * 所有sql片段均用" "进行拼接<br/>
	 * 拼接后的片段前后均不带空格
	 * @author 彭飞
	 * @date 2019年8月9日下午4:05:30
	 * @version 1.0
	 * @param fragment 片段数组
	 * @return "fragment1 fragment2 ..."
	 */
	public static String spliceSqlFragment(String ... fragment) {
		if(ArrayUtils.isEmpty(fragment)) {
			return EMPTY;
		}
		if( fragment.length == 1 ) {
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
