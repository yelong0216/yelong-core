/**
 * 
 */
package test.org.yelong.core.model;

import java.util.Date;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.annotation.PrimaryKey;
import org.yelong.core.model.sql.SqlModel;

public class BaseModel<M extends Modelable> extends SqlModel<M> implements Modelable{
	
	private static final long serialVersionUID = -133016938315975806L;
	
	@SuppressWarnings("unchecked")
	@Override
	public M getModel() {
		return (M) this;
	}
	
	@Override
	public Class<? extends Modelable> getModelClass() {
		return getClass();
	}
	
	@PrimaryKey
	private String id;
	
	private String creator;
	
	private Date createTime;
	
	private String updator;
	
	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
