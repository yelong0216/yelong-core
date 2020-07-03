/**
 * 
 */
package org.yelong.core.model.exception;

/**
 * 已经存在的字段映射列异常
 * 
 * @author PengFei
 */
public class HaveExistFieldMappingColumnException extends FieldAndColumnException {

	private static final long serialVersionUID = -5034435636005465884L;

	public HaveExistFieldMappingColumnException() {

	}

	public HaveExistFieldMappingColumnException(String message) {
		super(message);
	}

}
