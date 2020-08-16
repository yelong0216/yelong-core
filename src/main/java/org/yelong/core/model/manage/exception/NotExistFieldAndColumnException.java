/**
 * 
 */
package org.yelong.core.model.manage.exception;

import org.yelong.core.model.manage.ModelAndTable;

/**
 * 不存在字段列异常。该异常出现在对{@link ModelAndTable#getFieldAndColumn(String)}返回为
 * <code>null</code>时由使用者抛出的异常。
 * 
 * @since 2.0
 */
public class NotExistFieldAndColumnException extends ModelAndTableException {

	private static final long serialVersionUID = -1778370278385830638L;

	public NotExistFieldAndColumnException(ModelAndTable modelAndTable, String message, Throwable t) {
		super(modelAndTable, message, t);
	}

	public NotExistFieldAndColumnException(ModelAndTable modelAndTable, String message) {
		super(modelAndTable, message);
	}

	public NotExistFieldAndColumnException(ModelAndTable modelAndTable, Throwable t) {
		super(modelAndTable, t);
	}

	public NotExistFieldAndColumnException(ModelAndTable modelAndTable) {
		super(modelAndTable);
	}

}
