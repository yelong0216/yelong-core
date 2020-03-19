/**
 * 
 */
package org.yelong.core.model.service;

import java.util.List;

import org.yelong.core.jdbc.BaseDataBaseOperation;
import org.yelong.core.jdbc.sql.executable.SelectSqlFragment;
import org.yelong.core.model.Model;
import org.yelong.core.model.ModelConfiguration;
import org.yelong.core.model.sql.ModelSqlFragmentFactory;

/**
 * @author 彭飞
 * @date 2019年10月25日上午9:50:14
 * @version 1.2
 */
public class JdbcModelService extends AbstractModelService{

	private BaseDataBaseOperation db;
	
	private ModelSqlFragmentFactory modelSqlFragmentFactory;
	
	public JdbcModelService(ModelConfiguration modelConfiguration,BaseDataBaseOperation db , ModelSqlFragmentFactory modelSqlFragmentFactory) {
		super(modelConfiguration);
		this.modelSqlFragmentFactory = modelSqlFragmentFactory;
	}

	@Override
	public BaseDataBaseOperation getBaseDataBaseOperation() {
		return db;
	}

	@Override
	public <M extends Model> List<M> execute(Class<M> modelClass, SelectSqlFragment selectSqlFragment) {
		throw new UnsupportedOperationException("不支持查询，请通过ORM框架进行查询");
	}
	
	@Override
	public ModelSqlFragmentFactory getModelSqlFragmentFactory() {
		return this.modelSqlFragmentFactory;
	}
	
}
