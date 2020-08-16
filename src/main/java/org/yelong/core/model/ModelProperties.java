/**
 * 
 */
package org.yelong.core.model;

import org.yelong.core.model.service.ModelService;
import org.yelong.core.model.sql.SelectSqlColumnMode;
import org.yelong.core.model.validator.ModelValidator;

/**
 * 模型的配置
 * 
 * @since 2.0
 */
public class ModelProperties {

	/**
	 * 保存时是否验证模型
	 * 
	 * @see ModelService#save(Modelable) 等保存方法
	 * @see ModelValidator
	 */
	private boolean saveValidateModel = true;

	/**
	 * 修改时是否验证模型
	 * 
	 * @see ModelService#modifyById(Modelable) 等修改方法
	 * @see ModelValidator
	 */
	private boolean modifyValidateModel = true;

	/**
	 * 查询SQL列模式
	 */
	private SelectSqlColumnMode selectSqlColumnMode = SelectSqlColumnMode.STAR;
	
	public boolean isSaveValidateModel() {
		return saveValidateModel;
	}

	public void setSaveValidateModel(boolean saveValidateModel) {
		this.saveValidateModel = saveValidateModel;
	}

	public boolean isModifyValidateModel() {
		return modifyValidateModel;
	}

	public void setModifyValidateModel(boolean modifyValidateModel) {
		this.modifyValidateModel = modifyValidateModel;
	}

	public SelectSqlColumnMode getSelectSqlColumnMode() {
		return selectSqlColumnMode;
	}

	public void setSelectSqlColumnMode(SelectSqlColumnMode selectSqlColumnMode) {
		this.selectSqlColumnMode = selectSqlColumnMode;
	}

}
