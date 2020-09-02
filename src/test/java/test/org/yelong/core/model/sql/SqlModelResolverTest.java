/**
 * 
 */
package test.org.yelong.core.model.sql;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.model.sql.DefaultSqlModelResolver;
import org.yelong.core.model.sql.SqlModel;
import org.yelong.core.model.sql.SqlModelResolver;

import test.org.yelong.core.jdbc.sql.condition.support.ConditionResolverTest;
import test.org.yelong.core.model.User;
import test.org.yelong.core.model.manager.ModelManagerTest;

/**
 * @since
 */
public class SqlModelResolverTest {

	public static final SqlModelResolver sqlModelResolver = new DefaultSqlModelResolver(ModelManagerTest.modelManager,
			ConditionResolverTest.conditionResolver);

	@Test
	public void resolverToCondition_null() {
		SqlModel<?> sqlModel = new SqlModel<>();
		ConditionSqlFragment conditionSqlFragment = sqlModelResolver.resolveToConditionSqlFragment(sqlModel);
		System.out.println(conditionSqlFragment.getBoundSql());
	}

	@Test
	public void resolverToCondition() {
		SqlModel<?> sqlModel = new SqlModel<>();
		sqlModel.addCondition("username", "IN", ArrayUtils.toArray("a", "b"));
		ConditionSqlFragment conditionSqlFragment = sqlModelResolver.resolveToConditionSqlFragment(sqlModel);
		System.out.println(conditionSqlFragment.getBoundSql());
	}

	@Test
	public void resolverToConditionByModel() {
		User user = new User();
		user.setRealName("123");

		SqlModel<?> sqlModel = new SqlModel<>(user);
		sqlModel.addCondition("username", "IN", ArrayUtils.toArray("a", "b"));
		ConditionSqlFragment conditionSqlFragment = sqlModelResolver.resolveToConditionSqlFragment(sqlModel);
		System.out.println(conditionSqlFragment.getBoundSql());
	}

	@Test
	public void resolverToConditionByModel2() {
		User user = new User();
		user.setRealName("123");
		ConditionSqlFragment conditionSqlFragment = sqlModelResolver.resolveToConditionSqlFragment(user);
		System.out.println(conditionSqlFragment.getBoundSql());
	}

}
