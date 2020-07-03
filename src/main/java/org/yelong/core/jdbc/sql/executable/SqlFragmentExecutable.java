/**
 * 
 */
package org.yelong.core.jdbc.sql.executable;

import org.yelong.core.jdbc.sql.SqlFragment;

/**
 * 标志性接口。<br/>
 * 可以执行的sql片段。<br/>
 * 可以使用{@link #getBoundSql()}获取sql和参数，直接放置jdbc中执行
 * 
 * @author PengFei
 */
public interface SqlFragmentExecutable extends SqlFragment {

}
