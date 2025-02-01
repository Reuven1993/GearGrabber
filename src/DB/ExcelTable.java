package DB;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ExcelTable {

    private HashMap<String, String[]> excelMap = new HashMap<String, String[]>();
    private String filename;
    private String[] headings;
    private final String EMPTY_FILE = "Game.db";


    // Create a table from a given Excel file name.
    public ExcelTable(String tableName){
        try {
            filename = "db_tables\\" + tableName+".xlsx";
            File file = new File(filename);   //creating a new file instance
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file
            //creating Workbook instance that refers to .xlsx file
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object
            //iterating over excel file
            boolean isFirstRow = true;
            boolean isFirstColumn;
            String key;
            String[] values;
            for (Row row : sheet) {
                Iterator<Cell> columnsItr = row.cellIterator();   //iterating over each column
                isFirstColumn = true; // to separate the key from the values
                key = null;
                values = new String[100];
                int i = 0;
                // iterating over the columns
                // mind that reading string is different func. from reading a number
                while (columnsItr.hasNext()) {
                    Cell cell = columnsItr.next();

                    // Read first field as a key
                    if (isFirstColumn) {
                        isFirstColumn = false;
                        switch (cell.getCellType()) {
                            case STRING:    //field that represents string cell type
                                key = cell.getStringCellValue();
                                break;
                            case NUMERIC:    //field that represents number cell type
                                key = NumberToTextConverter.toText(cell.getNumericCellValue());
                                break;
                            default:
                        }
                    }
                    // Read regular fields. Note that key is read again as field 0.
                    switch (cell.getCellType()) {
                        case STRING:    //field that represents string cell type
                            values[i] = cell.getStringCellValue();
                            break;
                        case NUMERIC:    //field that represents number cell type
                            values[i] = NumberToTextConverter.toText(cell.getNumericCellValue());
                            break;
                        default:
                    }
                    i++;
                }
                if (isFirstRow){
                    String[] headings = new String[i];
                    System.arraycopy(values,0,headings,0,i);
                    this.headings = headings;
                    isFirstRow = false;
                }
                else
                    excelMap.put(key, values);
            }
        }
        // if no excel file found or cant be accessed
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("No file found or file is not accessible");
        }
    }

    // Create a new table with a given array of headings and save it in an Excel file with the name in tableName
    public ExcelTable(String tableName, String[] headings) throws Exception {
        filename = "db_tables\\" + tableName+".xlsx";
        File file = new File(filename);   //creating a new file instance
        try {
            FileInputStream fis = new FileInputStream(file);
            throw new Exception("File is already exist!");
        } catch (FileNotFoundException e) {
            this.headings = headings;
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(EMPTY_FILE);

            Row row = sheet.createRow(0);
            int columnCount = 0;
            for (String heading : headings) {
                Cell cell = row.createCell(columnCount++);
                cell.setCellValue(heading);
            }

            try (FileOutputStream outputStream = new FileOutputStream(filename)) {
                workbook.write(outputStream);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }


   // insert a new row of key+values to the table
    // throws exception if the key is already exist
    public void insertRow(String[] row) throws Exception {
        String tempRow[] = row.clone();
        String key = row[0];
        if (excelMap.putIfAbsent(key, tempRow) != null)
            throw new Exception("Primary key already exist!");
    }

    // updates a row in the table
    // throws exception if the key is absent
    public void updateRow(String[] row) throws Exception {
        String tempRow[] = row.clone();
        String key = row[0];
        if (excelMap.get(key) != null)
            excelMap.put(key, tempRow);
        else
            throw new Exception("Can't find primary key in table!");
    }

    // Return all the fields in a row associated with a key
    public String[] getFields(String key){
       return excelMap.get(key);
    }

    // Get only selected fields from a row with a key.
    // The indices of the fields are given in an array named index.
    // Index 0 is the key
    public String[] getFields(String key, int[] index){
        String[] row = new String[index.length];
        for (int i = 0; i < index.length; i++) {
            row[i] = excelMap.get(key)[index[i]];
        }
        return row;
    }

    // Delete the row associated with a key
    public void deleteRow(String key) {
        excelMap.remove(key);
    }

    // Clean table by deleting all rows
    public void deleteAllRows() {
        excelMap.clear();
    }

    // internal methods for sorting and comparing

    private <K, V extends Comparable<? super String[]>> Comparator<Map.Entry<String, String[]>> compareStrByXValue(int index) {
        return (Comparator<Map.Entry<String, String[]>> & Serializable)
                (c1, c2) -> c1.getValue()[index].compareTo(c2.getValue()[index]);
    }

    private <K, V extends Comparable<? super String[]>> Comparator<Map.Entry<String, String[]>> compareIntByXValue(int index) {
        return (Comparator<Map.Entry<String, String[]>> & Serializable)
                (c1, c2) -> (Integer.valueOf(c1.getValue()[index])).compareTo( Integer.valueOf(c2.getValue()[index]));
    }

 
    // checks if a string is a number
    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    // Sort the table by the specified column
    // If the column is string the sort is alphabetical and if numeric according to number.
    // The sort depends on the isDescending parameter and can go from small to big (false) or big to small (true)
    // If only ascending sort is needed, no need to specify the isDescending parameter.
    public void sortTable(int index){
        sortTable(index, false);
    }

    public void sortTable(int index, boolean isDescending)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, String[]> > list = new LinkedList<Map.Entry<String, String[]> >(excelMap.entrySet());

        // Sort the list
        if (isNumeric(list.get(0).getValue()[index])) {
            list.sort(compareIntByXValue(index));
        }
        else {
            list.sort(compareStrByXValue(index));
        }
        // Reverse order to return items from high to low
        if (isDescending) {
            Collections.reverse(list);
        }

        // put data from sorted list to hashmap
        HashMap<String, String[]> tempMap = new LinkedHashMap<String, String[]>();
        for (Map.Entry<String, String[]> mapEntry : list) {
            tempMap.put(mapEntry.getKey(), mapEntry.getValue());
        }
        this.excelMap = tempMap;
    }

    // sort the table by the primary keys (A to Z)
    public void sortByKey() {
        sortTable(0, false);
    }


    // returns the table's data (without the headings) as a nXm matrix
    public String[][] getTableAsMatrix(){
        String[][] table = new String[excelMap.size()][headings.length];
        int rowCount = 0;
        for (String key : excelMap.keySet()) {
            table[rowCount] = getFields(key);
            rowCount++;
        }
        return table;
    }

    // returns the table's data of the key and the selected columns
    public String[][] getTableAsMatrix(int[] index){
        String[][] table = new String[excelMap.size()][index.length];
        int rowCount = 0;
        for (String key : excelMap.keySet()) {
            table[rowCount] = getFields(key, index);
            rowCount++;
        }
        return table;
    }

    public void WriteToFile() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(EMPTY_FILE);

        Row firstRow = sheet.createRow(0);
        int firstRowColumn = 0;
        for (String heading : headings) {
            Cell keyCell = firstRow.createCell(firstRowColumn);
            keyCell.setCellValue(heading);
            firstRowColumn++;
        }
        int rowCount = 1;

        for (String key : excelMap.keySet()) {
            Row row = sheet.createRow(rowCount++);

            int columnCount = 0;
            for (String value : excelMap.get(key)) {
                Cell valueCell = row.createCell(columnCount++);
                valueCell.setCellValue(value);
            }

        }
        try (FileOutputStream outputStream = new FileOutputStream(filename)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
