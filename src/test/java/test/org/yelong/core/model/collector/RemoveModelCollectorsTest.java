package test.org.yelong.core.model.collector;

import org.junit.jupiter.api.Test;
import org.yelong.core.model.collector.ModelCollectors;
import org.yelong.core.model.service.SqlModelService;

import test.org.yelong.core.model.User;
import test.org.yelong.core.model.User2;
import test.org.yelong.core.model.service.SqlModelServiceTest;

public class RemoveModelCollectorsTest {

	public static final SqlModelService modelService = SqlModelServiceTest.modelService;

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

}
