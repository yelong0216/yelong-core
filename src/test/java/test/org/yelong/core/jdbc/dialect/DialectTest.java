/**
 * 
 */
package test.org.yelong.core.jdbc.dialect;

import java.util.Arrays;

import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.dialect.Dialects;

/**
 * @author PengFei
 *
 */
public class DialectTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Dialect dialect = Dialects.ORACLE.getDialect();
		String baseSelectSql = dialect.getBaseSelectSql("CO_USER", "u", Arrays.asList("username","password","id","createTime"));
		System.out.println(baseSelectSql);
	}

}
