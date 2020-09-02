/**
 * 
 */
package test.org.yelong.core.model.collector;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.yelong.core.model.ModelProperties;
import org.yelong.core.model.collector.ModelCollectors;
import org.yelong.core.model.service.SqlModelService;

import test.org.yelong.core.model.User;
import test.org.yelong.core.model.service.SqlModelServiceTest;

/**
 * @author PengFei
 *
 */
public class SaveModelCollectorsTest {

	public static final SqlModelService modelService = SqlModelServiceTest.modelService;

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

}
