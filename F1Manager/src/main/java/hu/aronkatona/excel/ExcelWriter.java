package hu.aronkatona.excel;

import hu.aronkatona.hibernateModel.Team;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.FileCopyUtils;

public class ExcelWriter {
	
	private Logger logger = Logger.getLogger(ExcelWriter.class);
	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private final String TEAMTEMPLATE = "Csapatok.xlsx";
	private final String TEAMSHEET = "Csapatok";
	private final short HEADERFONTSIZE = 15;
	
	public ExcelWriter(){
		workbook = new XSSFWorkbook();
	}

	public void writeTeam(List<Team> teams, boolean withTeams,ServletContext context, HttpServletResponse response) {
		
		sheet = workbook.createSheet(TEAMSHEET);
		String[] headerNames = {"id","name","price","point","picture","active"};
		createHeader(headerNames);
		     
     	if(withTeams){
     		fillWithTeams(teams);
     	}
     	
     	align();
     	
		
		writeAndDownload(TEAMTEMPLATE,context, response);
	}


	private void createHeader(String[] headerNames) {
		Row header = sheet.createRow(0);
		
		XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints(HEADERFONTSIZE);

        CellStyle style = workbook.createCellStyle(); 
        style.setBorderBottom(CellStyle.BORDER_MEDIUM);
        style.setBorderLeft(CellStyle.BORDER_HAIR);
        style.setBorderRight(CellStyle.BORDER_HAIR);
        style.setVerticalAlignment(CellStyle.ALIGN_CENTER);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(headerFont);
        
        for(int i = 0; i < headerNames.length; ++i){
    		Cell cell = header.createCell(i);
          	cell.setCellValue(headerNames[i]);
          	cell.setCellStyle(style);
        }
		
	}
	
	private void fillWithTeams(List<Team> teams) {
		int rowNumber = 1;
		for(Team team : teams){
			Row row = sheet.createRow(rowNumber++);
			
			Cell cell0 = row.createCell(0);
         	cell0.setCellValue(team.getId());
         	
         	Cell cell1 = row.createCell(1);
         	cell1.setCellValue(team.getName());
         	
         	Cell cell2 = row.createCell(2);
         	cell2.setCellValue(team.getPrice());
         	
         	Cell cell3 = row.createCell(3);
         	cell3.setCellValue(team.getPoint());
         	
         	Cell cell4 = row.createCell(4);
         	cell4.setCellValue(team.getPicture());
         	
         	Cell cell5 = row.createCell(5);
         	if(team.isActive())	cell5.setCellValue(1);
         	else cell5.setCellValue(0);
		}     
	}
	
	private void align() {
		XSSFRow rowAlign = workbook.getSheetAt(0).getRow(0);

	    for(int colNum = 0; colNum<rowAlign.getLastCellNum();colNum++){
	      	workbook.getSheetAt(0).autoSizeColumn(colNum);	        	
	    }
	}

	private void writeAndDownload(String excelFileName, ServletContext context, HttpServletResponse response){
		String filePath = context.getRealPath("");
     	filePath += File.separator;
     	filePath += "resources";
     	filePath += File.separator;
     	
     	File dirExist = new File(filePath);
      	if(!dirExist.exists()){
      		dirExist.mkdir();
      	}
      	
      	try{
      		FileOutputStream out = new FileOutputStream(new File(filePath + excelFileName));
            workbook.write(out);
            
            File folder = new File(filePath);
    		File[] listOfFiles = folder.listFiles();
        	File file = null;
        	    for (int i = 0; i < listOfFiles.length; i++) {
        	    	if(listOfFiles[i].getName().equals(excelFileName)){
        	    		file = listOfFiles[i];
        	    		break;
        	    	}
        	    }

            out.close();
            response.setContentType("application/xlsx");
            response.setContentLength(new Long(file.length()).intValue());
            response.setHeader("Content-Disposition", "attachment; filename="+excelFileName);
            FileCopyUtils.copy(new FileInputStream(file),response.getOutputStream());
            file.delete();
      	}
      	catch(Exception e){
      		logger.error("Excel iras letoltes", e);
			e.printStackTrace();
      	}
      	

        
	}

}
