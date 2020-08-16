package org.yelong.core.model.file;

import org.yelong.core.model.Modelable;

public interface FileModelable extends Modelable {

	String getFileName();

	void setFileName(String fileName);

	String getNewFileName();

	void setNewFileName(String newFileName);

	String getFileType();

	void setFileType(String fileType);

	Long getFileSize();

	void setFileSize(Long fileSize);

	String getFilePath();

	void setFilePath(String filePath);

}
