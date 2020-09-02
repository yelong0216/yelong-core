/**
 * 
 */
package test.org.yelong.core.model.validator;

import org.junit.jupiter.api.Test;
import org.yelong.commons.lang.annotation.AnnotationUtilsE;
import org.yelong.commons.support.Entry;
import org.yelong.core.model.annotation.Table;
import org.yelong.core.model.property.DefaultModelProperty;
import org.yelong.core.model.validator.DefaultModelValidator;
import org.yelong.core.model.validator.ModelValidator;

import test.org.yelong.core.model.User;
import test.org.yelong.core.model.UserDTO;
import test.org.yelong.core.model.manager.ModelManagerTest;


/**
 * @since
 */
public class ModelValidatorTest {

	public static final ModelValidator modelValidator = new DefaultModelValidator(ModelManagerTest.modelManager,
			DefaultModelProperty.INSTANCE);

	@Test
	public void validateModel() {
		User user = new User();
		user.setRealName("123465");
		modelValidator.validateModel(user);
	}
	
	public static void main(String[] args) {
//		ModelValidatorTest modelValidatorTest = new ModelValidatorTest();
//		modelValidatorTest.validateModel();
		
		Entry<Class<?>,Table> annotationEntry = AnnotationUtilsE.getAnnotationEntry(UserDTO.class, Table.class, true);
		System.out.println(annotationEntry.getKey());
		System.out.println(annotationEntry.getValue());
	}
	
}
