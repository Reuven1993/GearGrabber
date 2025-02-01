package DB;
import java.util.*;

public class ExcelDB {

    private static ExcelDB excelDB = null;
    private HashMap<String , ExcelTable> tables;

    // The constructor builds a hash map for the upcoming tables
    private ExcelDB(){
        tables = new HashMap<String , ExcelTable>();
    }

    // Using a singleton Design Pattern to ensure we have only one instance of the DB
    public static ExcelDB getInstance(){
        if (excelDB == null)
            excelDB = new ExcelDB();
        return excelDB;
    }

    // returns a table from the DB
    public ExcelTable getTable(String tableName){
        return tables.get(tableName);
    }

    // creating in the DB a new table that is based on the data in the excel file specified
    // the excel format is that the first line is headings and the rest are data
    public ExcelTable createTableFromExcel(String tableName) {
        ExcelTable excelTable = new ExcelTable(tableName);
        tables.put(tableName, excelTable);
        return excelTable;
    }

    // writes the changes made to the DB tables back to the excel files
    public void commit(){
        for (ExcelTable excelTable : tables.values()) {
            excelTable.WriteToFile();
        }
    }

    // creates a new table with the specified headings
    // throws exception if the table is already exist or if there is an error writing the file
    public ExcelTable createNewTable(String tableName, String[] headings) throws Exception {
        ExcelTable excelTable = new ExcelTable(tableName, headings);
        tables.put(tableName, excelTable);
        return excelTable;
    }

}
