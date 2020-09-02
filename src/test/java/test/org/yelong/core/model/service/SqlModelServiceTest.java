/**
 * 
 */
package test.org.yelong.core.model.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.yelong.core.jdbc.dialect.Dialects;
import org.yelong.core.jdbc.sql.group.GroupSqlFragment;
import org.yelong.core.model.ModelConfigurationBuilder;
import org.yelong.core.model.convertor.CaseInsensitiveModelAndMapConvertor;
import org.yelong.core.model.service.JdbcModelService;
import org.yelong.core.model.service.SqlModelService;
import org.yelong.core.model.sql.ModelSqlFragmentFactory;
import org.yelong.core.model.sql.SqlModel;

import test.org.yelong.core.jdbc.BaseDataBaseOperationTest;
import test.org.yelong.core.model.User;

/**
 * @since
 */
public class SqlModelServiceTest {

	public static final SqlModelService modelService = new JdbcModelService(
			ModelConfigurationBuilder.create(Dialects.MYSQL.getDialect()).build(), BaseDataBaseOperationTest.mysqlDb,
			CaseInsensitiveModelAndMapConvertor.INSTANCE);

	@Test
	public void findBySqlModel_01() {
		SqlModel<?> sqlModel = new SqlModel<>();
		List<User> users = modelService.findBySqlModel(User.class, sqlModel);
		users.stream().map(User::getUsername).forEach(System.out::println);
	}

	@Test
	public void findBySqlModel_02() {
		SqlModel<?> sqlModel = new SqlModel<>();
		sqlModel.addCondition("username", "<>", "123465");
		List<User> users = modelService.findBySqlModel(User.class, sqlModel);
		users.stream().map(User::getUsername).forEach(System.out::println);
	}

	@Test
	public void findBySqlModel_03() {
		SqlModel<User> sqlModel = new SqlModel<>(User.class);
		sqlModel.addGroupColumns("username");
		sqlModel.addCondition("username", "<>", "123465");
		sqlModel.addSortField("createTime", "DESC");
		List<User> users = modelService.findBySqlModel(User.class, sqlModel);
		users.stream().map(User::getUsername).forEach(System.out::println);
	}

	@Test
	public void find_groupSqlFragment() {
		ModelSqlFragmentFactory modelSqlFragmentFactory = modelService.getModelConfiguration().getModelSqlFragmentFactory();
		GroupSqlFragment groupSqlFragment = modelSqlFragmentFactory.createGroupSqlFragment();
		groupSqlFragment.addGroup("gender");
		List<User> users = modelService.findBySqlFragment(User.class, null,groupSqlFragment, null);
		System.out.println(users);
	}
	
}
