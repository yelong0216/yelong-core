/**
 * 
 */
package test.org.yelong.core.model;

import org.yelong.core.model.annotation.Transients;

/**
 * @since
 */
@Transients({"creatorRealName","username"})
public class UserDTO extends User{

	private static final long serialVersionUID = -7781203516555749848L;
	
	private String creatorRealName;

	public String getCreatorRealName() {
		return creatorRealName;
	}

	public void setCreatorRealName(String creatorRealName) {
		this.creatorRealName = creatorRealName;
	}
	
}
