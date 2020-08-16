/**
 * 
 */
package org.yelong.core.test.model.sql;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.model.sql.DefaultSqlModelResolver;
import org.yelong.core.model.sql.SqlModel;
import org.yelong.core.model.sql.SqlModelResolver;
import org.yelong.core.test.jdbc.sql.condition.support.ConditionResolverTest;
import org.yelong.core.test.model.User;
import org.yelong.core.test.model.manager.ModelManagerTest;

/**
 * @since
 */
public class SqlModelResolverTest {

	public static final SqlModelResolver sqlModelResolver = new DefaultSqlModelResolver(ModelManagerTest.modelManager,
			ConditionResolverTest.conditionResolver);

	@Test
	public void resolverToCondition_null() {
		SqlModel<?> sqlModel = new SqlModel<>();
		ConditionSqlFragment conditionSqlFragment = sqlModelResolver.resolveToCondition(sqlModel);
		System.out.println(conditionSqlFragment.getBoundSql());
	}
	
	@Test
	public void resolverToCondition() {
		SqlModel<?> sqlModel = new SqlModel<>();
		sqlModel.addCondition("username", "IN", ArrayUtils.toArray("a","b"));
		ConditionSqlFragment conditionSqlFragment = sqlModelResolver.resolveToCondition(sqlModel);
		System.out.println(conditionSqlFragment.getBoundSql());
	}
	
	@Test
	public void resolverToConditionByModel() {
		User user = new User();
		user.setRealName("123");
		
		SqlModel<?> sqlModel = new SqlModel<>(user);
		sqlModel.addCondition("username", "IN", ArrayUtils.toArray("a","b"));
		ConditionSqlFragment conditionSqlFragment = sqlModelResolver.resolveToCondition(sqlModel);
		System.out.println(conditionSqlFragment.getBoundSql());
	}
	
	@Test
	public void resolverToConditionByModel2() {
		User user = new User();
		user.setRealName("123");
		ConditionSqlFragment conditionSqlFragment = sqlModelResolver.resolveToCondition(user);
		System.out.println(conditionSqlFragment.getBoundSql());
	}

}
