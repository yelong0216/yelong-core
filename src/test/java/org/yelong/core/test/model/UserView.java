/**
 * 
 */
package org.yelong.core.test.model;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.annotation.View;

/**
 * @author YL
 *
 */
@View
public class UserView implements Modelable{

	private static final long serialVersionUID = -2370704857955561505L;
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
