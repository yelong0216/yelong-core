/**
 * 
 */
package test.org.yelong.core.model.manager;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.yelong.core.jdbc.BaseDataBaseOperation;
import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.dialect.impl.mysql.MySqlDialect;
import org.yelong.core.model.manage.DefaultModelManager;
import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.ModelAndTable;
import org.yelong.core.model.manage.ModelManager;
import org.yelong.core.model.map.MapModelAndTable;
import org.yelong.core.model.map.MapModelFieldAndColumnGetStrategy;
import org.yelong.core.model.map.annotation.AnnotationMapModelResolver;
import org.yelong.core.model.map.support.DefaultMapModelFieldAndColumnGetStrategy;
import org.yelong.core.model.pojo.POJOModelResolver;
import org.yelong.core.model.pojo.annotation.AnnotationFieldResolver;
import org.yelong.core.model.pojo.annotation.AnnotationPOJOModelResolver;
import org.yelong.core.model.resolve.DefaultModelResolverManager;
import org.yelong.core.model.resolve.ModelResolverManager;

import test.org.yelong.core.jdbc.BaseDataBaseOperationTest;
import test.org.yelong.core.model.User;
import test.org.yelong.core.model.UserMapModel;

/**
 * @since
 */
public class ModelManagerTest {

	public static final ModelManager modelManager;

	static {
		ModelResolverManager modelResolverManager = new DefaultModelResolverManager();
		modelManager = new DefaultModelManager(modelResolverManager);

		POJOModelResolver pojoModelResolver = new AnnotationPOJOModelResolver();
		pojoModelResolver.registerFieldResovler(new AnnotationFieldResolver());
		modelResolverManager.registerModelResolver(pojoModelResolver);

		Dialect dialect = new MySqlDialect();
		BaseDataBaseOperation db = BaseDataBaseOperationTest.mysqlDb;

		MapModelFieldAndColumnGetStrategy mapModelFieldAndColumnGetStrategy = new DefaultMapModelFieldAndColumnGetStrategy(
				dialect.createDataDefinitionLanguage(db), dialect.createDatabaseFunction(db));

		modelResolverManager.registerModelResolver(new AnnotationMapModelResolver(mapModelFieldAndColumnGetStrategy));
	}
	
	@Test
	public void getModelAndTable() {
		ModelAndTable modelAndTable = modelManager.getModelAndTable(User.class);
		List<FieldAndColumn> fieldAndColumns = modelAndTable.getFieldAndColumns();
		System.out.println(fieldAndColumns);
		FieldAndColumn onlyPrimaryKey = modelAndTable.getOnlyPrimaryKey();
		System.out.println(onlyPrimaryKey.getColumn());
	}
	
	@Test
	public void getMapModelAndTable() {
		ModelAndTable modelAndTable = modelManager.getModelAndTable(UserMapModel.class);
		System.out.println(modelAndTable instanceof MapModelAndTable);
		List<FieldAndColumn> fieldAndColumns = modelAndTable.getFieldAndColumns();
		System.out.println(fieldAndColumns);
		FieldAndColumn onlyPrimaryKey = modelAndTable.getOnlyPrimaryKey();
		System.out.println(onlyPrimaryKey.getColumn());
		FieldAndColumn fieldAndColumn = modelAndTable.getFieldAndColumn("username");
		System.out.println(fieldAndColumn);
		System.out.println(modelAndTable.getFieldAndColumns());
	}

}
