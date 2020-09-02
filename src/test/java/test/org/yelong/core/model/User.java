/**
 * 
 */
package test.org.yelong.core.model;

import org.yelong.core.model.annotation.Column;
import org.yelong.core.model.annotation.FACWrapperAppoint;
import org.yelong.core.model.annotation.FACWrapperAppointMap;
import org.yelong.core.model.annotation.FACWrapperAppoints;
import org.yelong.core.model.annotation.Table;
import org.yelong.core.model.annotation.Transient;
import org.yelong.core.model.annotation.Transients;

import test.org.yelong.core.model.annotation.UserIdFieldAndColumn;
import test.org.yelong.core.model.annotation.UserPasswordFieldAndColumn;

@Table("CO_USER")
@Transients("creatorRealName")
@FACWrapperAppoints({
		@FACWrapperAppointMap(fieldName = "id", appoint = @FACWrapperAppoint(UserIdFieldAndColumn.class)) })
public class User extends BaseModel<User> {

	private static final long serialVersionUID = -4675897263388346958L;

	@Transient
	private String name;
	
	private String username;

	@FACWrapperAppoint(UserPasswordFieldAndColumn.class)
	private String password;

	@Column(maxLength = 2)
	private String realName;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
