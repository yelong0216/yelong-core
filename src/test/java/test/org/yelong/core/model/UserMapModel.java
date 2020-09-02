package test.org.yelong.core.model;

import org.yelong.commons.util.map.CaseInsensitiveMapUtils.KeyStoreMode;
import org.yelong.core.model.annotation.FACWrapperAppoint;
import org.yelong.core.model.annotation.FACWrapperAppointMap;
import org.yelong.core.model.annotation.FACWrapperAppoints;
import org.yelong.core.model.annotation.Table;
import org.yelong.core.model.annotation.Transients;
import org.yelong.core.model.map.MapModel;

import test.org.yelong.core.model.annotation.UserMapModelUsernameFieldAndColumn;

@Table("CO_USER")
@Transients("username")
@FACWrapperAppoints(@FACWrapperAppointMap(fieldName = "username", appoint = @FACWrapperAppoint(UserMapModelUsernameFieldAndColumn.class)))
public class UserMapModel extends MapModel {

	private static final long serialVersionUID = 6612297532419876517L;

	public UserMapModel() {
		super(KeyStoreMode.LOWER);
	}

}
