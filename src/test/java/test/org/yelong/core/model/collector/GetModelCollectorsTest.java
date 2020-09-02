/**
 * 
 */
package test.org.yelong.core.model.collector;

import org.junit.jupiter.api.Test;
import org.yelong.core.model.collector.ModelCollectors;
import org.yelong.core.model.service.SqlModelService;

import test.org.yelong.core.model.User;
import test.org.yelong.core.model.service.SqlModelServiceTest;

/**
 * @author PengFei
 *
 */
public class GetModelCollectorsTest {

	public static final SqlModelService modelService = SqlModelServiceTest.modelService;

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

}
