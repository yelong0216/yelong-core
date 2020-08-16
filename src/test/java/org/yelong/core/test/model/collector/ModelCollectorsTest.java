/**
 * 
 */
package org.yelong.core.test.model.collector;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;
import org.yelong.commons.util.Dates;
import org.yelong.commons.util.map.ConvenientMap;
import org.yelong.core.jdbc.sql.condition.ConditionConnectWay;
import org.yelong.core.jdbc.sql.condition.ConditionalOperator;
import org.yelong.core.jdbc.sql.condition.support.Condition;
import org.yelong.core.jdbc.sql.sort.support.Sort;
import org.yelong.core.model.ModelProperties;
import org.yelong.core.model.collector.ModelCollectors;
import org.yelong.core.model.service.SqlModelService;
import org.yelong.core.test.model.User;
import org.yelong.core.test.model.User2;
import org.yelong.core.test.model.service.SqlModelServiceTest;

/**
 * @author PengFei
 *
 */
public class ModelCollectorsTest {

	public static final SqlModelService modelService = SqlModelServiceTest.modelService;

	public static void main(String[] args) {
//		modelService.collect(ModelCollectors.copyListBySingleColumnEQ(User.class, "age", 18));
	}

	// ==================================================save==================================================

	@Test
	public void saveSingle() {
//		modelService.collect(ModelCollectors.saveSingle(null));
		User user = new User();
		user.setName("彭飞");
		modelService.collect(ModelCollectors.saveSingle(user).beforeSave((x, y) -> {
			y.setId("123456");
		}));
	}

