/**
 * 
 */
package org.yelong.core.model.resolve;

import org.yelong.core.annotation.Nullable;

/**
 * 查询列条件。
 * 根据条件判断列是否进行映射
 * 
 * @author PengFei
 * @date 2019年12月27日下午6:54:44
 */
public class SelectColumnCondition {
	
	private final String propertyName;
	
	private final String havingValue;
	
	private final boolean matchIfMissing;
	
	/**
	 * @param propertyName 属性名称
	 * @param havingValue 属性期望值 。 如果期望值为空，则属性默认不能等于 false
	 * @param matchIfMissing 属性不存在，是否应该映射
	 */
	public SelectColumnCondition(String propertyName,@Nullable String havingValue, boolean matchIfMissing) {
		this.propertyName = propertyName;
		this.havingValue = havingValue;
		this.matchIfMissing = matchIfMissing;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getHavingValue() {
		return havingValue;
	}

	public boolean isMatchIfMissing() {
		return matchIfMissing;
	}
	
}
