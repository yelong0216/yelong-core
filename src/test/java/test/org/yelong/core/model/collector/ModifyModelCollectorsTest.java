package test.org.yelong.core.model.collector;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;
import org.yelong.commons.util.Dates;
import org.yelong.commons.util.map.ConvenientMap;
import org.yelong.core.jdbc.sql.condition.ConditionConnectWay;
import org.yelong.core.jdbc.sql.condition.ConditionalOperator;
import org.yelong.core.jdbc.sql.condition.support.Condition;
import org.yelong.core.model.collector.ModelCollectors;
import org.yelong.core.model.service.SqlModelService;

import test.org.yelong.core.model.User;
import test.org.yelong.core.model.service.SqlModelServiceTest;

public class ModifyModelCollectorsTest {

	public static final SqlModelService modelService = SqlModelServiceTest.modelService;

	// ==================================================modify-model==================================================

	@Test
	public void modifyModelByOnlyPrimaryKeyEQ() {
		User user = new User();
		user.setCreateTime(Dates.nowDate());
		user.setPassword("123456");
		user.setId("123456");
		Integer collect = modelService.collect(ModelCollectors.modifyModelByOnlyPrimaryKeyEQ(user));
		System.out.println(collect);
		System.out.println(user.getId());
	}

	@Test
	public void modifyModelByOnlyPrimaryKeyEQ_2() {
		User user = new User();
		user.setCreateTime(Dates.nowDate());
		user.setPassword("123456");
		Integer collect = modelService.collect(ModelCollectors.modifyModelByOnlyPrimaryKeyEQ(user, "123"));
		System.out.println(collect);
		System.out.println(user.getId());
	}

	@Test
	public void modifyModelByOnlyPrimaryKeyContains() {
		User user = new User();
		user.setCreateTime(Dates.nowDate());
		user.setPassword("123456");
		Integer collect = modelService
				.collect(ModelCollectors.modifyModelByOnlyPrimaryKeyContains(user, ArrayUtils.toArray("123", "456")));
		System.out.println(collect);
	}

	@Test
	public void modifyModelBySingleColumnEQ() {
		User user = new User();
		user.setCreateTime(Dates.nowDate());
		user.setPassword("123456");
		Integer collect = modelService.collect(ModelCollectors.modifyModelBySingleColumnEQ(user, "state", "01"));
		System.out.println(collect);
	}

	@Test
	public void modifyModelBySingleColumnContains() {
		User user = new User();
		user.setCreateTime(Dates.nowDate());
		user.setPassword("123456");
		Integer collect = modelService.collect(
				ModelCollectors.modifyModelBySingleColumnContains(user, "state", ArrayUtils.toArray("01", "02")));
		System.out.println(collect);
	}

	@Test
	public void modifyModelByCondition() {
		User user = new User();
		user.setCreateTime(Dates.nowDate());
		user.setPassword("123456");
		modelService.collect(ModelCollectors.modifyModelByCondition(user,
				new Condition("state", "in", ArrayUtils.toArray("01", "02"))));
	}

	@Test
	public void modifyModelByConditions() {
		User user = new User();
		user.setCreateTime(Dates.nowDate());
		user.setPassword("123456");
		Condition condition = new Condition("id", "not in", ArrayUtils.toArray("123", "456"));
		Condition condition1 = new Condition("id", "is not null");
		modelService.collect(ModelCollectors.modifyModelByConditions(user, Arrays.asList(condition, condition1)));
	}

	// ==================================================modify-single-column==================================================

	@Test
	public void modifySingleColumnByOnlyPrimaryKeyEQ() {
		modelService.collect(
				ModelCollectors.modifySingleColumnByOnlyPrimaryKeyEQ(User.class, "userName", "123456", "123456"));
	}

	@Test
	public void modifySingleColumnByOnlyPrimaryKeyContains() {
		modelService.collect(ModelCollectors.modifySingleColumnByOnlyPrimaryKeyContains(User.class, "userName",
				"123456", ArrayUtils.toArray("123", "456")));
	}

	@Test
	public void modifySingleColumnBySingleColumnEQ() {
		modelService.collect(
				ModelCollectors.modifySingleColumnBySingleColumnEQ(User.class, "userName", "123456", "id", "123456"));
	}

