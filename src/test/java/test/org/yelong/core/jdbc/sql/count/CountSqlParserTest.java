/**
 * 
 */
package test.org.yelong.core.jdbc.sql.count;

import org.junit.jupiter.api.Test;
import org.yelong.core.jdbc.sql.count.CountSqlParser;

/**
 * @author PengFei
 *
 */
public class CountSqlParserTest {

	public static CountSqlParser countSqlParser = new CountSqlParser();
	
	@Test
	public void test1() {
		String sql = "select * from CO_USER";
		System.out.println(countSqlParser.getSmartCountSql(sql));
	}
	
	@Test
	public void test2() {
		String sql = "select entrust.* from tb_sm_entrust_info entrust";
		System.out.println(countSqlParser.getSmartCountSql(sql));
	}
	
}
