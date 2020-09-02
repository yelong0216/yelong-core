/**
 * 
 */
package test.org.yelong.core.model.annotation;

import org.yelong.core.model.manage.SelectColumnCondition;
import org.yelong.core.model.map.MapModelFieldAndColumn;
import org.yelong.core.model.map.wrapper.MapModelFieldAndColumnWrapper;

/**
 * @since
 */
public class UserMapModelUsernameFieldAndColumn extends MapModelFieldAndColumnWrapper {

	public UserMapModelUsernameFieldAndColumn(MapModelFieldAndColumn fieldAndColumn) {
		super(fieldAndColumn);
	}

	@Override
	public boolean isTransient() {
		return false;
	}

	@Override
	public SelectColumnCondition getSelectColumnCondition() {
		return new SelectColumnCondition("userMapModel.username", "true", false);
	}

}
