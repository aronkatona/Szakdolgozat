package hu.aronkatona.utils;

public class ExcelErrorMessages {

	private int row;
	private int sheetIndex;
	private String field;
	private String errorMessage;
	
	public ExcelErrorMessages(int row, String field, String errorMessage) {
		this.row = row;
		this.field = field;
		this.errorMessage = errorMessage;
	}

	public ExcelErrorMessages(int row,int sheetIndex, String field, String errorMessage) {
		this.row = row;
		this.sheetIndex = sheetIndex;
		this.field = field;
		this.errorMessage = errorMessage;
	}
	
	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getSheetIndex() {
		return sheetIndex;
	}

	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}
	
	
}
