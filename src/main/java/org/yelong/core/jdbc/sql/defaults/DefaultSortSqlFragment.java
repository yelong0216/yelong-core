/**
 * 
 */
package org.yelong.core.jdbc.sql.defaults;

import static org.yelong.core.jdbc.sql.SpliceSqlUtils.spliceSqlFragment;

import java.util.ArrayList;
import java.util.List;

import org.yelong.core.jdbc.sql.exception.InvalidSortException;
import org.yelong.core.jdbc.sql.sort.AbstractSortSqlFragment;

/**
 * 默认排序实现<br/>
 * 默认添加排序验证器
 * @author PengFei
 */
public class DefaultSortSqlFragment extends AbstractSortSqlFragment{
	
	private static final String COMMA = ",";
	
	@Override
	protected void validSort(String fieldName, String direction) throws InvalidSortException {
		if( !SortDirection.test(direction) ) {
			throw new InvalidSortException("无效的排序方向："+direction);
		}
	}
	
	@Override
	protected String generatorSortFragment(List<SortFragmentWrapper> sortFragmentList) {
		
		List<String> sortFragment = new ArrayList<String>();
		sortFragmentList.forEach(x->{
			sortFragment.add(x.getFieldName());
			sortFragment.add(x.getDirection());
			sortFragment.add(COMMA);
		});
		sortFragment.remove(sortFragment.lastIndexOf(COMMA));
		return spliceSqlFragment(sortFragment.toArray(new String[0]));
	}
	
	/**
	 * 排序方向
	 */
	public enum SortDirection{
		
		ASC("ASC"),
		
		DESC("DESC");
		
		private final String direction;
		
		private SortDirection(final String direction) {
			this.direction = direction;
		}
		public String getDirection() {
			return direction;
		}
		
		/**
		 * 根据排序方向获取排序方向对象<br/>
		 * @param direction 排序方向
		 * @return <tt>true</tt> 排序方向的对象
		 */
		public static final SortDirection valueOfByDirection(String direction) {
			for (SortDirection sortDirection : SortDirection.values()) {
				if( direction.toUpperCase().equals(sortDirection.getDirection()) ) {
					return sortDirection;
				}
			}
			return null;
		}
		
		/**
		 * 测试是否存在此排序方向
		 * @param direction 排序方向
		 * @return <tt>true</tt>如果存在此排序方向
		 */
		public static final boolean test(String direction) {
			if( null == valueOfByDirection(direction)) {
				return false;
			}
			return true;
		}
		
	}
	
}
