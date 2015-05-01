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
import java.util.Locale;
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
import org.springframework.context.MessageSource;
import org.springframework.web.multipart.MultipartFile;

public class ExcelReader {
	
	private Logger logger = Logger.getLogger(ExcelReader.class);
	
	private final int HEADERROW = 1;
	private final String EXCELCOLUMNNAME = "**message.excel.column_name**";
	private final String EXCELMORECOLUMN = "**message.excel.more_column**";
	private final String EXCELLESSCOLUMN = "**message.excel.less_column**";
	private final String EXCELMISSINGCOLUMN = "**message.excel.missing_column**";
	private final String EXCELNAN = "**message.excel.nan**";
	private final String EXCELNOTFOUNDTEAM = "**message.excel.not_found_team**";
	private final String EXCELACTIVECOLUMNERROR = "**message.excel.active_column_error**";
	private final String EXCELEXISTINGTEAMNAME = "**message.excel.existing_team_name**";
	private final String EXCELSAMETEAMNAME = "**message.excel.same_team_name**";
	
	private final String EXCELID = "**message.excel.id**";
	private final String EXCELNAME = "**message.excel.name**";
	private final String EXCELPRICE = "**message.excel.price**";
	private final String EXCELPOINT = "**message.excel.point**";
	private final String EXCELPICTURE = "**message.excel.picture**";
	private final String EXCELACTIVE = "**message.excel.active**";
	
	
	private XSSFWorkbook workbook;
	
	private Validator validator;
	private ValidatorFactory factory;
	
