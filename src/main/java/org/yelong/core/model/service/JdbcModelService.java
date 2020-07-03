/**
 * 
 */
package org.yelong.core.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yelong.commons.util.map.MapBeanConverter;
import org.yelong.core.jdbc.BaseDataBaseOperation;
import org.yelong.core.jdbc.sql.BoundSql;
import org.yelong.core.jdbc.sql.executable.SelectSqlFragment;
import org.yelong.core.model.ModelConfiguration;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.exception.ModelException;

/**
 * @author PengFei
 */
public class JdbcModelService extends AbstractSqlModelService {

	private BaseDataBaseOperation db;

	private MapBeanConverter mapBeanConverter;

	public JdbcModelService(ModelConfiguration modelConfiguration, BaseDataBaseOperation db) {
		this(modelConfiguration, db, new MapBeanConverter());
	}

	public JdbcModelService(ModelConfiguration modelConfiguration, BaseDataBaseOperation db,
			MapBeanConverter mapBeanConverter) {
		super(modelConfiguration);
		this.db = db;
		this.mapBeanConverter = mapBeanConverter;
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
				M model = mapBeanConverter.mapConvertBean(map, modelClass);
				modelList.add(model);
			} catch (Exception e) {
				throw new ModelException("map转换为bean异常", e);
			}
		}
		return modelList;
	}

	public MapBeanConverter getMapBeanConverter() {
		return mapBeanConverter;
	}

	public void setMapBeanConverter(MapBeanConverter mapBeanConverter) {
		this.mapBeanConverter = mapBeanConverter;
	}

}
