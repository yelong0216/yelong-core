/**
 * 
 */
package org.yelong.core.jdbc.sql.sort;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.yelong.core.jdbc.sql.AbstractSqlFragment;
import org.yelong.core.jdbc.sql.exception.InvalidSortException;

/**
 * @author 彭飞
 * @date 2019年8月9日下午5:13:30
 * @version 1.0
 */
public abstract class AbstractSortSqlFragment extends AbstractSqlFragment implements SortSqlFragment{

	private boolean orderBy = true;
	
	private final List<SortFragmentWrapper> sortFragmentList = new ArrayList<>();
	
	
	@Override
	public void addSort(String fieldName, String direction) {
		validSort(fieldName, direction);
		sortFragmentList.add(new SortFragmentWrapper(fieldName,direction));
	}
	
	/**
	 * 验证排序是否符合规范
	 * @author 彭飞
	 * @date 2019年8月23日下午2:45:55
	 * @version 1.2
	 * @param fieldName 排序字段名称
	 * @param direction 排序方向
	 * @throws InvalidSortException 如果这不是一个符合规范的异常
	 */
	protected abstract void validSort(String fieldName , String direction) throws InvalidSortException;
	
	protected abstract String generatorSortFragment(List<SortFragmentWrapper> sortFragmentList);
	
	@Override
	public boolean isOrderBy() {
		return orderBy;
	}
	
	@Override
	public void setOrderBy(boolean orderBy) {
		this.orderBy = orderBy;
	}

	protected String orderBy(String sort) {
		if(orderBy) {
			return " order by "+sort;
		} else {
			return sort;
		}
	}
	
	@Override
	public Object[] getParams() {
		return  ArrayUtils.EMPTY_OBJECT_ARRAY;
	}
	
	@Override
	public String getSqlFragment() {
		if( isEmpty() ) {
			throw new UnsupportedOperationException("不存在排序!");
		}
		return orderBy(generatorSortFragment(sortFragmentList));
	}
	
	@Override
	public boolean isEmpty() {
		return sortFragmentList.isEmpty();
	}
	
	protected class SortFragmentWrapper{
		
		private final String fieldName;
		
		private final String direction;
		
		public SortFragmentWrapper(String fieldName, String direction) {
			this.fieldName = fieldName;
			this.direction = direction;
		}

		public String getFieldName() {
			return fieldName;
		}

		public String getDirection() {
			return direction;
		}
		
	}
	
	
}