	public ExcelReader(){
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	public ExcelUploadInformations<Team> readTeams(MultipartFile file, TeamServiceImpl teamServiceImpl, MessageSource messageSource) throws NotSupportedTypeException {
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
				
		String EXCELIDTMP = messageSource.getMessage(EXCELID, null , Locale.forLanguageTag("hu"));
		String EXCELNAMETMP = messageSource.getMessage(EXCELNAME, null , Locale.forLanguageTag("hu"));
		String EXCELPRICETMP = messageSource.getMessage(EXCELPRICE, null , Locale.forLanguageTag("hu"));
		String EXCELPOINTTMP = messageSource.getMessage(EXCELPOINT, null , Locale.forLanguageTag("hu"));
		String EXCELPICTURETMP = messageSource.getMessage(EXCELPICTURE, null , Locale.forLanguageTag("hu"));
		String EXCELACTIVETMP = messageSource.getMessage(EXCELACTIVE, null , Locale.forLanguageTag("hu"));
		
		String[] headerNames = {EXCELIDTMP,EXCELNAMETMP,EXCELPRICETMP,EXCELPOINTTMP,EXCELPICTURETMP,EXCELACTIVETMP};
		int[] headerNumbers = new int[headerNames.length];

		checkHeader(headerNames,headerNumbers,errors,messageSource);
		if(!errors.isEmpty()){
			uploadedFile.delete();
			return returnList;
		}
		
		readRows(rows);
		
		deleteBonusRowsBug(rows);
		
		validateTeamNames(rows,errors,headerNumbers,messageSource);
		
		validateTeams(rows,headerNames,headerNumbers,errors,updateTeams,teamServiceImpl,messageSource);
		
	    uploadedFile.delete();
		
		
		return returnList;
	}

	private void validateTeamNames(List<String[]> rows, List<ExcelErrorMessages> errors, int[] headerNumbers, MessageSource messageSource) {
		
		String EXCELNAMETMP = messageSource.getMessage(EXCELNAME, null , Locale.forLanguageTag("hu"));
		String EXCELSAMETEAMNAMETMP = messageSource.getMessage(EXCELSAMETEAMNAME, null , Locale.forLanguageTag("hu"));
		
		for(int i = 0; i < rows.size() -1 ; ++i){
			for(int j = i+1; j < rows.size() ; ++j){
				if(rows.get(i)[headerNumbers[1]].equals(rows.get(j)[headerNumbers[1]])){
					ExcelErrorMessages excelErrorMessage = new ExcelErrorMessages((i+2), EXCELNAMETMP ,  EXCELSAMETEAMNAMETMP + (j+2));
		 			errors.add(excelErrorMessage);
				}
			}
		}
		
	}

	private void checkHeader(String[] headerNames, int[] headerNumbers,List<ExcelErrorMessages> errors, MessageSource messageSource) {
		
		String EXCELCOLUMNNAMETMP = messageSource.getMessage(EXCELCOLUMNNAME, null , Locale.forLanguageTag("hu"));
		String EXCELMORECOLUMNTMP = messageSource.getMessage(EXCELMORECOLUMN, null , Locale.forLanguageTag("hu"));
		String EXCELLESSCOLUMNTMP = messageSource.getMessage(EXCELLESSCOLUMN, null , Locale.forLanguageTag("hu"));
		String EXCELMISSINGCOLUMNTMP = messageSource.getMessage(EXCELMISSINGCOLUMN, null , Locale.forLanguageTag("hu"));
		
		XSSFSheet sheet = workbook.getSheetAt(0);
		Row firstRow = sheet.getRow(0);
		
		if(headerNames.length < firstRow.getLastCellNum()){
			ExcelErrorMessages excelErrorMessage = new ExcelErrorMessages(HEADERROW, EXCELCOLUMNNAMETMP ,  EXCELMORECOLUMNTMP);
 			errors.add(excelErrorMessage);
 			return;
		}
		if(headerNames.length > firstRow.getLastCellNum()){
			ExcelErrorMessages excelErrorMessage = new ExcelErrorMessages(HEADERROW, EXCELCOLUMNNAMETMP , EXCELLESSCOLUMNTMP);
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
				ExcelErrorMessages excelErrorMessage = new ExcelErrorMessages(HEADERROW, EXCELCOLUMNNAMETMP ,  headerNames[i] + EXCELMISSINGCOLUMNTMP);
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
	
	private void validateTeams(List<String[]> rows,String[] headerNames, int[] headerNumbers,List<ExcelErrorMessages> errors, List<Team> updateTeams,TeamServiceImpl teamServiceImpl, MessageSource messageSource) {
		
		String EXCELIDTMP = messageSource.getMessage(EXCELID, null , Locale.forLanguageTag("hu"));
		String EXCELNOTFOUNDTEAMTMP = messageSource.getMessage(EXCELNOTFOUNDTEAM, null , Locale.forLanguageTag("hu"));
		String EXCELNANTMP = messageSource.getMessage(EXCELNAN, null , Locale.forLanguageTag("hu"));
		String EXCELACTIVECOLUMNERRORTMP = messageSource.getMessage(EXCELACTIVECOLUMNERROR, null , Locale.forLanguageTag("hu"));
		String EXCELEXISTINGTEAMNAMETMP = messageSource.getMessage(EXCELEXISTINGTEAMNAME, null , Locale.forLanguageTag("hu"));
		
		Team team = null;
        Set<ConstraintViolation<Team>> constraintViolations = null;

        for(int i = 0; i < rows.size(); ++i){
        	
        	boolean isCorrectRow = true;
        	long id = 0;
        	//id oszlop
        	//uj csapat, van-e ilyen nev
        	if(rows.get(i)[headerNumbers[0]] == null){
    			
    			if(teamServiceImpl.existTeamByName(rows.get(i)[headerNumbers[1]])){
    				ExcelErrorMessages excelErrorMessage = new ExcelErrorMessages((i+2), headerNames[1] ,EXCELEXISTINGTEAMNAMETMP + rows.get(i)[headerNumbers[1]]);
       				errors.add(excelErrorMessage);
    				isCorrectRow = false;
    			}
        	}
        	// regi csapat, jo id vagy nem
        	else{
        		Team teamTMP = null;
    			try{
    				id = Long.parseLong(rows.get(i)[headerNumbers[0]]);
    				teamTMP = teamServiceImpl.getTeamByIdExcel(id);
        			if(teamTMP == null){
    					ExcelErrorMessages excelErrorMessage = new ExcelErrorMessages((i+2), EXCELIDTMP ,EXCELNOTFOUNDTEAMTMP + rows.get(i)[headerNumbers[0]]);
           				errors.add(excelErrorMessage);
           				isCorrectRow = false;
    				}
    			}
    			catch(NumberFormatException e){
    				ExcelErrorMessages excelErrorMessage = new ExcelErrorMessages((i+2), headerNames[0] ,EXCELNANTMP + rows.get(i)[headerNumbers[0]]);
       				errors.add(excelErrorMessage);
       				isCorrectRow = false;
    			}
        	}
        	
        	//ar oszlop
        	long price = 0;
        	try{
        		price = Long.valueOf(rows.get(i)[headerNumbers[2]]);
        	}
        	catch(NumberFormatException e){
        		ExcelErrorMessages excelErrorMessage = new ExcelErrorMessages((i+2), headerNames[2] ,EXCELNANTMP + rows.get(i)[headerNumbers[2]]);
   				errors.add(excelErrorMessage);
   				isCorrectRow = false;
        	}
        	
        	//pont oszlop
        	int point = 0;
        	try{
        		point = Integer.valueOf(rows.get(i)[headerNumbers[3]]);
        	}
        	catch(NumberFormatException e){
        		ExcelErrorMessages excelErrorMessage = new ExcelErrorMessages((i+2), headerNames[3] ,EXCELNANTMP + rows.get(i)[headerNumbers[3]]);
   				errors.add(excelErrorMessage);
   				isCorrectRow = false;
        	}
        	
        	//aktiv oszlop
        	boolean active = true;
			try{
				if(rows.get(i)[headerNumbers[5]] != null && !(rows.get(i)[headerNumbers[5]].equals("0") || rows.get(i)[headerNumbers[5]].equals("1"))){			
					ExcelErrorMessages excelErrorMessage = new ExcelErrorMessages((i+2), headerNames[5] ,EXCELACTIVECOLUMNERRORTMP + rows.get(i)[headerNumbers[5]]);
       				errors.add(excelErrorMessage);
       				isCorrectRow = false;
				}
				else{
					if(rows.get(i)[headerNumbers[5]] != null &&  rows.get(i)[headerNumbers[5]].equals("0"))  active = false;
					else active = true;
				}	
			}
			catch(NumberFormatException e){
				ExcelErrorMessages excelErrorMessage = new ExcelErrorMessages((i+2), headerNames[5] ,EXCELNANTMP + rows.get(i)[headerNumbers[5]]);
   				errors.add(excelErrorMessage);
   				isCorrectRow = false;
			}
			
			if(isCorrectRow){
				if(rows.get(i)[headerNumbers[0]] == null){
					team = new Team(rows.get(i)[headerNumbers[1]],price,point,rows.get(i)[headerNumbers[4]],active);    						
				}
				else{
					team = new Team(id,rows.get(i)[headerNumbers[1]],price,point,rows.get(i)[headerNumbers[4]],active);    				
				}
			}
			else{
				continue;
			}

        	
    		 constraintViolations =  validator.validate( team );
    	        if(!constraintViolations.isEmpty()){
    		        	Iterator<ConstraintViolation<Team>> iterator = constraintViolations.iterator();
    			        while(iterator.hasNext()) {
    			        	ConstraintViolation<Team> error = iterator.next();
    			        	String field = messageSource.getMessage(error.getPropertyPath().toString(), null , Locale.forLanguageTag("hu"));
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
