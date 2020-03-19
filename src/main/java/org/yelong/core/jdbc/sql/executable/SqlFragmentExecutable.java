/**
 * 
 */
package org.yelong.core.jdbc.sql.executable;

import org.yelong.core.jdbc.sql.SqlFragment;

/**
 * 标志性接口。
 * 可以执行的sql片段。
 * 使用此接口时，应先调用任意一个initialization 方法。
 * 可以使用{@link #getBoundSql()}获取sql和参数，直接放置jdbc中执行
 * @author 彭飞
 * @date 2019年10月31日上午10:22:14
 * @version 1.2
 */
public interface SqlFragmentExecutable extends SqlFragment{
	
}
