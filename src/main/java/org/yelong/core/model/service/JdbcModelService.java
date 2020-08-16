/**
 * 
 */
package org.yelong.core.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yelong.core.jdbc.BaseDataBaseOperation;
import org.yelong.core.jdbc.sql.BoundSql;
import org.yelong.core.jdbc.sql.executable.SelectSqlFragment;
import org.yelong.core.model.ModelConfiguration;
import org.yelong.core.model.ModelException;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.convertor.DefaultModelAndMapConvertor;
import org.yelong.core.model.convertor.ModelAndMapConvertor;

/**
 * JDBC模型业务实现类。通过 {@link ModelAndMapConvertor}将Map转换为模型
 * 
 * @since 1.0
 * @see ModelAndMapConvertor
 */
public class JdbcModelService extends AbstractSqlModelService {

	private BaseDataBaseOperation db;

	private ModelAndMapConvertor modelAndMapConvertor;

	public JdbcModelService(ModelConfiguration modelConfiguration, BaseDataBaseOperation db) {
		this(modelConfiguration, db, DefaultModelAndMapConvertor.INSTANCE);
	}

	public JdbcModelService(ModelConfiguration modelConfiguration, BaseDataBaseOperation db,
			ModelAndMapConvertor modelAndMapConvertor) {
		super(modelConfiguration);
		this.db = db;
		this.modelAndMapConvertor = modelAndMapConvertor;
	}

	@Override
	public BaseDataBaseOperation getBaseDataBaseOperation() {
		return db;
	}

	@Override
	public <M extends Modelable> List<M> execute(Class<M> modelClass, SelectSqlFragment selectSqlFragment) {
		BoundSql boundSql = selectSqlFragment.getBoundSql();
		if (selectSqlFragment.isPage()) {
			boundSql = getModelConfiguration().getDialect().page(boundSql, selectSqlFragment.getPageNum(),
					selectSqlFragment.getPageSize());
		}
		List<Map<String, Object>> result = getBaseDataBaseOperation().select(boundSql.getSql(), boundSql.getParams());
		List<M> modelList = new ArrayList<>(result.size());
		for (Map<String, Object> map : result) {
			try {
				M model = modelAndMapConvertor.toModel(map, modelClass);
				modelList.add(model);
			} catch (Exception e) {
				throw new ModelException(modelClass, "map转换为model异常", e);
			}
		}
		return modelList;
	}

}