	@Test
	public void modifySingleColumnBySingleColumnContains() {
		modelService.collect(ModelCollectors.modifySingleColumnBySingleColumnContains(User.class, "userName", "123456",
				"id", ArrayUtils.toArray("123", "456")));
	}

	@Test
	public void modifySingleColumnByCondition() {
		modelService.collect(ModelCollectors.modifySingleColumnByCondition(User.class, "userName", "123456",
				new Condition("id", "not in", ArrayUtils.toArray("123", "456"))));
	}

	@Test
	public void modifySingleColumnByConditions() {
		Condition condition = new Condition("id", "not in", ArrayUtils.toArray("123", "456"));
		Condition condition1 = new Condition("id", "is not null");
		modelService.collect(ModelCollectors.modifySingleColumnByConditions(User.class, "userName", "123456",
				Arrays.asList(condition, condition1)));
	}

	@Test
	public void modifySingleColumnBySqlModel() {
		User sqlModel = new User();
		sqlModel.setId("123");
		modelService.collect(ModelCollectors.modifySingleColumnBySqlModel(User.class, "userName", "123456", sqlModel));
	}

	// ==================================================modify-multi-column==================================================

	@Test
	public void modifyMultiColumnByOnlyPrimaryKeyEQ() {
		ConvenientMap<String, Object> modifyAttributes = new ConvenientMap<String, Object>().put_("userName", "123")
				.put_("password", "123");
		modelService.collect(ModelCollectors.modifyMultiColumnByOnlyPrimaryKeyEQ(User.class, modifyAttributes, "123"));
	}

	@Test
	public void modifyMultiColumnByOnlyPrimaryKeyContains() {
		ConvenientMap<String, Object> modifyAttributes = new ConvenientMap<String, Object>().put_("userName", "123")
				.put_("password", "123");
		modelService.collect(ModelCollectors.modifyMultiColumnByOnlyPrimaryKeyContains(User.class, modifyAttributes,
				ArrayUtils.toArray("123", "456")));
	}

	@Test
	public void modifyMultiColumnBySingleColumnEQ() {
		ConvenientMap<String, Object> modifyAttributes = new ConvenientMap<String, Object>().put_("userName", "123")
				.put_("password", "123");
		modelService.collect(
				ModelCollectors.modifyMultiColumnBySingleColumnEQ(User.class, modifyAttributes, "userName", "123456"));
	}

	@Test
	public void modifyMultiColumnBySingleColumnContains() {
		ConvenientMap<String, Object> modifyAttributes = new ConvenientMap<String, Object>().put_("userName", "123")
				.put_("password", "123");
		modelService.collect(ModelCollectors.modifyMultiColumnBySingleColumnContains(User.class, modifyAttributes,
				"userName", ArrayUtils.toArray("123", "456")));
	}

	@Test
	public void modifyMultiColumnByCondition() {
		ConvenientMap<String, Object> modifyAttributes = new ConvenientMap<String, Object>().put_("userName", "123")
				.put_("password", "123");
		Integer collect = modelService.collect(ModelCollectors.modifyMultiColumnByCondition(User.class,
				modifyAttributes, new Condition("userName", ConditionalOperator.NOT_IN, Arrays.asList("123", "456"))));
		System.out.println(collect);
	}

	@Test
	public void modifyMultiColumnByConditions() {
		ConvenientMap<String, Object> modifyAttributes = new ConvenientMap<String, Object>().put_("userName", "123")
				.put_("password", "123");
		Condition condition = new Condition("userName", ConditionalOperator.NOT_LIKE, "peng%");
		Condition condition1 = new Condition("state", ConditionalOperator.EQUAL, "1")
				.setConnectWay(ConditionConnectWay.OR);
		Integer collect = modelService.collect(ModelCollectors.modifyMultiColumnByConditions(User.class,
				modifyAttributes, Arrays.asList(condition, condition1)));
		System.out.println(collect);
	}

	@Test
	public void modifyMultiColumnBySqlModel() {
		ConvenientMap<String, Object> modifyAttributes = new ConvenientMap<String, Object>().put_("userName", "123")
				.put_("password", "123");
		User sqlModel = new User();
		sqlModel.setName("123");
		Integer collect = modelService
				.collect(ModelCollectors.modifyMultiColumnBySqlModel(User.class, modifyAttributes, sqlModel));
		System.out.println(collect);
	}

}
