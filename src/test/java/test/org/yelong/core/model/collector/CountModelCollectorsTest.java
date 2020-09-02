package test.org.yelong.core.model.collector;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;
import org.yelong.core.model.collector.ModelCollectors;
import org.yelong.core.model.service.SqlModelService;

import test.org.yelong.core.model.User;
import test.org.yelong.core.model.service.SqlModelServiceTest;

public class CountModelCollectorsTest {

	public static final SqlModelService modelService = SqlModelServiceTest.modelService;

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

}
