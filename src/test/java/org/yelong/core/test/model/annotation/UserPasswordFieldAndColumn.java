package org.yelong.core.test.model.annotation;

import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.wrapper.FieldAndColumnWrapper;

/**
 * 
 * @since
 */
public class UserPasswordFieldAndColumn extends FieldAndColumnWrapper {

	public UserPasswordFieldAndColumn(FieldAndColumn fieldAndColumn) {
		super(fieldAndColumn);
	}

	@Override
	public boolean isTransient() {
		return true;
	}

}
