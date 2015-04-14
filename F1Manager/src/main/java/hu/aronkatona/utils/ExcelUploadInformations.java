package hu.aronkatona.utils;

import java.util.ArrayList;
import java.util.List;

public class ExcelUploadInformations<T> {

	private List<ExcelErrorMessages> excelErrorMessages = new ArrayList<>();

	private List<T> updateObjects = new ArrayList<>();

	public List<ExcelErrorMessages> getExcelErrorMessages() {
		return excelErrorMessages;
	}

	public void setExcelErrorMessages(List<ExcelErrorMessages> excelErrorMessages) {
		this.excelErrorMessages = excelErrorMessages;
	}

	public List<T> getUpdateObjects() {
		return updateObjects;
	}

	public void setUpdateObjects(List<T> updateObjects) {
		this.updateObjects = updateObjects;
	}

	
}