	@Test
	public void saveList() {
//		modelService.collect(ModelCollectors.saveList(null, null));
//		modelService.collect(ModelCollectors.saveList(User.class, null));
		User user = new User();
		user.setId("123");
		User user1 = new User();
		user1.setId("456");
		ModelProperties modelProperties = modelService.getModelConfiguration().getModelProperties();
		modelProperties.setSaveValidateModel(false);
		modelService.collect(ModelCollectors.saveList(User.class, Arrays.asList(user, user1)).preProcess((x, ms) -> {
			if (ms.isEmpty()) {
				throw new RuntimeException("不存在保存的模型");
			}
		}).saveModel((x, m) -> {
			try {
				modelService.saveSelective(m);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}));

	}

	// ==================================================copy==================================================

	@Test
	public void copySingle() {
		User user = new User();
		user.setPassword("12456");
		modelService.collect(ModelCollectors.copySingle(user));
	}

	@Test
	public void copySingleByOnlyPrimaryKeyEQ() {
		modelService.collect(ModelCollectors.copySingleByOnlyPrimaryKeyEQ(User.class, "123456"));
	}

	@Test
	public void copySingleBySingleColumnEQ() {
		modelService.collect(ModelCollectors.copySingleBySingleColumnEQ(User.class, "id", "123456"));
	}

	// ==================================================remove==================================================
	@Test
	public void removeAll() {
		modelService.collect(ModelCollectors.removeAll(User2.class));
	}

	@Test
	public void removeByOnlyPrimaryKeyEQ() {
		modelService.collect(ModelCollectors.removeByOnlyPrimaryKeyEQ(User.class, "123456789"));
	}

	@Test
	public void removeByOnlyPrimaryKeyContains() {
//		modelService.collect(ModelCollectors.removeByOnlyPrimaryKeyContains(User2.class, null));
//		modelService.collect(ModelCollectors.removeByOnlyPrimaryKeyContains(User2.class, ArrayUtils.toArray("123","456")));
	}

	@Test
	public void removeByCondition() {
		modelService.collect(ModelCollectors.removeByCondition(User2.class, null));
	}
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
	// ==================================================getSingleValue==================================================

	@Test
	public void getSingleValueByOnlyPrimaryKeyEQ() {
		Object value = modelService
				.collect(ModelCollectors.getSingleValueByOnlyPrimaryKeyEQ(User.class, "username", "123456"));
		System.out.println(value);
	}

	@Test
	public void getSingleValueBySingleColumnEQ() {
		Object value = modelService
				.collect(ModelCollectors.getSingleValueBySingleColumnEQ(User.class, "username", "username", "123456"));
		System.out.println(value);
	}

	// ==================================================getModel==================================================

	@Test
	public void getModelByOnlyPrimaryKeyEQ() {
		User user = modelService.collect(ModelCollectors.getModelByOnlyPrimaryKeyEQ(User.class, "123456"));
		System.out.println(user);
	}

	@Test
	public void getModelBySingleColumnEQ() {
		User user = modelService.collect(ModelCollectors.getModelBySingleColumnEQ(User.class, "username", "123456"));
		System.out.println(user);
	}

	// ==================================================find==================================================

	@Test
	public void findAll() {
		List<User> users = modelService.collect(ModelCollectors.findAll(User.class));
		System.out.println(users.size());
	}

	@Test
	public void findByOnlyPrimaryKeyContains() {
		List<User> users = modelService
				.collect(ModelCollectors.findByOnlyPrimaryKeyContains(User.class, ArrayUtils.toArray("AAA", "BBB")));
		users.forEach(System.out::println);
	}

	@Test
	public void findByOnlyPrimaryKeyContainsSort() {
		List<User> users = modelService.collect(ModelCollectors.findByOnlyPrimaryKeyContains(User.class,
				ArrayUtils.toArray("AAA", "BBB"), new Sort("age", "desc")));
		users.forEach(System.out::println);
	}

	@Test
	public void findByOnlyPrimaryKeyContainsSorts() {
		Sort ageSort = new Sort("age", "desc");
		Sort createTimeSort = new Sort("createTime", "desc");
		List<User> users = modelService.collect(ModelCollectors.findByOnlyPrimaryKeyContains(User.class,
				ArrayUtils.toArray("AAA", "BBB"), Arrays.asList(ageSort, createTimeSort)));
		users.forEach(System.out::println);
	}

	@Test
	public void findBySingleColumnEQ() {
		List<User> users = modelService
				.collect(ModelCollectors.findBySingleColumnEQ(User.class, "userName", "pengFei"));
		users.forEach(System.out::println);
	}

	@Test
	public void findBySingleColumnEQSort() {
		List<User> users = modelService.collect(
				ModelCollectors.findBySingleColumnEQ(User.class, "userName", "pengFei", new Sort("age", "desc")));
		users.forEach(System.out::println);
	}

	@Test
	public void findBySingleColumnEQSorts() {
		Sort ageSort = new Sort("age", "desc");
		Sort createTimeSort = new Sort("createTime", "desc");
		List<User> users = modelService.collect(ModelCollectors.findBySingleColumnEQ(User.class, "userName", "pengFei",
				Arrays.asList(ageSort, createTimeSort)));
		users.forEach(System.out::println);
	}

	@Test
	public void findBySingleColumnContains() {
		List<User> users = modelService.collect(ModelCollectors.findBySingleColumnContains(User.class, "userName",
				ArrayUtils.toArray("PengFei", "YeLong")));
		users.forEach(System.out::println);
	}

	@Test
	public void findBySingleColumnContainsSort() {
		List<User> users = modelService.collect(ModelCollectors.findBySingleColumnContains(User.class, "userName",
				ArrayUtils.toArray("PengFei", "YeLong"), new Sort("age", "desc")));
		users.forEach(System.out::println);
	}

	@Test
	public void findBySingleColumnContainsSorts() {
		Sort ageSort = new Sort("age", "desc");
		Sort createTimeSort = new Sort("createTime", "desc");
		List<User> users = modelService.collect(ModelCollectors.findBySingleColumnContains(User.class, "userName",
				ArrayUtils.toArray("PengFei", "YeLong"), Arrays.asList(ageSort, createTimeSort)));
		users.forEach(System.out::println);
	}

	@Test
	public void findByCondition() {
		List<User> users = modelService
				.collect(ModelCollectors.findByCondition(User.class, new Condition("userName", "=", "PengFei")));
		users.forEach(System.out::println);
	}

	@Test
	public void findByConditionSort() {
		Condition condition = new Condition("userName", ConditionalOperator.NOT_LIKE, "peng%");
		Sort sort = new Sort("createTime", "desc");
		List<User> users = modelService.collect(ModelCollectors.findByConditionSort(User.class, condition, sort));
		users.forEach(System.out::println);
	}

	@Test
	public void findByConditionsSorts() {
		Condition condition = new Condition("userName", ConditionalOperator.NOT_LIKE, "peng%");
		Condition condition1 = new Condition("age", ConditionalOperator.GREATER_THAN, 18);
		Sort sort = new Sort("createTime", "desc");
		Sort sort1 = new Sort("updateTime", "asc");
		List<User> users = modelService.collect(ModelCollectors.findByConditionsSorts(User.class,
				Arrays.asList(condition, condition1), Arrays.asList(sort, sort1)));
		users.forEach(System.out::println);
	}

	// ==================================================count==================================================

	@Test
	public void countAll() {
		System.out.println(modelService.collect(ModelCollectors.countAll(User.class)));
	}

	@Test
	public void countByOnlyPrimaryKeyEQ() {
		System.out.println(modelService.collect(ModelCollectors.countByOnlyPrimaryKeyEQ(User.class, "123456")));
	}

	@Test
	public void countByOnlyPrimaryKeyContains() {
		System.out.println(modelService
				.collect(ModelCollectors.countByOnlyPrimaryKeyContains(User.class, ArrayUtils.toArray("123", "456"))));
	}

	@Test
	public void countBySingleColumnEQ() {
		System.out
				.println(modelService.collect(ModelCollectors.countBySingleColumnEQ(User.class, "username", "123456")));
	}
	
	// ==================================================exist==================================================
	
	@Test
	public void exist() {
		System.out.println(modelService.collect(ModelCollectors.exist(User.class)));
	}

}
