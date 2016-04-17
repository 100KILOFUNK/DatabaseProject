import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.io.File;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class WriteExcel {

  private WritableCellFormat timesBoldUnderline;
  private WritableCellFormat times;
  private String inputFile;
  
public void setOutputFile(String inputFile) {
  this.inputFile = inputFile;
  }

  public void writeWinter(String name,reservation rs,int season,ArrayList<Winter> winter) throws IOException, WriteException {
    File file = new File(inputFile);
    WorkbookSettings wbSettings = new WorkbookSettings();

    wbSettings.setLocale(new Locale("en", "EN"));

    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
    workbook.createSheet("Fakturerings Underlag", 0);
    WritableSheet excelSheet = workbook.getSheet(0);
    createLabel(excelSheet,rs.getGroupSize(),season);
    createWinterContent(excelSheet,name,rs,winter);

    workbook.write();
    workbook.close();
  }
  public void writeSummer(String name,reservation rs,int season,ArrayList<Summer> summer) throws IOException, WriteException {
	    File file = new File(inputFile);
	    WorkbookSettings wbSettings = new WorkbookSettings();

	    wbSettings.setLocale(new Locale("en", "EN"));

	    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
	    workbook.createSheet("Fakturerings Underlag", 0);
	    WritableSheet excelSheet = workbook.getSheet(0);
	    createLabel(excelSheet,rs.getGroupSize(),season);
	    createSummerContent(excelSheet,name,rs,summer);

	    workbook.write();
	    workbook.close();
	  }


  private void createLabel(WritableSheet sheet,int groupSize,int season)
      throws WriteException {
    // Lets create a times font
    WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
    // Define the cell format
    times = new WritableCellFormat(times10pt);
    // Lets automatically wrap the cells
    times.setWrap(true);

    // create create a bold font with unterlines
    WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
        UnderlineStyle.SINGLE);
    timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
    // Lets automatically wrap the cells
    timesBoldUnderline.setWrap(true);

    CellView cv = new CellView();
    cv.setFormat(times);
    cv.setFormat(timesBoldUnderline);
    cv.setAutosize(true);

    // Write a few headers
    addCaption(sheet, 0, 0, "Full Name");
    addCaption(sheet, 1, 0, "Reservation ID");
    addCaption(sheet, 2, 0, "Cabin Number");
    addCaption(sheet, 3, 0, "Group Size");
    addCaption(sheet, 4, 0, "Cabin Price");
    addCaption(sheet, 5, 0, "Equipment Price*");
    addCaption(sheet, 6, 0, "Start Date");
    addCaption(sheet, 7, 0, "End Date");
    addCaption(sheet, 8, 0, "Total Price");
    addCaption(sheet, 0, 6, "*Equipment Price");
    if(season==0){
    		addCaption(sheet, 0, 7, "Group Member");
    		addCaption(sheet, 1, 7, "Type");
    		addCaption(sheet, 2, 7, "Length");
    		addCaption(sheet, 3, 7, "Head Size");
    		addCaption(sheet, 4, 7, "Price");
    }
    if(season==1){
    	addCaption(sheet, 0, 7, "Group Member");
    	addCaption(sheet, 1, 7, "Type");
    	addCaption(sheet, 2, 7, "Shoe Size");
    	addCaption(sheet, 3, 7, "Ski Size");
    	addCaption(sheet, 4, 7, "Pole Size");
    	addCaption(sheet, 5, 7, "Head Size");
    	addCaption(sheet, 6, 7, "Price");
    }
  }

  private void createWinterContent(WritableSheet sheet,String name,reservation rs,ArrayList<Winter> winter) throws WriteException,
      RowsExceededException {
    // Write a few number;
	  addLabel(sheet,0,1,name);
	  addNumber(sheet,1,1,rs.getR_id());
	  addNumber(sheet,2,1,rs.getCabinNr());
	  addNumber(sheet,3,1,rs.getGroupSize());
	  addLabel(sheet,4,1,Float.toString(rs.getCabinPrice()));
	  addLabel(sheet,5,1,Float.toString(rs.getEqPrice()));
	  addLabel(sheet,6,1,rs.getStartDate().toString());
	  addLabel(sheet,7,1,rs.getEndDate().toString());
	  addLabel(sheet,8,1,Float.toString(rs.getCabinPrice()+rs.getEqPrice()));
	  int j = 8;
	  int k = 0;

	 for(int i=0;i<winter.size();i++){
		  addNumber(sheet,k,j,i+1);
		  k++;
		  addLabel(sheet,k,j,winter.get(i).getType());
		  k++;
		  addLabel(sheet,k,j,Float.toString(winter.get(i).getShoeSize()));
		  k++;
		  addNumber(sheet,k,j,winter.get(i).getSkiSize());
		  k++;
		  addNumber(sheet,k,j,winter.get(i).getPoleSize());
		  k++;
		  addNumber(sheet,k,j,winter.get(i).getHeadSize());
		  k++;
		  addLabel(sheet,k,j,Float.toString(winter.get(i).getPrice()));
		  k=0;
		  j++;
	  }

  }
  private void createSummerContent(WritableSheet sheet,String name,reservation rs,ArrayList<Summer> summer) throws WriteException,
  RowsExceededException {
// Write a few number;
  addLabel(sheet,0,1,name);
  addNumber(sheet,1,1,rs.getR_id());
  addNumber(sheet,2,1,rs.getCabinNr());
  addNumber(sheet,3,1,rs.getGroupSize());
  addLabel(sheet,4,1,Float.toString(rs.getCabinPrice()));
  addLabel(sheet,5,1,Float.toString(rs.getEqPrice()));
  addLabel(sheet,6,1,rs.getStartDate().toString());
  addLabel(sheet,7,1,rs.getEndDate().toString());
  addLabel(sheet,8,1,Float.toString(rs.getCabinPrice()+rs.getEqPrice()));
  int j = 8;
  int k = 0;
 for(int i=0;i<summer.size();i++){
	  addNumber(sheet,k,j,i+1);
	  k++;
	  addLabel(sheet,k,j,summer.get(i).getType());
	  k++;
	  addNumber(sheet,k,j,summer.get(i).getLength());
	  k++;
	  addNumber(sheet,k,j,summer.get(i).getHeadSize());
	  k++;
	  addLabel(sheet,k,j,Float.toString(summer.get(i).getPrice()));
	  k=0;
	  j++;
  }

}

  private void addCaption(WritableSheet sheet, int column, int row, String s)
      throws RowsExceededException, WriteException {
    Label label;
    label = new Label(column, row, s, timesBoldUnderline);
    sheet.addCell(label);
  }

  private void addNumber(WritableSheet sheet, int column, int row,
      Integer integer) throws WriteException, RowsExceededException {
    Number number;
    number = new Number(column, row, integer, times);
    sheet.addCell(number);
  }
  private void addLabel(WritableSheet sheet, int column, int row, String s)
      throws WriteException, RowsExceededException {
    Label label;
    label = new Label(column, row, s, times);
    sheet.addCell(label);
  }
}