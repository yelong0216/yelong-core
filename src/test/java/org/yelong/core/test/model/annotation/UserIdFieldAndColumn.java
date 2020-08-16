/**
 * 
 */
package org.yelong.core.test.model.annotation;

import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.wrapper.FieldAndColumnWrapper;

/**
 * @since
 */
public class UserIdFieldAndColumn extends FieldAndColumnWrapper{

	public UserIdFieldAndColumn(FieldAndColumn fieldAndColumn) {
		super(fieldAndColumn);
	}
	

	@Override
	public String getColumn() {
		return "id";
	}
	
}
