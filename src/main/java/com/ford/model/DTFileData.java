package com.ford.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DTFileData")
public class DTFileData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer rowId;

	String fileName;

	String fileRowData;

	String filePath;

	public Integer getRowId() {
		return rowId;
	}

	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileRowData() {
		return fileRowData;
	}

	public void setFileRowData(String fileRowData) {
		this.fileRowData = fileRowData;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
