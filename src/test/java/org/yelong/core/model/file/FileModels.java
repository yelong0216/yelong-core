/**
 * 
 */
package org.yelong.core.model.file;

import org.yelong.core.model.Models;

/**
 * @since 2.0.0
 */
public final class FileModels {

	public static final String FILEMODEL_DELETEFILE_PROPERTY_NAEM = FileModels.class.getName() + ".deleteFile";

	private FileModels() {
	}

	public static void setDeleteFile(Boolean deleteFile) {
		Models.setProperty(FILEMODEL_DELETEFILE_PROPERTY_NAEM, deleteFile);
	}

	public static Boolean isDeleteFile() {
		return Models.getProperty(FILEMODEL_DELETEFILE_PROPERTY_NAEM);
	}

}
