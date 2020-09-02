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
public class CopyModelCollectorsTest {

	public static final SqlModelService modelService = SqlModelServiceTest.modelService;

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

}
