/**
 * 
 */
package org.yelong.core.jdbc.sql.defaults;

import static org.yelong.core.jdbc.sql.SpliceSqlUtils.spliceSqlFragment;

import java.util.ArrayList;
import java.util.List;

import org.yelong.core.jdbc.dialect.Dialect;
import org.yelong.core.jdbc.sql.exception.InvalidSortException;
import org.yelong.core.jdbc.sql.sort.AbstractSortSqlFragment;
import org.yelong.core.jdbc.sql.sort.SortDirection;
import org.yelong.core.jdbc.sql.sort.support.Sort;

/**
 * 默认排序实现<br/>
 * 默认添加排序验证器
 * 
 * @author PengFei
 */
public class DefaultSortSqlFragment extends AbstractSortSqlFragment {

	private static final String COMMA = ",";

	public DefaultSortSqlFragment(Dialect dialect) {
		super(dialect);
	}

	@Override
	protected void validSort(Sort sort) throws InvalidSortException {
		if (!SortDirection.test(sort.getDirection())) {
			throw new InvalidSortException("无效的排序方向：" + sort.getDirection());
		}
	}

	@Override
	protected String generatorSortFragment(List<Sort> sorts) {

		List<String> sortFragment = new ArrayList<String>();
		sorts.forEach(x -> {
			sortFragment.add(x.getColumn());
			sortFragment.add(x.getDirection());
			sortFragment.add(COMMA);
		});
		sortFragment.remove(sortFragment.lastIndexOf(COMMA));
		return spliceSqlFragment(sortFragment.toArray(new String[0]));
	}

}
