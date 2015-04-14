package hu.aronkatona.excel;

import hu.aronkatona.exceptions.NotSupportedTypeException;
import hu.aronkatona.hibernateModel.Team;
import hu.aronkatona.service.implementations.TeamServiceImpl;
import hu.aronkatona.utils.ExcelErrorMessages;
import hu.aronkatona.utils.ExcelUploadInformations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ExcelReader {
	
	private Logger logger = Logger.getLogger(ExcelReader.class);
	
	private final int HEADERROW = 1;
	private final String COLUMNNAME = "Oszlopnev: ";
	private final String MORECOLUMN = "Tobb oszlop van megadva mint kene, toltsd le a template excelt";
	private final String LESSCOLUMN = "Kevesebb oszlop van megadva mint kene, toltsd le a template excelt";
	private final String MISSINGCOLUMN = " oszlop hianyzik";
	private final String NAN = "Nem szam: ";
	private final String NOTFOUNDTEAM = "Nincs ilyen csapat: ";
	private XSSFWorkbook workbook;
	
	private Validator validator;
	private ValidatorFactory factory;
	
	public ExcelReader(){
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	public ExcelUploadInformations<Team> readTeams(MultipartFile file, TeamServiceImpl teamServiceImpl) throws NotSupportedTypeException {
		ExcelUploadInformations<Team> returnList = new ExcelUploadInformations<>();
		List<ExcelErrorMessages> errors = new ArrayList<>();
		List<Team> updateTeams = new ArrayList<>();
		List<String[]> rows = new ArrayList<>();   
		returnList.setExcelErrorMessages(errors);
		returnList.setUpdateObjects(updateTeams);
		
		//file,workbook
		File uploadedFile = convert(file);
		FileInputStream excel;
		try {
			excel = new FileInputStream(new File(uploadedFile.getAbsolutePath()));
			workbook = new XSSFWorkbook(excel);
		} catch (FileNotFoundException e) {
			logger.error("File not found", e);
			e.printStackTrace();
			return returnList;
		} catch (IOException e) {
			logger.error("IOException", e);
			e.printStackTrace();
			return returnList;
		}
				
		//EZ ITT SZENT!xD
		String[] headerNames = {"id","name","price","point","picture"};
		int[] headerNumbers = new int[headerNames.length];

		checkHeader(headerNames,headerNumbers,errors);
		if(!errors.isEmpty()){
			uploadedFile.delete();
			return returnList;
		}
		
		readRows(rows);
		
		deleteBonusRowsBug(rows);
		
		validateTeams(rows,headerNames,headerNumbers,errors,updateTeams,teamServiceImpl);
		
	    uploadedFile.delete();
		
		
		return returnList;
	}

	private void checkHeader(String[] headerNames, int[] headerNumbers,List<ExcelErrorMessages> errors) {
		XSSFSheet sheet = workbook.getSheetAt(0);
		Row firstRow = sheet.getRow(0);
		
		if(headerNames.length < firstRow.getLastCellNum()){
			ExcelErrorMessages excelErrorMessage = new ExcelErrorMessages(HEADERROW, COLUMNNAME ,  MORECOLUMN);
 			errors.add(excelErrorMessage);
 			return;
		}
		if(headerNames.length > firstRow.getLastCellNum()){
			ExcelErrorMessages excelErrorMessage = new ExcelErrorMessages(HEADERROW, COLUMNNAME , LESSCOLUMN);
 			errors.add(excelErrorMessage);
 			return;
		}
		
		String[] rowObject = new String[firstRow.getLastCellNum()];
		Iterator<Cell> cellIterator = firstRow.cellIterator();
		while (cellIterator.hasNext())
        {
            Cell cell = cellIterator.next();
            rowObject[cell.getColumnIndex()] = cell.getStringCellValue();
        }		
		
		for(int i = 0; i < headerNames.length; ++i){
			if(Arrays.asList(rowObject).indexOf(headerNames[i]) == -1){
				ExcelErrorMessages excelErrorMessage = new ExcelErrorMessages(HEADERROW, COLUMNNAME ,  headerNames[i] + MISSINGCOLUMN);
     			errors.add(excelErrorMessage);
			}
			else{
				headerNumbers[i] = Arrays.asList(rowObject).indexOf(headerNames[i]);
			}
		}
	}
	
	private void readRows(List<String[]> rows) {
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		int firstRowColumnNumbers = sheet.getRow(0).getLastCellNum();
		String[] rowObject;
		while (rowIterator.hasNext())
	    {
			Row row = rowIterator.next();
			if(row.getRowNum() != 0){
				rowObject = new String[firstRowColumnNumbers];
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext())
	            {
	        		Cell cell = cellIterator.next();
	        		switch (cell.getCellType())
	                {
	                    case Cell.CELL_TYPE_NUMERIC:
	                  	  	rowObject[cell.getColumnIndex()] = String.valueOf((long)cell.getNumericCellValue());
	                  	  	break;
	                    case Cell.CELL_TYPE_STRING:
	                    	rowObject[cell.getColumnIndex()] = cell.getStringCellValue();
	                        break;
	                }
	                
	                               
	            }
	        	rows.add(rowObject);
			}
			
	    }
	}
	
	private void deleteBonusRowsBug(List<String[]> rows) {
		int rowLength;
    	boolean rowIsNull;
    	List<String[]> deleteRows = new ArrayList<>();
        for(String[] row : rows){
        	rowLength = row.length;
        	rowIsNull = true;
        	for(int i = 0; i < rowLength; ++i){
        		if(row[i] != null){
        			rowIsNull = false;
        			break;
        		}
        	}
        	if(rowIsNull) deleteRows.add(row);
        }
        rows.removeAll(deleteRows);
	}
	
	private void validateTeams(List<String[]> rows,String[] headerNames, int[] headerNumbers,List<ExcelErrorMessages> errors, List<Team> updateTeams,TeamServiceImpl teamServiceImpl) {
		Team team;
        Set<ConstraintViolation<Team>> constraintViolations = null;
		
        for(int i = 0; i < rows.size(); ++i){
    		if(rows.get(i)[headerNumbers[0]] == null){
    			try{
    				team = new Team(rows.get(i)[headerNumbers[1]],Long.valueOf(rows.get(i)[headerNumbers[2]]),Integer.valueOf(rows.get(i)[headerNumbers[3]]),rows.get(i)[headerNumbers[4]]);    				
    			}
    			catch(NumberFormatException e){
    				//TODO:
    				ExcelErrorMessages excelErrorMessage = new ExcelErrorMessages((i+2), headerNames[1] ,NAN + rows.get(i)[headerNumbers[0]]);
       				errors.add(excelErrorMessage);
       				continue;
    			}
    		}
    		else{
    			Team teamTMP = teamServiceImpl.getTeamByIdExcel(Long.parseLong(rows.get(i)[headerNumbers[0]]));
    			if(teamTMP == null){
					ExcelErrorMessages excelErrorMessage = new ExcelErrorMessages((i+2), "id" ,NOTFOUNDTEAM + rows.get(i)[headerNumbers[0]]);
       				errors.add(excelErrorMessage);
       				continue;
				}
    			else{
    				try{
        				team = new Team(Long.parseLong(rows.get(i)[headerNumbers[0]]),rows.get(i)[headerNumbers[1]],Long.valueOf(rows.get(i)[headerNumbers[2]]),Integer.valueOf(rows.get(i)[headerNumbers[3]]),rows.get(i)[headerNumbers[4]]);    				
        			}
        			catch(NumberFormatException e){
        				//TODO:
        				ExcelErrorMessages excelErrorMessage = new ExcelErrorMessages((i+2), headerNames[1] ,NAN + rows.get(i)[headerNumbers[0]]);
           				errors.add(excelErrorMessage);
           				continue;
        			}
    			}
    			
    		}
    		
    		
    		 constraintViolations =  validator.validate( team );
    	        if(!constraintViolations.isEmpty()){
    		        	Iterator<ConstraintViolation<Team>> iterator = constraintViolations.iterator();
    			        while(iterator.hasNext()) {
    			        	ConstraintViolation<Team> error = iterator.next();
    			        	String field = error.getPropertyPath().toString();
    			            String errorMessage = error.getMessage();
    			            ExcelErrorMessages excelErrorMessage = new ExcelErrorMessages(i+2 , field, errorMessage);
    			            errors.add(excelErrorMessage);
    			        }
    		     }
    			 else{
    				 updateTeams.add(team);
    			 }
    		
        }
        
       
	}

	private File convert(MultipartFile file) throws NotSupportedTypeException
	{    
	    String parts[] = file.getOriginalFilename().split("\\.");
	    if( !parts[parts.length-1].equals("xlsx")) throw new NotSupportedTypeException();

		
		try{
			File convFile = new File(file.getOriginalFilename());
		    convFile.createNewFile(); 
		    FileOutputStream fos = new FileOutputStream(convFile); 
		    fos.write(file.getBytes());
		    fos.close(); 
		    return convFile;
		}
		catch(Exception e){
			logger.error("Excel iras feltoltes", e);
			e.printStackTrace();
			//TODO:
			return null;
		}
	    
	    
	}
}
