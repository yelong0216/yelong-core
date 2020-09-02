package test.org.yelong.core.model.collector;

import org.junit.jupiter.api.Test;
import org.yelong.core.model.collector.ModelCollectors;
import org.yelong.core.model.service.SqlModelService;

import test.org.yelong.core.model.User;
import test.org.yelong.core.model.service.SqlModelServiceTest;

public class ExistModelCollectorsTest {
	
	public static final SqlModelService modelService = SqlModelServiceTest.modelService;
	// ==================================================exist==================================================

	@Test
	public void exist() {
		System.out.println(modelService.collect(ModelCollectors.exist(User.class)));
	}

}
