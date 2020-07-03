/**
 * 
 */
package org.yelong.core.model.map;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.yelong.core.cache.CacheEntity;
import org.yelong.core.cache.CacheManager;
import org.yelong.core.jdbc.sql.ddl.DataDefinitionLanguage;
import org.yelong.core.jdbc.sql.function.DatabaseFunction;

/**
 * 默认的缓存 MapModel 列获取策略实现
 * 
 * @author PengFei
 * @since 1.3.0
 */
public class DefaultCacheSupportMapModelFieldAndColumnGetStrategy extends DefaultMapModelFieldAndColumnGetStrategy
		implements CacheSupportMapModelFieldAndColumnGetStrategy {

	private final CacheManager cacheManager;

	public DefaultCacheSupportMapModelFieldAndColumnGetStrategy(DataDefinitionLanguage dataDefinitionLanguage,
			DatabaseFunction dataBaseFunction, CacheManager cacheManager) {
		super(dataDefinitionLanguage, dataBaseFunction);
		this.cacheManager = Objects.requireNonNull(cacheManager);
	}

	@Override
	public synchronized List<MapModelFieldAndColumn> get(MapModelAndTable mapModelAndTable) {
		String cacheKey = getCacheKey(mapModelAndTable);
		CacheEntity<MapModelAndFieldAndColumns> cacheEntity = cacheManager.getCache(cacheKey);
		MapModelAndFieldAndColumns mapModelAndFieldAndColumns = cacheEntity.get();
		if (null == mapModelAndFieldAndColumns) {
			List<MapModelFieldAndColumn> mapModelFieldAndColumns = super.get(mapModelAndTable);
			mapModelAndFieldAndColumns = new MapModelAndFieldAndColumns(mapModelAndTable, mapModelFieldAndColumns);
			cacheManager.putCache(cacheKey, mapModelAndFieldAndColumns);
		}
		return mapModelAndFieldAndColumns.getMapModelFieldAndColumns();
	}

	@Override
	public synchronized List<MapModelFieldAndColumn> removeCache(MapModelAndTable mapModelAndTable) {
		String cacheKey = getCacheKey(mapModelAndTable);
		CacheEntity<MapModelAndFieldAndColumns> cacheEntity = cacheManager.remove(cacheKey);
		MapModelAndFieldAndColumns mapModelAndFieldAndColumns = cacheEntity.get();
		return mapModelAndFieldAndColumns != null ? mapModelAndFieldAndColumns.getMapModelFieldAndColumns() : null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public synchronized Set<MapModelAndTable> getCacheMapModelAndTable() {
		Map<String, CacheEntity<MapModelAndFieldAndColumns>> caches = (Map) cacheManager.getCacheAll();
		Collection<CacheEntity<MapModelAndFieldAndColumns>> values = caches.values();
		return values.stream().map(CacheEntity::get).map(MapModelAndFieldAndColumns::getMapModelAndTable)
				.collect(Collectors.toSet());
	}

	@Override
	public synchronized void clearCache() {
		cacheManager.clear();
	}

	protected String getCacheKey(MapModelAndTable mapModelAndTable) {
		Class<?> modelClass = mapModelAndTable.getModelClass();
		return modelClass.getName().replace(".", "").toUpperCase();
	}

	private static class MapModelAndFieldAndColumns {

		private final MapModelAndTable mapModelAndTable;

		private final List<MapModelFieldAndColumn> mapModelFieldAndColumns;

		public MapModelAndFieldAndColumns(MapModelAndTable mapModelAndTable,
				List<MapModelFieldAndColumn> mapModelFieldAndColumns) {
			this.mapModelAndTable = mapModelAndTable;
			this.mapModelFieldAndColumns = mapModelFieldAndColumns;
		}

		public MapModelAndTable getMapModelAndTable() {
			return mapModelAndTable;
		}

		public List<MapModelFieldAndColumn> getMapModelFieldAndColumns() {
			return mapModelFieldAndColumns;
		}

	}

}
