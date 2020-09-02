package test.org.yelong.core.model.collector;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;
import org.yelong.core.jdbc.sql.condition.ConditionalOperator;
import org.yelong.core.jdbc.sql.condition.support.Condition;
import org.yelong.core.jdbc.sql.sort.support.Sort;
import org.yelong.core.model.collector.ModelCollectors;
import org.yelong.core.model.service.SqlModelService;

import test.org.yelong.core.model.User;
import test.org.yelong.core.model.service.SqlModelServiceTest;

public class FindModelCollectorsTest {

	public static final SqlModelService modelService = SqlModelServiceTest.modelService;

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

	// ==================================================find-single-column==================================================

	@Test
	public void findSingleColumnAll() {
		List<String> usernames = modelService.collect(ModelCollectors.findSingleColumnAll(User.class, "username"));
		usernames.forEach(System.out::println);
		System.out.println(usernames);
	}

	@Test
	public void findSingleColumnByOnlyPrimaryKeyContains() {
		List<String> usernames = modelService.collect(ModelCollectors
				.findSingleColumnByOnlyPrimaryKeyContains(User.class, "username", ArrayUtils.toArray("1")));
		System.out.println(usernames);
	}

	@Test
	public void findSingleColumnBySingleColumnEQ() {
		List<String> usernames = modelService.collect(
				ModelCollectors.findSingleColumnBySingleColumnEQ(User.class, "username", "username", "pengfei"));
		System.out.println(usernames);
	}

	@Test
	public void findSingleColumnBySingleColumnContains() {
		List<String> usernames = modelService.collect(ModelCollectors.findSingleColumnBySingleColumnContains(
				User.class, "username", "username", ArrayUtils.toArray("pengfei", "yelong")));
		System.out.println(usernames);
	}

}
